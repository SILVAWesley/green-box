package org.ufcg.si.controllers.rest;

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
import org.ufcg.si.util.tokens.HS512_2Minutes_Token;
import org.ufcg.si.util.tokens.TokenBuilder;

@RestController
@RequestMapping(ServerConstants.ACCESS_PATH + ServerConstants.LOGIN_PATH)
public class LoginController {
	private TokenBuilder tokenBuilder;
	private UserService userService;
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	
	@Autowired
	public void setTokenBuilder(HS512_2Minutes_Token tokenBuilder) {
		this.tokenBuilder = tokenBuilder;
	}
	
	@RequestMapping(value = "/authenticate",
					method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public LoginResponse login(@RequestBody User user) throws ServletException {
		System.out.println("Password of user = " + user.getPassword());
		ExceptionHandler.checkLoginFields(user);
		System.out.println("Password of user = " + user.getPassword());
		User foundUser = null;
		
		if (user.getEmail() != null) {
			foundUser = userService.findByEmail(user.getEmail());
		} else {
			foundUser = userService.findByUsername(user.getUsername());
		}
		System.out.println(foundUser);
		ExceptionHandler.checkUserInDatabase(foundUser);
		ExceptionHandler.checkMatchingPassword(user, foundUser);
		
		String token = tokenBuilder.build(foundUser);		
		return new LoginResponse(token, foundUser);
	}
}
