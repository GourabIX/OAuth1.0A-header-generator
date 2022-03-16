package com.gourabix.utility.oauth10a;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import com.gourabix.utility.oauth10a.common.AppConstants;
import com.gourabix.utility.oauth10a.services.OAuth10AHeaderGenerator;

/**
 * The entrypoint for the OAuthHeaderGeneratorApp.
 * 
 * @author Gourab Sarkar
 *
 */
public class OAuthHeaderGeneratorApp {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		final String consumerKey = System.getProperty(AppConstants.VAR_CONSUMER_KEY);
		final String consumerSecret = System.getProperty(AppConstants.VAR_CONSUMER_SECRET);
		final String httpMethod = System.getProperty(AppConstants.VAR_HTTP_METHOD);
		final String protectedUrl = System.getProperty(AppConstants.VAR_PROTECTED_URL);

		System.out.println("Generating OAuth header for the following payload:");
		System.out.println("Consumer key: " + consumerKey);
		System.out.println("Consumer secret: " + consumerSecret);
		System.out.println("HTTP method: " + httpMethod);
		System.out.println("URL: " + protectedUrl);
		System.out.println();

		OAuth10AHeaderGenerator oAuth10AHeaderGenerator = new OAuth10AHeaderGenerator(consumerKey, consumerSecret);
		String authHeader = oAuth10AHeaderGenerator.generateHeader(httpMethod, protectedUrl, new HashMap<>());

		System.out.println("\n--------------------------------------");
		System.out.println(authHeader);
		System.out.println("--------------------------------------\n");
	}

}
