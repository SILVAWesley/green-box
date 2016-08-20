package org.ufcg.si.controllers;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.ExceptionHandler;
import org.ufcg.si.util.ServerConstants;
import org.ufcg.si.util.responses.AuthenticationResponse;
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
	private UserService userService;
	private TokenBuilder tokenBuilder;
	
	/**
	 * this method authenticates a User so you can have access to your files 
	 * @param requestBody The User that will be authenticated
	 * @return A LoginResponse
	 * @throws ServletException
	 */
	@RequestMapping(value = "/login",
					method = RequestMethod.POST, 
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationResponse> login(@RequestBody User requestBody) throws ServletException {
		/* FUTURE WORKS ON THIS CLASS SHOULD ENABLE: 
		 * Exception handling (REFACTORING) */
		ExceptionHandler.checkLoginFields(requestBody);
		
		User dbUser = null;
		
		if (requestBody.getEmail() != null) {
			dbUser = userService.findByEmail(requestBody.getEmail());
		} else {
			dbUser = userService.findByUsername(requestBody.getUsername());
		}
		
		ExceptionHandler.checkUserInDatabase(dbUser);
		ExceptionHandler.checkMatchingPassword(requestBody, dbUser);
		
		String token = tokenBuilder.build(dbUser);
		AuthenticationResponse response = new AuthenticationResponse(token, dbUser);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	
	@Autowired
	public void setTokenBuilder(HS512_24Hours_Token tokenBuilder) {
		this.tokenBuilder = tokenBuilder;
	}
}
