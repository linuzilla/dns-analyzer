package ncu.cc.digger.models;

import java.util.List;
import java.util.Optional;

public enum SeverityLevel {
    NORMAL(0),
    INFO(1),
    LOW(2),
    MEDIUM(3),
    HIGH(4),
    URGENT(5),
    FATAL(6);

    private final int level;

    SeverityLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static Optional<SeverityLevel> byLevel(int level) {
        return List.of(values()).stream().filter(severityLevel -> severityLevel.level == level).findFirst();
    }

    public static Optional<SeverityLevel> byName(String level) {
        return List.of(values()).stream().filter(severityLevel -> severityLevel.name().equalsIgnoreCase(level)).findFirst();
    }
}
