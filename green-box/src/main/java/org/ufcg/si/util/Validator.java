package org.ufcg.si.util;

import java.util.regex.Pattern;

public class Validator {
	public static final String USERNAME_REGEXP = "^[a-z0-9_-]{4,15}$";
	public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
	public static final String STORAGE_ITEM_NAME_REGEXP = "^([a-z0-9_\\s]+)";
	public static final String FILE_NAME_REGEXP = "[a-z0-9_\\s]+";
	public static final String EMAIL_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
											 + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean isEmailInvalid(String pEmail) {
		return isEmpty(pEmail) || !Pattern.matches(EMAIL_REGEXP, pEmail);
	}
	
	public static boolean isUsernameInvalid(String pUsername) {
		return isEmpty(pUsername) || !Pattern.matches(USERNAME_REGEXP, pUsername);
	}
	
	public static boolean isPasswordInvalid(String pPassword) {
		return isEmpty(pPassword) || !Pattern.matches(PASSWORD_REGEXP, pPassword);
	}
	
	public static boolean isStorageItemNameInvalid(String pName) {
		return isEmpty(pName) || !Pattern.matches(STORAGE_ITEM_NAME_REGEXP, pName);
	}
	
	public static boolean isExtensionInvalid(String pExtension) {
		return FileExtension.valueOfIgnoreCase(pExtension) == null;
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
