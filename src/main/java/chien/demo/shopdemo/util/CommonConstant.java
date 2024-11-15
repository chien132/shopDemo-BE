package chien.demo.shopdemo.util;

import java.util.List;

/**
 * Common constants.
 */
public final class CommonConstant {
    private CommonConstant() {}

    public static final List<String> COLUMNS_IGNORE =
            List.of("createdDate", "createdBy", "updatedDate", "updatedBy");

    // Report file type constants
    public static final Integer EXCEL_TYPE = 0;
    public static final Integer PDF_TYPE = 1;

    /**
     * Date time format.
     */
    public static class DateTimeFormat {
        public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    }

    /**
     * Time zone id.
     */
    public static class TimeZoneId {
        public static final String ASIA_TOKYO = "Asia/Tokyo";
    }

    /**
     * Fonts.
     */
    public static class Font {
        public static final String NOTOSANSJP_FONT_PATH = "/fonts/NotoSansJP.ttf";
        public static final String NOTOSANSJP_NAME = "NotoSansJP";
    }

    /**
     * Character.
     */
    public static class Character {
        public static final String UNDERSCORE = "_";
        public static final String SPACE = " ";
    }
}
