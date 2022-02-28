package com.gourabix.utility;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import com.gourabix.utility.oauth10a.OAuth10AHeaderGenerator;

/**
 * The entrypoint for the OAuthHeaderGeneratorApp.
 * 
 * @author gourab
 *
 */
public class OAuthHeaderGeneratorApp {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		OAuth10AHeaderGenerator oAuth10AHeaderGenerator = new OAuth10AHeaderGenerator("test", "test");
		String authHeader = oAuth10AHeaderGenerator.generateHeader("GET", "https://www.google.com/test",
				new HashMap<>());
		System.out.println(authHeader);
	}

}
