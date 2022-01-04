package ncu.cc.digger.models;

public class NameServerRecord {
    private final String reportedBy;
    private final String zone;
    private final String domainName;
    private String ipv4Address;
    private String ipv6Address;
    private final boolean isAuthorityAnswer;

    public NameServerRecord(String reportedBy, String zone, String domainName, boolean isAuthorityAnswer) {
        this.reportedBy = reportedBy;
        this.zone = zone;
        this.domainName = domainName;
        this.isAuthorityAnswer = isAuthorityAnswer;
        this.ipv4Address = null;
        this.ipv6Address = null;
    }

    public NameServerRecord(NameServerRecord another) {
        this.reportedBy = another.reportedBy;
        this.zone = another.zone;
        this.domainName = another.domainName;
        this.ipv4Address = another.ipv4Address;
        this.ipv6Address = another.ipv6Address;
        this.isAuthorityAnswer = another.isAuthorityAnswer;
    }

    public boolean haveAddress() {
        return ipv4Address != null || ipv6Address != null;
    }

    public String getAddress() {
        if (ipv4Address != null) {
            return ipv4Address;
        } else if (ipv6Address != null) {
            return ipv6Address;
        } else {
            return null;
        }
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public String getZone() {
        return zone;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getIpv4Address() {
        return ipv4Address;
    }

    public void setIpv4Address(String ipv4Address) {
        this.ipv4Address = ipv4Address;
    }

    public String getIpv6Address() {
        return ipv6Address;
    }

    public void setIpv6Address(String ipv6Address) {
        this.ipv6Address = ipv6Address;
    }

    public boolean isAuthorityAnswer() {
        return isAuthorityAnswer;
    }
}
