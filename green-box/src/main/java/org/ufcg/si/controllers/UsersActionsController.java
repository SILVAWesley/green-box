package org.ufcg.si.controllers;

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
import org.ufcg.si.util.ServerConstants;
import org.ufcg.si.util.requests.FileRequestBody;
import org.ufcg.si.util.requests.FolderRequestBody;

/**
 * This controller class uses JSON data format to be the 
 * endpoint of requests related to directories of users
 * on the server-side.
 * 
 * <strong>This controller needs to a valid token to be accessed.</strong>
 */
@RestController
@RequestMapping(ServerConstants.SERVER_REQUEST_URL + ServerConstants.USERS_DIRECTORY_REQUEST_URL)
public class UsersActionsController {
	private UserService userService;
	
	@RequestMapping(value = "/newfolder", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFolder(@RequestBody FolderRequestBody requestBody) throws Exception {
		User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
		dbUser.getUserDirectory().addFolder(requestBody.getFolderName(), requestBody.getFolderPath());
		User updatedUser = userService.update(dbUser);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/newfile", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFile(@RequestBody FileRequestBody requestBody) throws Exception {
		User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
		System.out.println("TESTE: " + requestBody.getFilePath());
		dbUser.getUserDirectory().addFile(requestBody.getFileName(), requestBody.getFileExtension(), requestBody.getFileContent(), requestBody.getFilePath());
		User updatedUser = userService.update(dbUser);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/editfile",
					method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> editFile(@RequestBody FileRequestBody requestBody) throws Exception{
		User userFromRequest = requestBody.getUser();
		User dbUser = userService.findByUsername(userFromRequest.getUsername());
		dbUser.getUserDirectory().editFile(requestBody.getFileName(), requestBody.getFileContent(), requestBody.getFilePath());
		User updateUser = userService.update(dbUser);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
}
