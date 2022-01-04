package ncu.cc.digger.constants;

import java.util.stream.Stream;

public enum LocaleEnum {
    EN_US("en_US", 0),
    ZH_TW("zh_TW", 1);

    private final String value;
    private final int code;

    LocaleEnum(String value, int code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }

    public static LocaleEnum toLocale(String locale) {
        return Stream.of(values())
                .filter(localeEnum -> localeEnum.value.equalsIgnoreCase(locale))
                .findFirst()
                .orElseGet(() -> locale.toLowerCase().startsWith("zh") ? ZH_TW : EN_US);
    }
}
