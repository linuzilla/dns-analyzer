package ncu.cc.digger.utils;

import ncu.cc.commons.validators.ZoneOrURLValidator;
import ncu.cc.commons.validators.ZoneValidValidator;
import ncu.cc.digger.models.NameServerRecord;
import ncu.cc.digger.models.dns.FlatResourceRecord;
import ncu.cc.digger.models.dns.SOARecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNSUtil {
    private static final Logger logger = LoggerFactory.getLogger(DNSUtil.class);

    public static Pattern SOA_RECORD_PATTERN;

    static {
        SOA_RECORD_PATTERN = Pattern.compile("^(\\S+)\\s+(\\S+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*$");
    }

    public static String stripTrailingDot(String zone) {
        return zone.toLowerCase().replaceAll("\\.+$", "");
    }

    public static String zoneNameNormalize(String zone) {
        // change to lower case add add a tailing dot
        return stripTrailingDot(zone) + ".";
    }

    public static String retrieveCountryCode(String zone) {
        int pos = zone.lastIndexOf(".");

        return pos > 0 ? zone.substring(pos + 1) : "";
    }

    public static String parentZone(String zone) {
        return zone.substring(zone.indexOf(".") + 1);
    }

    public static String soaEmail(String email) {
        int pos = email.indexOf("\\.");

        String leading = "";

        while (pos >= 0) {
            leading += email.substring(0, pos) + ".";
            email = email.substring(pos + 2);
            System.out.println("leading: " + leading + ", email: " + email);
            pos = email.indexOf("\\.");
        }

        pos = email.indexOf(".");

        return leading + email.substring(0, pos) + "@" + email.substring(pos + 1);
    }

    public static SOARecord toSoaRecord(String data) {
        Matcher matcher = SOA_RECORD_PATTERN.matcher(data);

        if (matcher.matches()) {
            SOARecord soaRecord = new SOARecord();

            soaRecord.setEmail(soaEmail(matcher.group(2).toLowerCase()));
            soaRecord.setMaster_name(matcher.group(1).toLowerCase());
            soaRecord.setSerial(Long.parseLong(matcher.group(3).toLowerCase()));
            soaRecord.setRefresh(Integer.parseInt(matcher.group(4).toLowerCase()));
            soaRecord.setRetry(Integer.parseInt(matcher.group(5).toLowerCase()));
            soaRecord.setExpire(Integer.parseInt(matcher.group(6).toLowerCase()));
            soaRecord.setTtl(Integer.parseInt(matcher.group(7).toLowerCase()));
            return soaRecord;
        } else {
            return null;
        }
    }

    public static List<FlatResourceRecord> nameServerRecordsToFlatResourceRecords(List<NameServerRecord> nameServerRecords) {
        List<FlatResourceRecord> list = new ArrayList<>();

        nameServerRecords.forEach(nameServerRecord -> {

            if (nameServerRecord.getIpv4Address() != null) {
                FlatResourceRecord flatResourceRecord = new FlatResourceRecord();

                flatResourceRecord.setName(nameServerRecord.getDomainName());
                flatResourceRecord.setAddress(nameServerRecord.getIpv4Address());
                flatResourceRecord.setIs_v6(false);
                flatResourceRecord.setReported_by(nameServerRecord.getReportedBy());
                list.add(flatResourceRecord);
            }

            if (nameServerRecord.getIpv6Address() != null) {
                FlatResourceRecord flatResourceRecord = new FlatResourceRecord();

                flatResourceRecord.setName(nameServerRecord.getDomainName());
                flatResourceRecord.setAddress(nameServerRecord.getIpv6Address());
                flatResourceRecord.setIs_v6(true);
                flatResourceRecord.setReported_by(nameServerRecord.getReportedBy());
                list.add(flatResourceRecord);
            }

            if (nameServerRecord.getIpv4Address() == null && nameServerRecord.getIpv6Address() == null) {
                FlatResourceRecord flatResourceRecord = new FlatResourceRecord();

                flatResourceRecord.setName(nameServerRecord.getDomainName());
                flatResourceRecord.setAddress(null);
                flatResourceRecord.setIs_v6(false);
                flatResourceRecord.setReported_by(nameServerRecord.getReportedBy());
                list.add(flatResourceRecord);
            }
        });

        return list;
    }

    public static Optional<String> verifyOrRetrieveZone(String input) {
        var inputZone = input.toLowerCase().trim().replaceAll("\\.+$", "");

        if (ZoneValidValidator.COMMON_DOMAIN_PATTERN.matcher(inputZone).matches()) {
            logger.trace("match domain pattern: {}", inputZone);
            return Optional.of(DNSUtil.stripTrailingDot(inputZone));
        } else {
            Matcher matcher = ZoneOrURLValidator.COMMON_URL_PATTERN.matcher(inputZone);

            if (matcher.matches()) {
                var sliced = matcher.group(ZoneOrURLValidator.ZONE_GROUP_NUMBER);

                logger.trace("got [ {} ] from: {}", sliced, inputZone);

                return Optional.of(DNSUtil.stripTrailingDot(sliced));
            } else {
                return Optional.empty();
            }
        }
    }

//    public static void main(String[] args) {
//        System.out.println(DNSUtil.soaEmail("john\\.doe.example.com"));
//        System.out.println(DNSUtil.soaEmail("john\\.doe\\.example.com"));
//        System.out.println(DNSUtil.soaEmail("john.doe.example.com"));
//    }
}
