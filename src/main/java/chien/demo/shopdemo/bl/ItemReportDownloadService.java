package chien.demo.shopdemo.bl;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import chien.demo.shopdemo.dto.ItemDto;
import chien.demo.shopdemo.exception.NotFoundException;
import chien.demo.shopdemo.service.AbstractMessage;
import chien.demo.shopdemo.service.ItemService;
import chien.demo.shopdemo.util.CommonConstant;
import chien.demo.shopdemo.util.Utilities;

/**
 * Network report download service.
 *
 * @author ChienDang
 */
@RequiredArgsConstructor
@Service
public class ItemReportDownloadService extends AbstractMessage {

    private static final Logger logger = LoggerFactory.getLogger(ItemReportDownloadService.class);

    private static final String BL_NO = "NetworkReportDownload";

    private static final String METHOD = "downloadReport";

    private final ItemService itemService;

    private final SimpleDateFormat simpleDateFormat
            = new SimpleDateFormat(CommonConstant.DateTimeFormat.YYYYMMDD_HHMMSS);

    @Value("${report.template-path}")
    private String templatePath;

    @Value("${report.file-name}")
    private String fileName;

    @Value("${report.startCell.column}")
    private Integer startColumn;

    @Value("${report.startCell.row}")
    private Integer startRow;

    private final DataFormatter dataFormatter = new DataFormatter();

    private FormulaEvaluator formulaEvaluator;

    /**
     * Download network report function.
     *
     * @return the byte array output stream of Excel file
     */
    public ByteArrayOutputStream download(Integer type) {
        logger.info(getMessageStart(BL_NO, METHOD));
        logger.debug(getMessageInputParam(BL_NO, "type", type));

        // Check input params
        checkInputParameters(type);

        // Get records from DB
        List<ItemDto> networks = itemService.findAll();
        logger.debug("Found %d record(s) to report".formatted(networks.size()));

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+9"));

        // Get template file and fill data
        File templateFile = new File(Path.of(this.templatePath + fileName).toString());
        try (FileInputStream templateInputStream = new FileInputStream(templateFile);
             XSSFWorkbook excelFile = new XSSFWorkbook(templateInputStream)) {
            if (excelFile.getNumberOfSheets() == 0 || excelFile.getSheetAt(0).getPhysicalNumberOfRows() == 0) {
                throw new RuntimeException(getMessage("error.templateFileBlank"));
            }
            formulaEvaluator = new XSSFFormulaEvaluator(excelFile);
            CellAddress headerAddress = new CellAddress(startRow - 2, startColumn - 1);
            writeExcelFile(excelFile, headerAddress, networks);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if (type.equals(CommonConstant.EXCEL_TYPE)) {
                excelFile.write(outputStream);
            } else if (type.equals(CommonConstant.PDF_TYPE)) {
                writePdfOutputStream(excelFile, headerAddress, outputStream);
                //ExcelToPdf.toPdfOutputStream(excelFile, outputStream, headerAddress);
            }

            logger.info(getMessageEnd(BL_NO, METHOD));
            return outputStream;

        } catch (FileNotFoundException e) {
            String errorMsg = getMessage("error.templateFileNotFound");
            logger.error(errorMsg, e);
            logger.info(getMessageEnd(BL_NO, METHOD));
            throw new NotFoundException(errorMsg);
        } catch (IOException e) {
            String errorMsg = String.format("%s: %s", getMessage("Error.500"), e.getMessage());
            logger.error(errorMsg, e);
            logger.info(getMessageEnd(BL_NO, METHOD));
            throw new RuntimeException(errorMsg);
        } catch (Exception e) {
            String errorMsg = String.format("%s: %s", getMessage("Error.500"), e.getMessage());
            logger.error(errorMsg, e);
            logger.info(getMessageEnd(BL_NO, METHOD));
            throw e;
        }
    }

    private void checkInputParameters(Integer type) {
        if (type == null || (!type.equals(CommonConstant.EXCEL_TYPE) && !type.equals(CommonConstant.PDF_TYPE))) {
            throw new IllegalArgumentException("File type must be EXCEL or PDF");
        }
    }

    private void writePdfOutputStream(Workbook excelFile, CellAddress headerAddress,
                                      ByteArrayOutputStream outputStream) throws IOException {
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDoc);
        document.setMargins(10, 20, 10, 20);

        // Create table base on Excel size
        Sheet sheet = excelFile.getSheetAt(0);
        int numberOfColumns = sheet.getRow(headerAddress.getRow()).getLastCellNum();

