package com.gourabix.utility.oauth10a.exceptions;

/**
 * The exception to be thrown when invalid OAuth 1.0A credentials are specified.
 *
 * @author Gourab Sarkar
 */
public class InvalidOAuthConsumerCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 7983672612970882553L;

	public InvalidOAuthConsumerCredentialsException(String message) {
		super(message);
	}

}
