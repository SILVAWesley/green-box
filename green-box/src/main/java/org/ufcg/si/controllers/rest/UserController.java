package org.ufcg.si.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;

@RestController
public class UserController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User matchingUser = userService.findById(id);
		
		if (matchingUser != null) {
			return new ResponseEntity<>(matchingUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(matchingUser, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/user/new", method = RequestMethod.POST)
	public ResponseEntity<User> newUser(@RequestBody User newUser) {
		User savedUser = userService.save(newUser);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}
	
}
