package ncu.cc.digger.constants;

public enum DigCommands {
    QUERY_SOA("dig +norec +noad +noedns soa {zone} @{server}"),
    ZONE_TRANSFER("dig +noedns +nocookie axfr {zone} @{server}"),
    FIND_NS("dig +nocookie +noedns ns {zone}"),
    FIND_ANY("dig +nocookie +noedns any {zone}"),
    FIND_NS_FROM("dig +nocookie +noedns ns {zone} @{server}"),
    FIND_A("dig +nocookie +noedns a {zone}"),
    FIND_AAAA("dig +nocookie +noedns aaaa {zone}"),
    FIND_A_FROM("dig +nocookie +noedns a {zone} @{server}"),
    DNSSEC_QUERY("dig +dnssec +multi {zone} @{server}");

    private final String command;

    DigCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String toCommand(String zone) {
        return this.toCommand(zone, null);
    }

    public String toCommand(String zone, String server) {
        String cmd = this.command;

        if (zone != null) {
            cmd = cmd.replace("{zone}", zone);
        }

        if (server != null) {
            cmd = cmd.replace("{server}", server);
        }

        return cmd;
    }
}
