package org.ufcg.si.util;

import javax.servlet.ServletException;

import org.ufcg.si.exceptions.InvalidDataException;
import org.ufcg.si.exceptions.InvalidRequestBodyException;
import org.ufcg.si.exceptions.MissingItemException;
import org.ufcg.si.models.User;
import org.ufcg.si.util.requests.FileRequestBody;
import org.ufcg.si.util.requests.FolderRequestBody;

public class ExceptionHandler {
	public static final String USER_USERNAME = "username";
	public static final String USER_EMAIL = "email";
	public static final String USER_PASSWORD = "password";
	
	public static void checkNewFolderBody(FolderRequestBody requestBody) {
		if (Validator.isEmpty(requestBody.getUser())) {
			throw new InvalidRequestBodyException("Invalid user: " + requestBody.getUser() + ".");
		}
		
		String username = requestBody.getUser().getUsername();
		String folderName = requestBody.getFolderName();
		String folderPath = requestBody.getFolderPath();
		
		if (Validator.isUsernameInvalid(username)) {
			throw new InvalidRequestBodyException("Invalid username: " + username + ".");
		}
		
		if (Validator.isStorageItemNameInvalid(folderName)) {
			throw new InvalidRequestBodyException("Invalid name for folder: " + folderName + ".");
		}
		
		if (Validator.isEmpty(folderPath)) {
			throw new InvalidRequestBodyException("Invalid path for folder: " + folderPath + ".");
		}
	}
	
	public static void checkNewFileBody(FileRequestBody requestBody) {
		if (Validator.isEmpty(requestBody.getUser())) {
			throw new InvalidRequestBodyException("Invalid user: " + requestBody.getUser() + ".");
		}
		
		String username = requestBody.getUser().getUsername();
		String fileName = requestBody.getFileName();
		String filePath = requestBody.getFilePath();
		String fileExtension = requestBody.getFileExtension();
		
		if (Validator.isUsernameInvalid(username)) {
			throw new InvalidRequestBodyException("Invalid username: " + username + ".");
		}
		
		if (Validator.isStorageItemNameInvalid(fileName)) {
			throw new InvalidRequestBodyException("Invalid name for file: " + fileName + ".");
		}
		
		if (Validator.isEmpty(filePath)) {
			throw new InvalidRequestBodyException("Invalid path for file: " + filePath + ".");
		}
		
		if (Validator.isEmpty(fileExtension)) {
			throw new InvalidRequestBodyException("Invalid extension for file: " + fileExtension + ".");
		}
	}
	
	public static void checkEditFileBody(FileRequestBody requestBody) {
		if (Validator.isEmpty(requestBody.getUser())) {
			throw new InvalidRequestBodyException("Invalid user: " + requestBody.getUser() + ".");
		}
		
		String username = requestBody.getUser().getUsername();
		String fileName = requestBody.getFileName();
		String filePath = requestBody.getFilePath();
		
		if (Validator.isUsernameInvalid(username)) {
			throw new InvalidRequestBodyException("Invalid username: " + username + ".");
		}
		
		if (Validator.isStorageItemNameInvalid(fileName)) {
			throw new InvalidRequestBodyException("Invalid name for file: " + fileName + ".");
		}
		
		if (Validator.isEmpty(filePath)) {
			throw new InvalidRequestBodyException("Invalid path for file: " + filePath + ".");
		}
	}
	
	public static void checkRegistrationBody(User requestBody) throws InvalidRequestBodyException {
		String username = requestBody.getUsername();
		String email = requestBody.getEmail();
		String password = requestBody.getPassword();
		
		if (Validator.isUsernameInvalid(username)) {
			throw new InvalidRequestBodyException("Request with invalid username: " + username + ".");
		}
		
		if (Validator.isEmailInvalid(email)) {
			throw new InvalidRequestBodyException("Request with invalid email: " + email + ".");
		}
		
		if (Validator.isPasswordInvalid(password)) {
			throw new InvalidRequestBodyException("Request with invalid password.");
		}
	}
	public static void checkLoginBody(User requestBody) throws InvalidRequestBodyException {
		if (Validator.isEmpty(requestBody.getUsername()) && Validator.isEmpty(requestBody.getEmail())) {
			throw new InvalidRequestBodyException("Request without email or username.");
		}
		
		if (Validator.isEmpty(requestBody.getPassword())) {
			throw new InvalidRequestBodyException("Request without password.");
		}
	}
	
	public static void checkLoginSuccess(User requestBody, User dbUser) throws InvalidDataException {
		if (Validator.isEmpty(dbUser) || !dbUser.getPassword().equals(requestBody.getPassword())) {
			throw new InvalidDataException("Invalid username or password.");
		}
	}
	
	public static void checkUserInDatabase(User user) throws ServletException {
		if (Validator.isEmpty(user)) {
			throw new MissingItemException("User " + user + " not found in the database.");
		}
	}
}
