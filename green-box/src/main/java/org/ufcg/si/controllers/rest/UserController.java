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
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user/deleteid={id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable Long id){
		User deletedUser = userService.delete(id);
		if (deletedUser != null){
			return new ResponseEntity<> (deletedUser, HttpStatus.OK);
		}else{
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/user/all", method = RequestMethod.GET)
	public ResponseEntity<Iterable<User>> getAllUsers(){
		Iterable<User> allUsers = userService.findAll();
		return new ResponseEntity<> (allUsers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/put", method = RequestMethod.PUT)
	public ResponseEntity<User> modifyUser(@RequestBody User newUser){
		User modifiedUser = userService.modify(newUser);
		if(modifiedUser != null){
			return new ResponseEntity<> (modifiedUser, HttpStatus.OK);
		}else{
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
		
	}	
}
