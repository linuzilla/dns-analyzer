package ncu.cc.digger.models;

import java.util.regex.Matcher;

public class ResourceRecord {
    public static final String RESOURCE_RECORD_PATTERN = "(\\S+)\\s+(\\d+)\\s+IN\\s+(\\w+)\\s+(.+)";

    private String rawData;
    private String domainName;
    private long ttl;
    private String recordClass;  // IN
    private String recordType;   // A, AAAA, SOA, NS, PTR ...
    private String data;

    public ResourceRecord(Matcher matcher) {
        this.domainName = matcher.group(1);
        this.ttl = Long.parseLong(matcher.group(2));
        this.recordType = matcher.group(3);
        this.data = matcher.group(4);
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String getRecordClass() {
        return recordClass;
    }

    public void setRecordClass(String recordClass) {
        this.recordClass = recordClass;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
