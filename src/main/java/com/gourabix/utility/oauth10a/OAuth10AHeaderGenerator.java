package com.gourabix.utility.oauth10a;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * The utility class to generate OAuth authorization header contents. This
 * authorization header conforms to OAuth 1.0A specification.
 * 
 * @author gourab
 */
public class OAuth10AHeaderGenerator {

	private final String consumerKey;

	private final String consumerSecret;

	private final String signatureMethod;

	private final String oauthVersion;

	public OAuth10AHeaderGenerator(String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.signatureMethod = AppConstants.OAUTH_SIGNATURE_ALG_HEADER;
		this.oauthVersion = AppConstants.OAUTH_VER_HEADER;
	}

	/**
	 * Generates OAuth 1.0A header which can be passed as Authorization header.
	 * 
	 * @param httpMethod
	 * @param url
	 * @param requestParams
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public String generateHeader(String httpMethod, String url, Map<String, String> requestParams)
			throws NoSuchAlgorithmException, InvalidKeyException {
		StringBuilder base = new StringBuilder();

		final String nonce = getNonce();
		final String timestamp = getTimestamp();
		final String baseSignatureString = generateSignatureBaseString(httpMethod, url, requestParams, nonce,
				timestamp);
		final String signature = generateSignature(baseSignatureString);

		base.append(AppConstants.OAUTH_AUTH_PREAMBLE);
		append(base, AppConstants.OAUTH_CONSUMER_KEY_HEADER, consumerKey);
		append(base, AppConstants.OAUTH_SIGNATURE_METHOD_HEADER, signatureMethod);
		append(base, AppConstants.OAUTH_TIMESTAMP_HEADER, timestamp);
		append(base, AppConstants.OAUTH_NONCE_HEADER, nonce);
		append(base, AppConstants.OAUTH_VERSION_HEADER, oauthVersion);
		append(base, AppConstants.OAUTH_SIGNATURE_HEADER, signature);
		base.deleteCharAt(base.length() - 1);

		System.out.println(
				"OAuth 1.0A header generated successfully. Paste the contents with the header key as 'Authorization'.");
		return base.toString();
	}

	/**
	 * Generate base signature string to generate the oauth_signature.
	 * 
	 * @param httpMethod
	 * @param url
	 * @param requestParams
	 * @return String
	 */
	private String generateSignatureBaseString(final String httpMethod, final String url,
			final Map<String, String> requestParams, final String nonce, final String timestamp) {
		Map<String, String> params = new HashMap<>();
		requestParams.entrySet().forEach(entry -> putEncoded(params, entry.getKey(), entry.getValue()));
		putEncoded(params, AppConstants.OAUTH_CONSUMER_KEY_HEADER, consumerKey);
		putEncoded(params, AppConstants.OAUTH_NONCE_HEADER, nonce);
		putEncoded(params, AppConstants.OAUTH_SIGNATURE_METHOD_HEADER, signatureMethod);
		putEncoded(params, AppConstants.OAUTH_TIMESTAMP_HEADER, timestamp);
		putEncoded(params, AppConstants.OAUTH_VERSION_HEADER, oauthVersion);

		Map<String, String> sortedParams = params.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		StringBuilder baseSignatureString = new StringBuilder();
		sortedParams.entrySet().forEach(entry -> baseSignatureString.append(entry.getKey())
				.append(AppConstants.EQUALS_LITERAL).append(entry.getValue()).append(AppConstants.AMPERSAND_LITERAL));

		baseSignatureString.deleteCharAt(baseSignatureString.length() - 1);
		return httpMethod.toUpperCase() + AppConstants.AMPERSAND_LITERAL + percentEncode(url)
				+ AppConstants.AMPERSAND_LITERAL + percentEncode(baseSignatureString.toString());
	}

	/**
	 * Encrypts and encodes the base signature string using HMAC-SHA1 algorithm to
	 * generate the OAuth signature as per OAuth 1.0A specification.
	 * 
	 * @param signatureBaseString
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	private String generateSignature(final String signatureBaseString)
			throws NoSuchAlgorithmException, InvalidKeyException {
		final String secret = new StringBuilder().append(percentEncode(consumerSecret))
				.append(AppConstants.AMPERSAND_LITERAL).toString();
		final byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		final SecretKey key = new SecretKeySpec(keyBytes, AppConstants.HMAC_SHA1_ALG);

		final Mac mac = Mac.getInstance(AppConstants.HMAC_SHA1_ALG);
		mac.init(key);
		final byte[] signatureBytes = mac.doFinal(signatureBaseString.getBytes(StandardCharsets.UTF_8));
		return new String(Base64.getEncoder().encode(signatureBytes));
	}

	/**
	 * Percentage encode String as per RFC 3986, Section 2.1.
	 * 
	 * @param value
	 * @return String
	 */
	private String percentEncode(String value) {
		String encoded = "";
		try {
			encoded = URLEncoder.encode(value, AppConstants.ENCODING_UTF8);
		} catch (UnsupportedEncodingException uee) {
			System.err.println("Percent encoding failed. The encoding specified is unsupported.");
			System.err.println("Raw exception message: " + uee.getMessage());
		}

		// Process encoded string to avoid issues with the auth provider.
		StringBuilder builder = new StringBuilder();
		char focus;
		for (int i = 0; i < encoded.length(); i++) {
			focus = encoded.charAt(i);
			if (focus == AppConstants.ASTERISK_LITERAL) {
				builder.append(AppConstants.ASTERISK_PE_REPLACEMENT);
			} else if (focus == AppConstants.PLUS_LITERAL) {
				builder.append(AppConstants.PLUS_PE_REPLACEMENT);
			} else if (focus == AppConstants.PERCENT7E_LITERAL.charAt(0) && i + 1 < encoded.length()
					&& encoded.charAt(i + 1) == AppConstants.PERCENT7E_LITERAL.charAt(1)
					&& encoded.charAt(i + 2) == AppConstants.PERCENT7E_LITERAL.charAt(2)) {
				// if "%7" is present, then it's definitely "%7E".
				builder.append(AppConstants.PERCENT7E_PE_REPLACEMENT);
			} else {
				builder.append(focus);
			}
		}

		return builder.toString();
	}

	/**
	 * Encodes and puts entries inside the specified {@link Map}.
	 * 
	 * @param map
	 * @param key
	 * @param value
	 */
	private void putEncoded(Map<String, String> map, String key, String value) {
		map.put(percentEncode(key), percentEncode(value));
	}

	/**
	 * Encodes and appends the specified key-value pair to a {@link StringBuilder}.
	 * 
	 * @param builder
	 * @param key
	 * @param value
	 */
	private void append(StringBuilder builder, String key, String value) {
		builder.append(percentEncode(key)).append("=\"").append(percentEncode(value)).append("\",");
	}

	/**
	 * Generates the "NONCE" attribute for the OAuth 1.0A header.
	 * 
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	private String getNonce() throws NoSuchAlgorithmException {
		return SecureRandom.getInstanceStrong().nextInt() + getTimestamp();
	}

	/**
	 * Generates timestamp as per OAuth 1.0A specification.
	 * 
	 * @return String
	 */
	private String getTimestamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

}
