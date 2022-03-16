package com.gourabix.utility.oauth10a.common;

/**
 * The final class housing constants used in the application.
 * 
 * @author Gourab Sarkar
 *
 */
public final class AppConstants {

	public static final String OAUTH_SIGNATURE_ALG_HEADER = "HMAC-SHA1";
	public static final String OAUTH_VER_HEADER = "1.0";
	public static final String ENCODING_UTF8 = "UTF-8";

	public static final String OAUTH_CONSUMER_KEY_HEADER = "oauth_consumer_key";
	public static final String OAUTH_SIGNATURE_METHOD_HEADER = "oauth_signature_method";
	public static final String OAUTH_TIMESTAMP_HEADER = "oauth_timestamp";
	public static final String OAUTH_NONCE_HEADER = "oauth_nonce";
	public static final String OAUTH_VERSION_HEADER = "oauth_version";
	public static final String OAUTH_SIGNATURE_HEADER = "oauth_signature";
	public static final String HMAC_SHA1_ALG = "HmacSHA1";
	public static final String OAUTH_AUTH_PREAMBLE = "OAuth ";

	public static final String EQUALS_LITERAL = "=";
	public static final String AMPERSAND_LITERAL = "&";
	public static final char ASTERISK_LITERAL = '*';
	public static final String ASTERISK_PE_REPLACEMENT = "%2A";
	public static final char PLUS_LITERAL = '+';
	public static final String PLUS_PE_REPLACEMENT = "%20";
	public static final String PERCENT7E_LITERAL = "%7E";
	public static final String PERCENT7E_PE_REPLACEMENT = "~";

	public static final String VAR_CONSUMER_KEY = "consumerKey";
	public static final String VAR_CONSUMER_SECRET = "consumerSecret";
	public static final String VAR_HTTP_METHOD = "httpMethod";
	public static final String VAR_PROTECTED_URL = "url";

	private AppConstants() {
		// Constructor made private so this class cannot be instantiated.
	}

}
