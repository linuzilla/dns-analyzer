package ncu.cc.digger.models;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class RecaptchaVerificationPDU {
	private String secret;
	private String response;
	// public String remoteip;
	
	public RecaptchaVerificationPDU() {
		super();
	}
	
	public RecaptchaVerificationPDU(String secret, String response) {
		super();
		this.secret = secret;
		this.response = response;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
