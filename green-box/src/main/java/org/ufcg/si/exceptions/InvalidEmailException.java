package org.ufcg.si.exceptions;

public class InvalidEmailException extends InvalidDataException {
	private static final long serialVersionUID = -2274868151023290395L;

	public InvalidEmailException(String message) {
		super(message);
	}

}