        // Set width for PDF base on Excel columns
        List<Float> columnWidths = new ArrayList<>();
        float totalExcelWidth = 0;
        for (int i = 0; i < numberOfColumns; i++) {
            if (sheet.isColumnHidden(sheet.getRow(headerAddress.getRow()).getCell(i).getColumnIndex())) {
                // ignore hidden column
                continue;
            }
            if (sheet.getColumnWidth(i) == Math.toIntExact(Math.round(sheet.getDefaultColumnWidth() * 256.0))) {
                Cell cell = sheet.getRow(headerAddress.getRow()).getCell(i);

                // Get font size
                int fontSize = excelFile.getFontAt(cell.getCellStyle().getFontIndex()).getFontHeightInPoints();

                // Estimate the width of the cell in pixels
                int dataLength = this.dataFormatter.formatCellValue(cell, this.formulaEvaluator).length();

                // Estimate width based on character count and font size
                float estimatedWidth = fontSize * 0.12f * dataLength;
                columnWidths.add(estimatedWidth * Units.DEFAULT_CHARACTER_WIDTH);
            } else {
                columnWidths.add(sheet.getColumnWidthInPixels(i));
            }
        }
        float[] columnWidthArray = new float[columnWidths.size()];
        for (int i = 0; i < columnWidths.size(); i++) {
            columnWidthArray[i] = columnWidths.get(i);
            totalExcelWidth += columnWidthArray[i];
        }
        Table table = new Table(UnitValue.createPercentArray(columnWidthArray));
        table.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

        float pageWidth = totalExcelWidth + document.getLeftMargin() + document.getRightMargin();
        if (pageWidth > pdfDoc.getDefaultPageSize().getWidth()) {
            pdfDoc.setDefaultPageSize(pdfDoc.getDefaultPageSize().rotate());
            float pageHeight = Math.max(pdfDoc.getDefaultPageSize().getHeight(),
                    pageWidth * pdfDoc.getDefaultPageSize().getHeight() / pdfDoc.getDefaultPageSize().getWidth());
            pdfDoc.setDefaultPageSize(new PageSize(pageWidth, pageHeight));
        }
        table.setWidth(UnitValue.createPercentValue(100));

        // Get font that support Japanese
        PdfFont font = PdfFontFactory.createFont(CommonConstant.Font.NOTOSANSJP_FONT_PATH, PdfEncodings.IDENTITY_H,
                PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);

