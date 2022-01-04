package ncu.cc.digger.constants;

public class DigConstants {
    public static final String COOKIE_GOOD = "good";

    public static final String QUERY_RESPONSE = "qr";  // QR specifies whether this message is a query (0), or a response (1)
    public static final String RECURSION_DESIRED = "rd";
    public static final String AUTHORITATIVE_ANSWER = "aa";
    public static final String AUTHENTICATED_DATA = "ad";  // DNSSEC-related flags
    public static final String CHECKING_DISABLED = "cd";   // DNSSEC-related flags
    public static final String TRUNCATION = "tc";
    public static final String RECURSION_AVAILABLE = "ra";
}
