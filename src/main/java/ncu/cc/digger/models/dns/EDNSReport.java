package ncu.cc.digger.models.dns;

import java.util.Map;

public class EDNSReport {
    private String edns_compliance;
    private Map<String,String> edns_result;
    private boolean support_cookie;
    private boolean support_subnet;

    public String getEdns_compliance() {
        return edns_compliance;
    }

    public void setEdns_compliance(String edns_compliance) {
        this.edns_compliance = edns_compliance;
    }

    public Map<String, String> getEdns_result() {
        return edns_result;
    }

    public void setEdns_result(Map<String, String> edns_result) {
        this.edns_result = edns_result;
    }

    public boolean isSupport_cookie() {
        return support_cookie;
    }

    public void setSupport_cookie(boolean support_cookie) {
        this.support_cookie = support_cookie;
    }

    public boolean isSupport_subnet() {
        return support_subnet;
    }

    public void setSupport_subnet(boolean support_subnet) {
        this.support_subnet = support_subnet;
    }
}
