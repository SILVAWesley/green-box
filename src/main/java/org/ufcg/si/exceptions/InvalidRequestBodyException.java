package org.ufcg.si.exceptions;

public class InvalidRequestBodyException extends GreenboxException {
	private static final long serialVersionUID = -2321083140485417108L;

	public InvalidRequestBodyException(String message) {
		super(message);
	}
	
	public InvalidRequestBodyException() {
		super("Invalid request body.");
	}
}
