package ncu.cc.digger.models;

import java.util.List;

public class ParentNSRRSet implements Comparable<ParentNSRRSet> {
    private final String nameServer;
    private final boolean authority;
    private final int numberOfRecords;
    private final List<NameServerRecord> rrset;

    public ParentNSRRSet(String nameServer, List<NameServerRecord> rrset) {
        this.nameServer = nameServer;
        this.rrset = rrset;
        this.numberOfRecords = rrset.size();
        this.authority = rrset.size() > 0 && rrset.get(0).isAuthorityAnswer();
    }

    public boolean haveRecord() {
        return numberOfRecords > 0;
    }

    public List<NameServerRecord> getRrset() {
        return rrset;
    }

    @Override
    public int compareTo(ParentNSRRSet o) {
        if (this.authority == o.authority) {
            return 0;
        } else {
            return this.authority ? -1 : 1;
        }
    }

    public String getNameServer() {
        return nameServer;
    }

    public boolean isAuthority() {
        return authority;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }
}