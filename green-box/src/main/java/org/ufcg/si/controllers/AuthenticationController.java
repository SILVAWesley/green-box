package org.ufcg.si.controllers;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.ExceptionHandler;
import org.ufcg.si.util.ServerConstants;
import org.ufcg.si.util.responses.LoginResponse;
import org.ufcg.si.util.tokens.HS512_24Hours_Token;
import org.ufcg.si.util.tokens.TokenBuilder;

/**
 * This controller class uses JSON data format to be the 
 * endpoint of requests related to authentication of users
 * on the server-side.<br>
 * An authenticated user has a session unique token, which
 * provides him the permission to successfully obtain responses
 * from requests sent to UserDirectoryController. 
 */
@RestController
@RequestMapping(ServerConstants.SERVER_REQUEST_URL + ServerConstants.AUTHENTICATION_REQUEST_URL)
public class AuthenticationController {
	private TokenBuilder tokenBuilder;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	
	@Autowired
	public void setTokenBuilder(HS512_24Hours_Token tokenBuilder) {
		this.tokenBuilder = tokenBuilder;
	}
	
	@RequestMapping(value = "/authenticate",
					method = RequestMethod.POST, 
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public LoginResponse login(@RequestBody User user) throws ServletException {
		ExceptionHandler.checkLoginFields(user);
		
		User foundUser = null;
		
		if (user.getEmail() != null && !user.getEmail().equals("")) {
			foundUser = userService.findByEmail(user.getEmail());
		} else {
			foundUser = userService.findByUsername(user.getUsername());
		}
		
		ExceptionHandler.checkUserInDatabase(foundUser);
		ExceptionHandler.checkMatchingPassword(user, foundUser);
		
		String token = tokenBuilder.build(foundUser);		
		return new LoginResponse(token, foundUser);
	}
}
