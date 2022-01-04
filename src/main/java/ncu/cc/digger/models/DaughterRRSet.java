package ncu.cc.digger.models;

import java.util.List;

public class DaughterRRSet extends NameServerRecord {
    private List<NameServerRecord>  nameServers;

    public DaughterRRSet(NameServerRecord another, List<NameServerRecord> nameServers) {
        super(another);
        this.nameServers = nameServers;
    }

    public List<NameServerRecord> getNameServers() {
        return nameServers;
    }

    public void setNameServers(List<NameServerRecord> nameServers) {
        this.nameServers = nameServers;
    }
}
