package org.ufcg.si.beans.responses;

import org.ufcg.si.models.User;

/**
 * This class is a custom response.<br> 
 * It encapsulates a user and a token, which needs to be returned
 * when a user successfully login into the system.
 */
public class AuthenticationResponse {
	private String token;
	private User user;
	
	/**
	 * Create a new LoginResponse.
	 * 
	 * @param token A token representing the session of the user.
	 * @param user The user that made login.
	 */
	public AuthenticationResponse(String token, User user) {
		this.token = token;
		this.user = user;
	}
	
	/**
	 *  
	 * @return The token binded to a login request.
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * 
	 * @return The user binded to a login request.
	 */
	public User getUser() {
		return user;
	}
}
