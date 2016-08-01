package org.ufcg.si.controllers.rest;

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

@RestController
@RequestMapping(ServerConstants.ACCESS_PATH + ServerConstants.USERDIRECTORY_PATH)
public class UserDirectoryController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	
	@RequestMapping(value = "/newfolder/{dirname}", 
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addDirectory(@RequestBody User user, @PathVariable String dirname) throws Exception {
		user.getUserDirectory().createDirectory(dirname);
		User updatedUser = userService.update(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
}
