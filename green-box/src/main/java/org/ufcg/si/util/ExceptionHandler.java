package org.ufcg.si.util;

import javax.servlet.ServletException;

import org.ufcg.si.exceptions.InvalidDataException;
import org.ufcg.si.exceptions.InvalidPasswordException;
import org.ufcg.si.exceptions.InvalidUserException;
import org.ufcg.si.exceptions.InvalidUsernameException;
import org.ufcg.si.models.User;

public class ExceptionHandler {
	public static final String USER_USERNAME = "username";
	public static final String USER_EMAIL = "email";
	public static final String USER_PASSWORD = "password";
	
	public static void checkUserInDatabase(User user) throws ServletException {
		try {
			checkNotNullData(user);
		} catch(InvalidDataException e) {
			throw new ServletException("Invalid username or password.");
		}
	}
	
	public static void checkMatchingPassword(User loggingUser, User dbUser) throws ServletException {
		try {
			if (!loggingUser.getPassword().equals(dbUser.getPassword())) {
				throw new InvalidPasswordException("The given password is invalid.");
			}
		} catch(InvalidDataException e) {
			throw new ServletException("Invalid username or password.");
		}
	}
	
	public static void checkLoginFields(User user) throws ServletException {
		if ((user.getUsername() == null && user.getEmail() == null) || (user.getPassword() == null)) {
			throw new ServletException("Username/Email and password are both required.");
		}
	}
	
	public static void checkUser(User user, String... attributes) throws InvalidUserException {
		try {
			for (String attribute : attributes) {
				if (attribute.equals(USER_USERNAME)) {
					checkUsername(user.getUsername());
				}
				
				if (attribute.equals(USER_EMAIL)) {
					checkEmail(user.getEmail());
				}
				
				if (attribute.equals(USER_PASSWORD)) {
					checkPassword(user.getPassword());
				}
			}
		} catch(InvalidDataException e) {
			throw new InvalidUserException("User is not valid. " + e.getMessage());
		}
	}
	
	public static void checkUsername(String username) throws InvalidUsernameException {
		try {
			checkNotNullData(username);
			checkNotEmptyString(username);
		} catch(InvalidDataException e) {
			throw new InvalidUsernameException("The given username is invalid. " + e.getMessage());
		}
	}
	
	public static void checkEmail(String email) {
		try {
			checkNotNullData(email);
			checkNotEmptyString(email);
		} catch(InvalidDataException e) {
			throw new InvalidUsernameException("The given email is invalid. " + e.getMessage());
		}
	}
	
	public static void checkPassword(String password) throws InvalidUsernameException {
		try {
			checkNotNullData(password);
			checkNotEmptyString(password);
		} catch(InvalidDataException e) {
			throw new InvalidUsernameException("The given password is invalid. " + e.getMessage());
		}
	}
	
	public static void checkNotEmptyString(String checkingData) throws InvalidDataException {
		if (checkingData.trim().isEmpty()) { 
			throw new InvalidDataException("Empty data.");
		}
	}
	
	public static <T> void checkNotNullData(T checkingData) throws InvalidDataException {
		if (checkingData == null) { 
			throw new InvalidDataException("Null data.");
		}
	}
	
}
