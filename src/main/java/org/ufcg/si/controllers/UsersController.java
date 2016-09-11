package org.ufcg.si.controllers;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.beans.requests.RegistrationBean;
import org.ufcg.si.exceptions.ExceptionHandler;
import org.ufcg.si.exceptions.GreenboxException;
import org.ufcg.si.exceptions.Validator;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.ServerConstants;

/**
 * This controller class uses JSON data format to be the 
 * endpoint of requests related to users on the server-side.
 */
@RestController
@RequestMapping(ServerConstants.SERVER_REQUEST_URL + ServerConstants.USERS_REQUEST_URL)
public class UsersController {
	private UserService userService;
	
	@RequestMapping(value = "/get/{username}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable String username) {
		User dbUser = userService.findByUsername(username);
	
		if (Validator.isEmpty(dbUser)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<>(dbUser, HttpStatus.OK);
	}
	
	/**
	 * Create a new User
	 * @param requestBody The new User
	 * @return The newly created user
	 * @throws ServletException
	 */
	@RequestMapping(value = "/new", 
					method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody RegistrationBean requestBody) throws ServletException {
		try {
			ExceptionHandler.checkRegistrationBody(requestBody);
			
			User newUser = new User(requestBody.getEmail(), requestBody.getUsername(), requestBody.getPassword());
			userService.save(newUser);
			
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to register user... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to register user... " + dae.getMessage());
		}
	}
	
	/**
	 * Delete a User
	 * @param id The identification of the user
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", 
					method = RequestMethod.DELETE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		User deletedUser = userService.delete(id);

		if (Validator.isEmpty(deletedUser)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(deletedUser, HttpStatus.OK);
	}
	
	/**
	 * This method return a list with all user created 
	 * @returnAll A list of Users 
	 */
	@RequestMapping(value = "/all", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<User>> getAllUsers() {
		Iterable<User> allUsers = userService.findAll();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
	
	/**
	 * This method update a user
	 * @param reqBody The user who will be update 
	 * @return
	 */
	@RequestMapping(value = "/update", 
					method = RequestMethod.PUT, 
					consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody User reqBody) {
		User updatedUser = userService.update(reqBody);

		if (updatedUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
}
