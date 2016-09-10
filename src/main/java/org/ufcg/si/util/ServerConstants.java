package org.ufcg.si.util;

/**
 * This class put together constants used all along the server. 
 */
public class ServerConstants {
	/* Constants related to io */
	public static final String LINE_BREAK = System.lineSeparator();
	public static final String PATH_SEPARATOR = "/";
	public static final String FILES_PATH = "files/";
	
	/* Constants related to request mapping */
	
	/**
	 * Represents the piece of URL that should be inserted
	 * in a request to access any operation allowed by 
	 * the server.
	 */
	public static final String SERVER_REQUEST_URL = "/server";
	
	/**
	 * Represents the piece of URL that should be inserted
	 * in a request to access any operation allowed by 
	 * the UsersController.
	 */
	public static final String USERS_REQUEST_URL = "/users";
	
	/**
	 * Represents the piece of URL that should be inserted
	 * in a request to access any operation allowed by 
	 * the AuthenticationController.
	 */
	public static final String AUTHENTICATION_REQUEST_URL = "/authentication";
	
	/**
	 * Represents the piece of URL that should be inserted
	 * in a request to access any operation allowed by 
	 * the UsersDirectoryController.
	 */
	public static final String USERS_ACTIONS_REQUEST_URL = "/userdirectory";
	
	/* Constants related to permissions and security */
	
	/**
	 * Pattern used to designate requests that should require a valid token
	 * to be sucessful. <br>
	 * In this case, a request that begins with /server/userdirectory/
	 * will be checked to send a valid token inside the http request
	 * header.
	 */
	public static final String USERSDIRECTORY_PATTERN = SERVER_REQUEST_URL + 
													   USERS_ACTIONS_REQUEST_URL + "/*";
	
	/**
	 * This is the key used to encrypt and decrypt a token.  
	 */
	public static final String TOKEN_KEY = "lordoftherings";
	
}
