package org.ufcg.si.util;

public class ServerConstants { 
	public static final String ACCESS_KEY = "server";
	public static final String ACCESS_PATH = "/" + ACCESS_KEY;
	
	public static final String USERS_KEY = "users";
	public static final String USERS_PATH = "/" + USERS_KEY;
	
	public static final String LOGIN_KEY = "login";
	public static final String LOGIN_PATH = "/" + LOGIN_KEY;
	
	public static final String USERDIRECTORY_KEY = "userdirectory";
	public static final String USERDIRECTORY_PATH = "/" + USERDIRECTORY_KEY;	
	public static final String USERDIRECTORY_PATTERN = "/" + ACCESS_KEY + "/" + USERDIRECTORY_KEY + "/*";
	
	public static final String TOKEN_KEY = "lordoftherings";
	
}
