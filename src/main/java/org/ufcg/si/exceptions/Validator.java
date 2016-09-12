package org.ufcg.si.exceptions;

import java.util.regex.Pattern;

import org.ufcg.si.util.FileExtensions;
import org.ufcg.si.util.permissions.file.FilePermissions;

public class Validator {
	public static final String USERNAME_REGEXP = "^[a-zA-Z0-9_-]{4,15}$";
	public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
	public static final String STORAGE_ITEM_NAME_REGEXP = "^([a-zA-Z0-9_\\s]+)";
	public static final String EMAIL_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
											 + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean isEmailInvalid(String email) {
		return isEmpty(email) || !Pattern.matches(EMAIL_REGEXP, email);
	}
	
	public static boolean isUsernameInvalid(String username) {
		return isEmpty(username) || !Pattern.matches(USERNAME_REGEXP, username);
	}
	
	public static boolean isPasswordInvalid(String password) {
		return isEmpty(password) || !Pattern.matches(PASSWORD_REGEXP, password);
	}
	
	public static boolean isStorageItemNameInvalid(String name) {
		return isEmpty(name) || !Pattern.matches(STORAGE_ITEM_NAME_REGEXP, name);
	}
	
	public static boolean isExtensionInvalid(String extension) {
		return FileExtensions.valueOfIgnoreCase(extension) == null;
	}
	
	public static boolean isPermissionLevelInvalid(String permission) {
		return FilePermissions.valueOfIgnoreCase(permission) == null;
	}
	
	public static boolean isEmptyUsername(String username) {
		if (username == null || username.isEmpty() || username.equals("dir")) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isEmpty(String target) {
		if (target == null || target.isEmpty()) {
			return true;
		}
	
		return false;
	}
	
	public static boolean isEmpty(Object target) {
		if (target == null) {
			return true;
		}
		
		return false;
	}
}