        // Write data from Excel to table
        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);
            for (int cellIndex = 0; cellIndex < numberOfColumns; cellIndex++) {
                Cell currentCell = currentRow.getCell(cellIndex);
                if (sheet.isColumnHidden(currentCell.getColumnIndex())) {
                    // ignore hidden cell
                    continue;
                }
                String cellValue = dataFormatter.formatCellValue(currentCell, this.formulaEvaluator);
                com.itextpdf.layout.element.Cell pdfCell = new com.itextpdf.layout.element.Cell();
                Paragraph paragraph = new Paragraph(cellValue).setMultipliedLeading(1);
                pdfCell.add(paragraph);
                //if (rowIndex == headerAddress.getRow()) {
                //    pdfCell.setHeight(currentCell.getRow().getHeightInPoints());
                //}
                if (Objects.nonNull(currentCell)) {
                    // Set cell style
                    // Set font
                    XSSFFont excelFont = (XSSFFont) sheet.getWorkbook()
                            .getFontAt(currentCell.getCellStyle().getFontIndex());
                    paragraph.setFont(font).setFontSize(excelFont.getFontHeightInPoints() + 1f);

                    // Set Font color
                    paragraph.setFontColor(getRgbColorObject(excelFont.getXSSFColor()));

                    // Set BG Color
                    XSSFColor bgColor = ((XSSFColor) currentCell.getCellStyle().getFillForegroundColorColor());
                    if (Objects.nonNull(bgColor)) {
                        DeviceRgb bgRgb = getRgbColorObject(bgColor);
                        if (Objects.nonNull(bgRgb)) {
                            pdfCell.setBackgroundColor(bgRgb);
                        }
                    }

                    // Padding & alignments
                    pdfCell.setPaddings(1, 2, 1, 2);
                    try {
                        pdfCell.setVerticalAlignment(VerticalAlignment.valueOf(
                                currentCell.getCellStyle().getVerticalAlignment().name()));
                        pdfCell.setTextAlignment(TextAlignment.valueOf(
                                currentCell.getCellStyle().getAlignment().name()));
                    } catch (IllegalArgumentException ignored) {
                        // ignored if cell dont have text align
                    }

                    // Text style
                    if (excelFont.getItalic()) {
                        paragraph.setItalic();
                    }
                    if (excelFont.getUnderline() == 1) {
                        paragraph.setUnderline();
                    }
                    if (excelFont.getBold()) {
                        paragraph.setBold();
                    }

                    // Borders
                    pdfCell.setBorderTop(convertExcelBorderToPdfBorder(currentCell.getCellStyle().getBorderTop(),
                            ((XSSFCell) currentCell).getCellStyle().getTopBorderXSSFColor()));
                    pdfCell.setBorderBottom(convertExcelBorderToPdfBorder(currentCell.getCellStyle().getBorderBottom(),
                            ((XSSFCell) currentCell).getCellStyle().getBottomBorderXSSFColor()));
                    pdfCell.setBorderLeft(convertExcelBorderToPdfBorder(currentCell.getCellStyle().getBorderLeft(),
                            ((XSSFCell) currentCell).getCellStyle().getLeftBorderXSSFColor()));
                    pdfCell.setBorderRight(convertExcelBorderToPdfBorder(currentCell.getCellStyle().getBorderRight(),
                            ((XSSFCell) currentCell).getCellStyle().getRightBorderXSSFColor()));

                }
                table.addCell(pdfCell);
            }
        }
        // Set page height to fit table
        float tableHeight = table.createRendererSubTree().setParent(document.getRenderer())
                .layout(new LayoutContext(new LayoutArea(0, pdfDoc.getDefaultPageSize())))
                .getOccupiedArea().getBBox().getHeight();
        pdfDoc.setDefaultPageSize(new PageSize(pdfDoc.getDefaultPageSize().getWidth(),
                tableHeight + (document.getTopMargin() * 2) + (document.getBottomMargin() * 2)));
        document.add(table);
        document.close();
    }

    private void writeExcelFile(Workbook workbook, CellAddress headerAddress, List<ItemDto> networks) {
        Sheet sheet = workbook.getSheetAt(0);
        // Get headers of template
        List<String> headers = getHeadersAsCamelCase(sheet.getRow(headerAddress.getRow()));
        Field[] dtoFields = ItemDto.class.getDeclaredFields();
        Map<Integer, Field> fieldMap = new HashMap<>();
        for (Field field : dtoFields) {
            int index = headers.indexOf(field.getName());
            if (index != -1) {
                fieldMap.put(index, field);
            }
        }

        // Format style for value cells
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        // Write values
        int rowIndex = headerAddress.getRow();
        for (ItemDto network : networks) {
            Row row = sheet.createRow(++rowIndex);
            int column = headerAddress.getColumn();
            for (int i = 0; i < headers.size(); i++) {
                String value = null;
                if (Objects.nonNull(fieldMap.get(i))) {
                    value = getValueFromObject(fieldMap.get(i), network);
                }
                Cell cell = row.createCell(column++);
                cell.setCellValue(value);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private List<String> getHeadersAsCamelCase(Row row) {
        List<String> headers = new ArrayList<>();
        for (Cell cell : row) {

            String cellValue = this.dataFormatter.formatCellValue(cell, this.formulaEvaluator).trim();
            headers.add(Utilities.toCamelCaseString(cellValue));
        }
        return headers;
    }

    private String getValueFromObject(Field field, ItemDto network) {
        field.setAccessible(true);
        Object value = null;
        try {
            value = switch (field.get(network)) {
                //case NetworkAllOfTypeDto networkAllOfTypeDto ->
                //        // Get Type name instead of object
                //        networkAllOfTypeDto.getName();
                //case NetworkAllOfApproveDto networkAllOfApproveDto ->
                //        // Get Type name instead of object
                //        networkAllOfApproveDto.getName();
                //case StatusDto statusDto ->
                //        // Get Type name instead of object
                //        statusDto.getName();
                case Date date ->
                        // Format UTC to LocalTime
                        simpleDateFormat.format(date);
                case null, default -> field.get(network);
            };
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Objects.nonNull(value) ? value.toString() : "";
    }

    private static DeviceRgb getRgbColorObject(XSSFColor xssfColor) {
        if (xssfColor == null) {
            return null;
        }
        byte[] rgb = xssfColor.getRGBWithTint() != null ? xssfColor.getRGBWithTint() : xssfColor.getRGB();

        return rgb == null ? null
                : new DeviceRgb(Byte.toUnsignedInt(rgb[0]), Byte.toUnsignedInt(rgb[1]), Byte.toUnsignedInt(rgb[2]));
    }

    private static Border convertExcelBorderToPdfBorder(BorderStyle borderStyle, XSSFColor color) {
        if (borderStyle == BorderStyle.NONE) {
            return Border.NO_BORDER;
        }

        // Set a basic width based on the Excel border style
        float width = switch (borderStyle) {
            case THIN -> 0.5f;
            case MEDIUM -> 1.0f;
            case THICK -> 1.5f;
            case DOUBLE -> 2.0f;
            default -> 0.5f;
        };

        Color borderColor = DeviceRgb.BLACK;
        if (Objects.nonNull(color)) {
            DeviceRgb colorRgb = getRgbColorObject(color);
            if (Objects.nonNull(colorRgb)) {
                borderColor = colorRgb;
            }
        }

        return switch (borderStyle) {
            case DOTTED -> new DottedBorder(borderColor, width);
            case DASHED -> new DashedBorder(borderColor, width);
            default -> new SolidBorder(borderColor, width);
        };

    }
}
