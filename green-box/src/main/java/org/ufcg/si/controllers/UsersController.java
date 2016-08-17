package org.ufcg.si.controllers;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.ServerConstants;

/**
 * This controller class uses JSON data format to be the 
 * endpoint of requests related to users on the server-side.d
 */
@RestController
@RequestMapping(ServerConstants.SERVER_REQUEST_URL + ServerConstants.USERS_REQUEST_URL)
public class UsersController {
	private UserService userService;

	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	/**
	 * This method return a User according to their ID
	 * @param id The identification of a user
	 * @return A user
	 */
	@RequestMapping(value = "/get/{id}", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User matchingUser = userService.findById(id);

		if (matchingUser != null) {
			return new ResponseEntity<>(matchingUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Create a new User
	 * @param newUser The new User
	 * @return The newly created user
	 * @throws ServletException
	 */
	@RequestMapping(value = "/new", 
					method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody User newUser) throws ServletException {
		try {
			String username = newUser.getUsername();
			newUser.getUserDirectory().setName(username);
			User savedUser = userService.save(newUser);
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("User already inside the database. " + e.getMessage());
		}
	}
	
	/**
	 * Delete a User
	 * @param id The identification of the user
	 * @return
	 */
	@RequestMapping(value = "/deleteid={id}", 
					method = RequestMethod.DELETE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		User deletedUser = userService.delete(id);

		if (deletedUser != null) {
			return new ResponseEntity<>(deletedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * This method return a list with all user createds 
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
	 * @param user The user who will be update 
	 * @return
	 */
	
	@RequestMapping(value = "/update", 
					method = RequestMethod.PUT, 
					consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User modifiedUser = userService.update(user);

		if (modifiedUser != null) {
			return new ResponseEntity<>(modifiedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
