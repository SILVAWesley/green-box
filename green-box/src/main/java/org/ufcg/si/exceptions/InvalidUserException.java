package org.ufcg.si.exceptions;

public class InvalidUserException extends RuntimeException {
	private static final long serialVersionUID = -7489205867453612742L;
	
	public InvalidUserException(String message) {
		super(message);
	}
	
}
