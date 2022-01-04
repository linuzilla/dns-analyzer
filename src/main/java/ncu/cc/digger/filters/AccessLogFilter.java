package ncu.cc.digger.filters;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
//@Component
public class AccessLogFilter { //implements Filter {
//    private PrintWriter pw = new PrintWriter(System.out);
//    private boolean active = true;
//
//    public AccessLogFilter(@Value(ValueConstants.APPLICATION_ACCESS_LOG) boolean active) {
//        this.active = active;
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        if (active) {
//            HttpServletRequest req = (HttpServletRequest) servletRequest;
//
//            SimpleDateFormat dateFormate = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss Z]");
//
//            String requestURI = req.getRequestURI();
//
//            if (pw != null) {
//                String user = "-";
//
//                String referer = req.getHeader("referer");
//                String useragent = req.getHeader("user-agent");
//                String queryString = req.getQueryString();
//
//                if (referer == null) referer = "";
//                if (useragent == null) useragent = "";
//                if (queryString == null) {
//                    queryString = "";
//                } else if (! queryString.isEmpty()) {
//                    queryString = "?" + queryString;
//                }
//
//                pw.println(req.getRemoteAddr() + " " + user + " - " +
//                        dateFormate.format(Calendar.getInstance().getTime()) + " \"" +
//                        req.getMethod() + " " + requestURI + queryString + " " +
//                        req.getProtocol() + "\" \"" + referer + "\" \"" + useragent + "\"");
//                pw.flush();
//            }
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    public boolean isActive() {
//        return active;
//    }
//
//    public void setActive(boolean active) {
//        this.active = active;
//    }
}
