package org.ufcg.si.exceptions;

import javax.servlet.ServletException;

import org.ufcg.si.beans.requests.AddNDeleteFileBean;
import org.ufcg.si.beans.requests.AddNDeleteFolderBean;
import org.ufcg.si.beans.requests.EditFileBean;
import org.ufcg.si.beans.requests.RegistrationBean;
import org.ufcg.si.beans.requests.RenameFileBean;
import org.ufcg.si.beans.requests.RenameFolderBean;
import org.ufcg.si.beans.requests.ShareFileBean;
import org.ufcg.si.models.User;


public class ExceptionHandler {
	public static final String USER_USERNAME = "username";
	public static final String USER_EMAIL = "email";
	public static final String USER_PASSWORD = "password";
	
	public static void checkAddFolderBody(AddNDeleteFolderBean body) {
		checkInvalidUser(body.getUser());
		
		String username = body.getUser().getUsername();
		String folderName = body.getFolderName();
		String folderPath = body.getFolderPath();
		
		checkInvalidUsername(username);
		checkInvalidStorageItemName(folderName);
		checkInvalidPath(folderPath);
	}
	
	public static void checkAddFileBody(AddNDeleteFileBean body) {
		checkInvalidUser(body.getUser());
		
		String username = body.getUser().getUsername();
		String fileName = body.getFileName();
		String filePath = body.getFilePath();
		String fileExtension = body.getFileExtension();
		
		checkInvalidUsername(username);
		checkInvalidStorageItemName(fileName);
		checkInvalidPath(filePath);
		checkInvalidExtension(fileExtension);
	}
	
	public static void checkEditFileBody(EditFileBean body) {
		checkInvalidUser(body.getUser());
		
		String username = body.getUser().getUsername();
		String fileName = body.getClickedFile().getName();
		String filePath = body.getFilePath();
		String newName = body.getFileName();
		String newExtension = body.getFileExtension();
		
		checkInvalidUsername(username);
		checkInvalidStorageItemName(fileName);		
		checkInvalidPath(filePath);
		
		if (!Validator.isEmpty(newName)) {
			checkInvalidStorageItemName(newName);
		}
	
		if (!Validator.isEmpty(newExtension)) {
			checkInvalidExtension(newExtension);
		}
	}
	
	public static void checkRenameFolderBody(RenameFolderBean body) {
		checkInvalidUser(body.getUser());
		
		String username = body.getUser().getUsername();
		String folderName = body.getOldName();
		String folderPath = body.getFolderPath();
		String newName = body.getNewName();
		
		checkInvalidUsername(username);
		checkInvalidStorageItemName(folderName);
		checkInvalidPath(folderPath);
		checkInvalidStorageItemName(newName);
	}
	
	public static void checkRegistrationBody(RegistrationBean requestBody) throws InvalidRequestBodyException {
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
	
	public static void checkRenameFileBody(RenameFileBean body) {
		checkInvalidUser(body.getUser());
		
		String username = body.getUser().getUsername();
		String fileName = body.getOldName();
		String filePath = body.getFolderPath();
		String newName = body.getNewName();
		
		checkInvalidUsername(username);
		checkInvalidStorageItemName(fileName);
		checkInvalidPath(filePath);
		checkInvalidStorageItemName(newName);
	}
	
	public static void checkShareFileBody(ShareFileBean body) {
		checkInvalidUser(body.getUser());
		checkInvalidUser(body.getUserSharedWith());
		
		String usernameOfSender = body.getUser().getUsername();
		String usernameOfReceiver = body.getUserSharedWith().getUsername();
		String fileName = body.getName();
		String filePath = body.getFolderPath();
		String fileExtension = body.getFileExtension();
		String permissionLevel = body.getPermissionLevel();
		
		checkInvalidUsername(usernameOfSender);
		checkInvalidUsername(usernameOfReceiver);
		checkInvalidStorageItemName(fileName);
		checkInvalidPath(filePath);
		checkInvalidExtension(fileExtension);
		checkInvalidFilePemission(permissionLevel);
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
		
	public static void checkInvalidUser(User user) {
		if (Validator.isEmpty(user)) {
			throw new InvalidRequestBodyException("Invalid user.");
		}
	}
	
	public static void checkInvalidUsername(String username) {
		if (Validator.isUsernameInvalid(username)) {
			throw new InvalidRequestBodyException("Invalid username: " + username + ".");
		}
	}
	
	public static void checkInvalidStorageItemName(String name) {
		if (Validator.isStorageItemNameInvalid(name)) {
			throw new InvalidRequestBodyException("Invalid name for storage item: " + name + ".");
		}
	}
	
	public static void checkInvalidPath(String path) {
		if (Validator.isEmpty(path)) {
			throw new InvalidRequestBodyException("Invalid path for item: " + path + ".");
		}
	}
	
	public static void checkInvalidExtension(String extension) {
		if (Validator.isExtensionInvalid(extension)) {
			throw new InvalidRequestBodyException("Invalid extension for item: " + extension + ".");
		}
	}
	
	public static void checkInvalidFilePemission(String permission) {
		if (Validator.isPermissionLevelInvalid(permission)) {
			throw new InvalidRequestBodyException("Invalid permission: " + permission + ".");
		}
	}
}
