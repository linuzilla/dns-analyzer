package ncu.cc.digger.constants;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class ValueConstants {
    public static final String CONTEXT_PATH = "${server.servlet.context-path}";

    public static final String PORTAL_WEBCOMM_KEY_URL = "${portal.webcomm.key-url}";

    public static final String APPLICATION_ACCESS_LOG = "${application.access-log}";
    public static final String DIG_CACHE_TIME_TO_LIVE = "${application.dig-cache-ttl}";
    public static final String APPLICATION_DEBUG = "${application.debug}";
    public static final String EDNS_COMPLIANCE_TEST_TEMPLATES = "${application.edns-compliance-test-templates}";
    public static final String PROBLEM_SPEC_ZH_TW = "${application.problem-spec-zh-tw}";
    public static final String PROBLEM_SPEC_EN_US = "${application.problem-spec-en-us}";
    public static final String COUNTRY_CODES = "${application.country-codes}";
    public static final String ENABLE_IPV6_NAME_SERVER = "${application.enable-v6-name-server}";
    public static final String QUEYE_WORKER = "${application.queue-worker}";
    public static final String APPLICATION_MONTHLY_SCORING = "${application.monthly-scoring}";

    public static final String SCHEDULER_SIZE = "${application.scheduler}";

    public static final String GOOGLE_RECAPTCHA_KEY_SITE = "${google.recaptcha.key.site}";
    public static final String GOOGLE_RECPATCHA_KEY_SECRET = "${google.recaptcha.key.secret}";
    public static final String GOOGLE_RECPATCHA_API_SCRIPT = "${google.recaptcha.api-script}";
    public static final String GOOGLE_RECAPTCHA_VERIFY_URL = "${google.recaptcha.verify-url}";
    public static final String GOOGLE_RECAPTCHA_RESPONSE_PARAMETER = "${google.recaptcha.response-parameter}";
}
