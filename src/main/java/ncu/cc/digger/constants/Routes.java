package ncu.cc.digger.constants;

public class Routes {
    private static final String REDIRECT = "redirect:";

    public static final String FAVICON = "/favicon.ico";
    public static final String ROBOTS = "/robots.txt";

    public static final String ACTUATOR = "/actuator";
    public static final String ROOT = "/";
    public static final String LOGIN = "/login";
    public static final String LOGIN_PROCESSING_URL = "/login";
    public static final String LOGOUT = "/logout";
    public static final String SIGNUP = "/signup";
    public static final String WEBJARS = "/webjars";
    public static final String RESOURCES = "/resources";
    public static final String DEVEL = "/devel";
    public static final String DEVEL_ROLE_MANIPULATE = "/role-manipulate";
    public static final String DEVEL_THREADS = "/threads";

    public static final String REPORT = "/report";

    public static final String UNIV = "/univ";
    public static final String UNIV_BROWSE = "/browse";
    public static final String UNIV_BACKEND = "/backend";
    public static final String UNIV_BACKEND_BROWSE = "/browse";

    public static final String VIEW = "/view";
    public static final String VIEW_INDEX = "/index";
    public static final String VIEW_SELECT = "/select";
    public static final String VIEW_BROWSE = "/browse";
    public static final String VIEW_STATISTICS = "/statistics";
    public static final String VIEW_BACKEND = "/backend";
    public static final String VIEW_BACKEND_BROWSE = "/browse";
    public static final String VIEW_BACKEND_SEVERITY = "/severity";
    public static final String VIEW_BACKEND_EDNS = "/edns";
    public static final String VIEW_BACKEND_IPV6 = "/ipv6";
    public static final String VIEW_BACKEND_DNSSEC = "/dnssec";
    public static final String VIEW_BACKEND_PROBLEMS = "/problems";
    public static final String VIEW_BACKEND_RRSET = "/rrset";
    public static final String VIEW_BACKEND_SERVERS = "/servers";
    public static final String VIEW_BACKEND_AXFR = "/axfr";
    public static final String VIEW_BACKEND_RECURSIVE = "/recursive";


    public static final String MANAGE = "/mgmt";

//    public static final String LOGIN_WITH_PORTAL = "/portal/signon";

    public static final String ERROR = "/error";
    public static final String ERROR403 = "/403";

    public static final String ABOUT = "/about";

    public static final String PUBLIC_API_PATH = "/public/apis";
    public static final String API_PATH = "/apis";

    public static final String API_QUERY = "/query";
    public static final String API_PUSH = "/push";
    public static final String API_EDNS = "/edns";
    public static final String API_STATUS = "/status";
    public static final String API_MISC = "/misc";
    public static final String API_SCORING = "/scoring";
    public static final String API_STATUS_EVENTS = "/events";
    public static final String API_STATUS_THREADS = "/thread";
    public static final String API_MISC_ENCODE = "/encode";
    public static final String API_EDNS_ANALYZE = "/analyze";

    public static final String BACKEND = "/backend";
    public static final String BACKEND_MENU = "/menu";
    public static final String BACKEND_COUNTRY = "/country";

    public static final String LANG = "/lang";

    public static final String FLASH = "/flash";
    public static final String EXPIRED = "/expired";

    public static final String ZONE = "/zone";
    public static final String QUERY = "/query";
    public static final String QUERY_RELOAD = "/reload";

    public static final String STATISTICS = "/statistics";
    public static final String STATISTICS_SEVERITY = "/severity";
    public static final String STATISTICS_UNIVERSITIES = "/univ";
    public static final String STATISTICS_RANKING = "/ranking";
    public static final String STATISTICS_RANKING_LIST = "/list";
    public static final String STATISTICS_UNIV_BACKEND = "/backend";
    public static final String STATISTICS_UNIVERSITIES_SEVERITY = "/severity";
    public static final String STATISTICS_UNIVERSITIES_EDNS = "/edns";
    public static final String STATISTICS_UNIVERSITIES_IPV6 = "/ipv6";
    public static final String STATISTICS_UNIVERSITIES_DNSSEC = "/dnssec";
    public static final String STATISTICS_UNIVERSITIES_PROBLEMS = "/problems";
    public static final String STATISTICS_UNIVERSITIES_RRSET = "/rrset";
    public static final String STATISTICS_UNIVERSITIES_SERVERS = "/servers";
    public static final String STATISTICS_UNIVERSITIES_AXFR = "/axfr";
    public static final String STATISTICS_UNIVERSITIES_RECURSIVE = "/recursive";
    public static final String STATISTICS_UNIVERSITIES_COUNTRY = "/country";
    public static final String STATISTICS_BACKEND = "/backend";
    public static final String STATISTICS_BACKEND_SEVERITY = "/severity";
    public static final String STATISTICS_BACKEND_EDNS = "/edns";
    public static final String STATISTICS_BACKEND_IPV6 = "/ipv6";
    public static final String STATISTICS_BACKEND_DNSSEC = "/dnssec";
    public static final String STATISTICS_BACKEND_PROBLEMS = "/problems";
    public static final String STATISTICS_BACKEND_RRSET = "/rrset";
    public static final String STATISTICS_BACKEND_SERVERS = "/servers";
    public static final String STATISTICS_BACKEND_AXFR = "/axfr";
    public static final String STATISTICS_BACKEND_RECURSIVE = "/recursive";
    public static final String STATISTICS_BACKEND_RANKING = "/ranking";

    public static String redirect(String ...routes) {
        if (routes.length == 0) {
            return REDIRECT + "/";
        } else if (routes.length == 1) {
            return REDIRECT + routes[0];
        } else {
            return REDIRECT + String.join("", routes);
        }
    }
}
