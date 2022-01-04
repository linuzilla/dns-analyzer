package ncu.cc.digger.security;

import reactor.core.publisher.Mono;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface RecaptchaService {
	enum RecaptchaVerificationResultEnum {
		SUCCESSFUL, FAILED, ERROR;
	}
	Mono<RecaptchaVerificationResultEnum> validateResponseForID(String recaptcha, String remoteAddr);
}
