package chien.demo.shopdemo.util;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The type Excel to pdf.
 */
public class ExcelToPdf {

    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    /**
     * Convert to pdf.
     *
     * @param workbook      the workbook
     * @param outputStream  the output stream
     * @param headerAddress the header address
     */

    public static void toPdfOutputStream(XSSFWorkbook workbook, OutputStream outputStream, CellAddress headerAddress) {
        XSSFFormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(workbook);

        XSSFSheet sheet = workbook.getSheetAt(0);

        // get number of columns
        int numberOfColumns = sheet.getRow(headerAddress.getRow()).getLastCellNum();

        // calculate columnWidth
        List<Float> columnWidths = new ArrayList<>();
        float totalExcelWidth = 0;
        for (int i = 0; i < numberOfColumns; i++) {
            if (sheet.isColumnHidden(sheet.getRow(headerAddress.getRow()).getCell(i).getColumnIndex())) {
                // ignore hidden column
                continue;
            }
            if (sheet.getColumnWidth(i) == Math.toIntExact(Math.round(sheet.getDefaultColumnWidth() * 256.0))) {
                org.apache.poi.ss.usermodel.Cell cell = sheet.getRow(headerAddress.getRow()).getCell(i);

                // Get font size
                int fontSize = workbook.getFontAt(cell.getCellStyle().getFontIndex()).getFontHeightInPoints();

                // Estimate the width of the cell in pixels
                int dataLength = DATA_FORMATTER.formatCellValue(cell, formulaEvaluator).length();

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
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.useAllAvailableWidth();

        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            for (int columnNum = 0; columnNum < numberOfColumns; columnNum++) {
                XSSFCell xlsCell = row.getCell(columnNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                // check if we need colspan for merged cells
                int mergedCellIndex = getIndexForMergedCell(sheet, xlsCell);
                int colSpan = 1;
                if (mergedCellIndex > -1) {
                    CellRangeAddress mergedRegion = sheet.getMergedRegion(mergedCellIndex);
                    int first = mergedRegion.getFirstColumn();
                    int last = mergedRegion.getLastColumn();
                    columnNum = last;
                    colSpan = (last - first) + 1;
                }

                // create new cell
                Cell pdfCell = createPdfCell(xlsCell, colSpan, row.getHeightInPoints());
                table.addCell(pdfCell);
            }
        }

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
        Document doc = setupDocument(pdfDoc, totalExcelWidth);
        doc.add(table);
        doc.close();
        pdfDoc.close();

    }

    private static int getIndexForMergedCell(Sheet sheet, XSSFCell cell) {
        int numMerged = sheet.getNumMergedRegions();
        for (int i = 0; i < numMerged; i++) {
            CellRangeAddress mergedCell = sheet.getMergedRegion(i);
            if (mergedCell.isInRange(cell)) {
                return i;
            }
        }
        return -1;
    }

    private static Cell createPdfCell(XSSFCell xlsCell, int colSpan, float rowHeightInPoints) {
        String cellValueAsString = getCellValueAsString(xlsCell);
        Paragraph paragraph = new Paragraph().add(cellValueAsString);
        XSSFCellStyle cellStyle = xlsCell.getCellStyle();

        float fontSize = cellStyle.getFont().getFontHeightInPoints();
        paragraph.setFontSize(fontSize);
        if (cellStyle.getFont().getBold()) {
            paragraph.setBold();
        }
        if (cellStyle.getFont().getItalic()) {
            paragraph.setItalic();
        }
        paragraph.setFontColor(getRgbColorObject(cellStyle.getFont().getXSSFColor()));

        Cell pdfCell = new Cell(1, colSpan);
        setAlignment(pdfCell, paragraph, cellStyle);
        setCellBorder(xlsCell, pdfCell);
        return pdfCell.setBackgroundColor(getRgbColorObject(cellStyle.getFillForegroundXSSFColor()))
                .add(paragraph).setMargins(0f, 0f, 0f, 0f).setPaddings(1.5f, 1.5f, 1.5f, 1.5f);
    }

    private static void setAlignment(Cell pdfCell, Paragraph paragraph, XSSFCellStyle cellStyle) {
        switch (cellStyle.getAlignment()) {
            case LEFT:
                pdfCell.setHorizontalAlignment(HorizontalAlignment.LEFT);
                paragraph.setTextAlignment(TextAlignment.LEFT);
                break;
            case CENTER:
                pdfCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                paragraph.setTextAlignment(TextAlignment.CENTER);
                break;
            case RIGHT:
                pdfCell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                paragraph.setTextAlignment(TextAlignment.RIGHT);
                break;
            default:
                break;
        }
        switch (cellStyle.getVerticalAlignment()) {
            case TOP:
                pdfCell.setVerticalAlignment(VerticalAlignment.TOP);
                break;
            case CENTER:
                pdfCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                break;
            case BOTTOM:
                pdfCell.setVerticalAlignment(VerticalAlignment.BOTTOM);
                break;
            default:
                break;
        }
    }

    private static void setCellBorder(XSSFCell xlsCell, Cell pdfCell) {
        XSSFCellStyle cellStyle = xlsCell.getCellStyle();
        pdfCell.setBorderBottom(getPdfBorder(cellStyle.getBorderBottom(), cellStyle.getBottomBorderXSSFColor()));

        pdfCell.setBorderLeft(getPdfBorder(cellStyle.getBorderLeft(), cellStyle.getLeftBorderXSSFColor()));

        pdfCell.setBorderRight(getPdfBorder(cellStyle.getBorderRight(), cellStyle.getRightBorderXSSFColor()));

        pdfCell.setBorderTop(getPdfBorder(cellStyle.getBorderTop(), cellStyle.getTopBorderXSSFColor()));
    }

    private static byte[] getDefaultRgb(int index) {
        HSSFColor hssfColor = HSSFColor.getIndexHash().get(index);
        if (hssfColor == null) {
            return null;
        }
        short[] rgbShort = hssfColor.getTriplet();
        return new byte[]{(byte) rgbShort[0], (byte) rgbShort[1], (byte) rgbShort[2]};
    }

    private static DeviceRgb getRgbColorObject(XSSFColor xssfColor) {
        if (xssfColor == null) {
            return null;
        }
        byte[] rgb = xssfColor.getRGBWithTint() != null ? xssfColor.getRGBWithTint() : xssfColor.getRGB();
        if (rgb == null) {
            return null;
        }
        return new DeviceRgb(Byte.toUnsignedInt(rgb[0]), Byte.toUnsignedInt(rgb[1]), Byte.toUnsignedInt(rgb[2]));
    }

    private DeviceRgb getRgbColorObject(HSSFColor hssfColor) {
        if (hssfColor == null) {
            return null;
        }
        short[] rgbShort = hssfColor.getTriplet();
        if (rgbShort == null) {
            return null;
        }
        byte[] rgb = new byte[]{(byte) rgbShort[0], (byte) rgbShort[1], (byte) rgbShort[2]};
        return new DeviceRgb(Byte.toUnsignedInt(rgb[0]), Byte.toUnsignedInt(rgb[1]), Byte.toUnsignedInt(rgb[2]));
    }



    private static Border getPdfBorder(BorderStyle bs, XSSFColor xssfColor) {
        DeviceRgb defaultColor = new DeviceRgb(0, 0, 0);
        DeviceRgb rgbColorObject = getRgbColorObject(xssfColor);
        Color color = rgbColorObject != null ? rgbColorObject : defaultColor;

        return switch (bs) {
            case DASHED, MEDIUM_DASHED -> new DashedBorder(color, 1);
            case DOTTED -> new DottedBorder(color, 1);
            case THIN -> new SolidBorder(color, 0.5f);
            case THICK -> new SolidBorder(color, 1.5f);
            case MEDIUM -> new SolidBorder(color, 1);
            case DOUBLE -> new DoubleBorder(color, 100);
            default -> Border.NO_BORDER;
        };
    }

    private static String getCellValueAsString(XSSFCell cell) {
        String value;
        if (cell.getCellType() == CellType.FORMULA) {
            try {
                CellStyle cellStyle = cell.getCellStyle();
                value = DATA_FORMATTER.formatRawCellContents(cell.getNumericCellValue(),
                        cellStyle.getDataFormat(), cellStyle.getDataFormatString());
            } catch (IllegalStateException e) {
                try {
                    value = cell.getStringCellValue();
                } catch (IllegalStateException e1) {
                    value = "";
                }
            }
        } else {
            value = DATA_FORMATTER.formatCellValue(cell);
        }
        return value;
    }


    private static Document setupDocument(PdfDocument pdfDoc, float pageWidth) {
        // Create document base on Excel size
        Document document;
        if (pageWidth > pdfDoc.getDefaultPageSize().getWidth()) {
            pdfDoc.setDefaultPageSize(pdfDoc.getDefaultPageSize().rotate());
            float pageHeight = Math.max(pdfDoc.getDefaultPageSize().getHeight(),
                    pageWidth * pdfDoc.getDefaultPageSize().getHeight() / pdfDoc.getDefaultPageSize().getWidth());
            document = new Document(pdfDoc, new PageSize(pageWidth, pageHeight));
        } else {
            document = new Document(pdfDoc, PageSize.A4);
        }
        document.setMargins(10, 20, 10, 20);
        return document;
    }


}