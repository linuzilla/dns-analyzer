package ncu.cc.digger.models;

import java.util.List;

public enum EDNSExpects {
    SOA("SOA"),
    NOERROR("NOERROR"),
    OPT_VERSION_ZERO("OPT record with version set to 0"),
    EDNS_OVER_IPV6("EDNS over IPv6"),
    BADVERS("BADVERS"),
    NO_SOA("not to see SOA"),
    UDP_SIZE_LE_512("UDP DNS message size to be less than or equal to 512 bytes"),
    OPTION_NOT_PRESENT("that the option will not be present in response"),
    DO_FLAG("DO flag in response if RRSIG is present in response"),
    Z_BIT_TO_BE_CLEAR("Z bit to be clear in response");

    private final String expectString;

    EDNSExpects(String expectString) {
        this.expectString = expectString;
    }

    public static EDNSExpects byString(String value) {
        return List.of(values()).stream()
                .filter(ednsExpects -> ednsExpects.expectString.equals(value))
                .findFirst()
                .orElseThrow();
    }
}
