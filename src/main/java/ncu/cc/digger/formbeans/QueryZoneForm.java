package ncu.cc.digger.formbeans;

import ncu.cc.commons.validators.ZoneOrURL;

import javax.validation.constraints.NotNull;

public class QueryZoneForm {
    @NotNull
    @ZoneOrURL
    private String zone;
    private String recaptchaResponse;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRecaptchaResponse() {
        return recaptchaResponse;
    }

    public void setRecaptchaResponse(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
    }
}
