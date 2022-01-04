package ncu.cc.digger.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class RecaptchaResponse {
	public boolean success;
	
	@SerializedName("error-codes")
	public String []errorCodes;
	
	@SerializedName("challenge_ts")
	public String challengeTs;
	
	public String hostname;

}
