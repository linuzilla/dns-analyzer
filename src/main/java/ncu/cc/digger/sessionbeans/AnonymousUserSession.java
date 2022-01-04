package ncu.cc.digger.sessionbeans;

public class AnonymousUserSession {
    public enum ZoneStatusEnum {
        UNKNOWN, NOT_IN_DB, IN_DB, WAITING, RELOAD
    }
    private String queryZone;
    private ZoneStatusEnum zoneStatusEnum;

    public String getQueryZone() {
        return queryZone;
    }

    public void setQueryZone(String queryZone) {
        this.queryZone = queryZone;
    }

    public ZoneStatusEnum getZoneStatusEnum() {
        return zoneStatusEnum;
    }

    public void setZoneStatusEnum(ZoneStatusEnum zoneStatusEnum) {
        this.zoneStatusEnum = zoneStatusEnum;
    }
}
