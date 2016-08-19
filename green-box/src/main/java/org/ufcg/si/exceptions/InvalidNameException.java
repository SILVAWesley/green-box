package org.ufcg.si.exceptions;

public class InvalidNameException extends RuntimeException {
	private static final long serialVersionUID = -7489205867453612742L;
	
	public InvalidNameException(String message) {
		super(message);
	}
	
}
