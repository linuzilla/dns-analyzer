package ncu.cc.digger.security;

import ncu.cc.digger.constants.ValueConstants;
import ncu.cc.digger.models.RecaptchaResponse;
import ncu.cc.digger.models.RecaptchaVerificationPDU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Service
public class RecaptchaServiceImpl implements RecaptchaService {
	private static final String USER_AGENT = "Reactive Recaptcha Client/1.0";

	@Value(ValueConstants.GOOGLE_RECPATCHA_KEY_SECRET)
	private String secretKey;
	@Value(ValueConstants.GOOGLE_RECAPTCHA_VERIFY_URL)
	private String verifyUrl;
	@Value(ValueConstants.GOOGLE_RECAPTCHA_RESPONSE_PARAMETER)
	private String responseParameter;
	@Value(ValueConstants.GOOGLE_RECPATCHA_API_SCRIPT)
	private String apiScript;
	private final WebClient webClient;

	private static final Logger logger = LoggerFactory.getLogger(RecaptchaServiceImpl.class);

	public RecaptchaServiceImpl() {
		this.webClient = WebClient
				.builder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.build();
	}

	@Override
	public Mono<RecaptchaVerificationResultEnum> validateResponseForID(String recaptcha, String remoteAddr) {
		if (recaptcha == null || "".equals(recaptcha)) {
			return Mono.just(RecaptchaVerificationResultEnum.FAILED);
		}

		RecaptchaVerificationPDU pdu = new RecaptchaVerificationPDU(secretKey, recaptcha);

		MultiValueMap<String, String> postData = new LinkedMultiValueMap<>();

		postData.add("secret", pdu.getSecret());
		postData.add("response", pdu.getResponse());

		// WebClient.RequestBodySpec request =
		return webClient.method(HttpMethod.POST)
				.uri(verifyUrl)
				.accept(MediaType.APPLICATION_JSON, MediaType.ALL)
				.acceptCharset(StandardCharsets.UTF_8)
				.body(BodyInserters.fromFormData(postData))
				.retrieve()
				.bodyToMono(RecaptchaResponse.class)
				.map(res -> res.success
						? RecaptchaVerificationResultEnum.SUCCESSFUL
						: RecaptchaVerificationResultEnum.FAILED);




//		try {
//			HttpClient client = HttpClientBuilder.create().build();
//			HttpPost request = new HttpPost(verifyUrl);
//			// request.addHeader("Accept", "application/json");
//			request.addHeader("Accept", "*/*");
//			request.addHeader("User-Agent", "NCU Portal/1.0");
//
//			// request.addHeader("Content-Type", "application/json");
//			request.addHeader("Content-Type", "application/x-www-form-urlencoded");
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//
//			params.add(new BasicNameValuePair("secret", pdu.getSecret()));
//			params.add(new BasicNameValuePair("response", pdu.getResponse()));
//
//			request.setEntity(new UrlEncodedFormEntity(params));
//
//			HttpResponse response = client.execute(request);
//
//			// logger.info("Response Code : " + response.getStatusLine().getStatusCode());
//
//			HttpEntity entity = response.getEntity();
//
//			if (entity != null) {
//				BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
//
//				StringBuffer result = new StringBuffer();
//				String line = "";
//				while ((line = rd.readLine()) != null) {
//					result.append(line);
//				}
//
//				String jsonString = result.toString();
//
//				logger.info(jsonString);
//				Gson gson = new Gson();
//				RecaptchaResponse res = gson.fromJson(jsonString, RecaptchaResponse.class);
//
//				return res.success ? RecaptchaVerificationResultEnum.SUCCESSFUL : RecaptchaVerificationResultEnum.FAILED;
//			} else {
//				logger.error("no response");
//				return RecaptchaVerificationResultEnum.ERROR;
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			// Something wrong! so, just let it go!
//			return RecaptchaVerificationResultEnum.ERROR;
//		}
	}

}
