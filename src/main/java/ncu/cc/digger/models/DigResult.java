package ncu.cc.digger.models;

import com.google.gson.Gson;
import ncu.cc.commons.utils.StringUtil;
import ncu.cc.digger.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class DigResult {
    private static final Logger logger = LoggerFactory.getLogger(DigResult.class);
    private static final String SPACES = "\\s+";

    public enum FailedType {
        UNKNOWN("unknown", false),
        SUCCESS("success", false),
        CONNECTION_TIMEOUT("timeout"),
        CONNECTION_FAILED("connection-refused"),
        TRANSFER_FAILED;

        private final String value;
        private final boolean failed;

        FailedType(String value, boolean failed) {
            this.value = value;
            this.failed = failed;
        }

        FailedType(String value) {
            this.value = value;
            this.failed = true;
        }

        FailedType() {
            this.value = name().toLowerCase();
            this.failed = true;
        }

        public String getValue() {
            return value;
        }

        public boolean isFailed() {
            return failed;
        }
    }

    private int query;
    private int answer;
    private int authority;
    private int additional;
    private Integer version;
    private Integer udp;
    private String opcode;
    private String status;
    private String cookie;
    private String subnet;
    private String tempFlags;
    private String ednsTempFlags;
    private String failedMessage;
    private int messageSize;
    private List<ResourceRecord> axfrRecords = new ArrayList<>();
    private List<ResourceRecord> answerRecords = new ArrayList<>();
    private List<ResourceRecord> authorityRecords = new ArrayList<>();
    private List<ResourceRecord> additionalRecords = new ArrayList<>();
    private FailedType failedType = FailedType.UNKNOWN;
    private Set<String> flags = Collections.emptySet();
    private Set<String> ednsFlags = Collections.emptySet();
    private String mbz = null;
    private String opt = null;
    private boolean havePseudoSection = false;
    private int transferRecords;
    private int transferMessages;
    private long elapsedTime;

    public void postConstruct() {
        if (StringUtil.isNotEmpty(this.tempFlags)) {

            logger.trace("flags: {}", this.tempFlags);
            String[] parts = this.tempFlags.split(Constants.SEMICOLON);

            this.flags = Set.of(parts[0].split(SPACES));

            IntStream.range(1, parts.length)
                    .mapToObj(i -> parts[i].trim().split(Constants.COLON))
                    .filter(entries -> entries.length > 1 && "MBZ".equals(entries[0]))
                    .forEach(entries -> this.mbz = entries[1].trim());
        }

        if (StringUtil.isNotEmpty(this.ednsTempFlags)) {
            this.ednsFlags = Set.of(this.ednsTempFlags.split(SPACES));
        }
    }

    public static DigResult fromJSON(String jsonString) {
        return new Gson().fromJson(jsonString, DigResult.class);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public int getQuery() {
        return query;
    }

    public void setQuery(int query) {
        this.query = query;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getAdditional() {
        return additional;
    }

    public void setAdditional(int additional) {
        this.additional = additional;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getUdp() {
        return udp;
    }

    public void setUdp(Integer udp) {
        this.udp = udp;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getTempFlags() {
        return tempFlags;
    }

    public void setTempFlags(String tempFlags) {
        this.tempFlags = tempFlags;
    }

    public String getEdnsTempFlags() {
        return ednsTempFlags;
    }

    public void setEdnsTempFlags(String ednsTempFlags) {
        this.ednsTempFlags = ednsTempFlags;
    }

    public String getFailedMessage() {
        return failedMessage;
    }

    public void setFailedMessage(String failedMessage) {
        this.failedMessage = failedMessage;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public List<ResourceRecord> getAxfrRecords() {
        return axfrRecords;
    }

    public void setAxfrRecords(List<ResourceRecord> axfrRecords) {
        this.axfrRecords = axfrRecords;
    }

    public List<ResourceRecord> getAnswerRecords() {
        return answerRecords;
    }

    public void setAnswerRecords(List<ResourceRecord> answerRecords) {
        this.answerRecords = answerRecords;
    }

    public List<ResourceRecord> getAuthorityRecords() {
        return authorityRecords;
    }

    public void setAuthorityRecords(List<ResourceRecord> authorityRecords) {
        this.authorityRecords = authorityRecords;
    }

    public List<ResourceRecord> getAdditionalRecords() {
        return additionalRecords;
    }

    public void setAdditionalRecords(List<ResourceRecord> additionalRecords) {
        this.additionalRecords = additionalRecords;
    }

    public FailedType getFailedType() {
        return failedType;
    }

    public void setFailedType(FailedType failedType) {
        this.failedType = failedType;
    }

    public Set<String> getFlags() {
        return flags;
    }

    public void setFlags(Set<String> flags) {
        this.flags = flags;
    }

    public Set<String> getEdnsFlags() {
        return ednsFlags;
    }

    public void setEdnsFlags(Set<String> ednsFlags) {
        this.ednsFlags = ednsFlags;
    }

    public String getMbz() {
        return mbz;
    }

    public void setMbz(String mbz) {
        this.mbz = mbz;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public int getTransferRecords() {
        return transferRecords;
    }

    public void setTransferRecords(int transferRecords) {
        this.transferRecords = transferRecords;
    }

    public int getTransferMessages() {
        return transferMessages;
    }

    public void setTransferMessages(int transferMessages) {
        this.transferMessages = transferMessages;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public boolean isHavePseudoSection() {
        return havePseudoSection;
    }

    public void setHavePseudoSection(boolean havePseudoSection) {
        this.havePseudoSection = havePseudoSection;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
