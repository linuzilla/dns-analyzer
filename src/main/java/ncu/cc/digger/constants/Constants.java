package ncu.cc.digger.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class Constants {
    public static final String VERSION = "DNS-Digger 1.0";
    public static final String BASE_PACKAGE = "ncu.cc.digger";

    public static final String FLASH_MESSAGE = "FlashMessage";

    public static final String G_RECAPTCHA_RESPONSE = "g-recaptcha-response";

    public static final Locale DEFAULT_LOCALE = Locale.TRADITIONAL_CHINESE;

    public static final String DB_SCHEMA = "digger_db";

    public static final int DIG_CACHE_TTL = 300;
    public static final int DIG_CACHE_MIN_TTL = 60;

    public static final String ZONE_PATH_VARIABLE = "{zone:[-\\w\\.]+}";

    public static final String LOCALE_ZH_TW = "zh_TW";
    public static final String LOCALE_EN_US = "en_US";

    public static final int MAX_QUEUED_REQUEST = 10;

    public static final String SEMICOLON = ";";
    public static final String COLON = ":";

    public static final String REVERSE_IPV6 = ".ip6.arpa";

    public static final int DEFAULT_PAGE_SIZE = 100;

    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");
    public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static final String RANKING_SCORE = "rank";
    public static final String RANKING_IPV6 = "ipv6";
    public static final String RANKING_DNSSEC = "dnssec";
    public static final String RANKING_EDNS = "edns";
    public static final String RANKING_AXFR = "axfr";
    public static final String RANKING_RECURSION = "recursion";
    public static final String RANKING_SEVERITY = "severity";
}
