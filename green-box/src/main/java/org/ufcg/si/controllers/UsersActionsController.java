package org.ufcg.si.controllers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.exceptions.GreenboxException;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.ExceptionHandler;
import org.ufcg.si.util.ServerConstants;
import org.ufcg.si.util.requests.FileRequestBody;
import org.ufcg.si.util.requests.FolderRequestBody;
import org.ufcg.si.util.requests.NewFolderRequestBody;

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
	public ResponseEntity<User> addFolder(@RequestBody FolderRequestBody requestBody) throws ServletException {
		try {
			ExceptionHandler.checkNewFolderBody(requestBody);
		
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);

			dbUser.getUserDirectory().addFolder(requestBody.getFolderName(), requestBody.getFolderPath());
			User updatedUser = userService.update(dbUser);
		
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to create new folder... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to create new folder... " + dae.getMessage());
		}
	}
	
	@RequestMapping(value = "/newfile", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFile(@RequestBody FileRequestBody requestBody) throws ServletException {
		try {
			ExceptionHandler.checkNewFileBody(requestBody);
			
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			dbUser.getUserDirectory().addFile(requestBody.getFileName(), requestBody.getFileExtension(), requestBody.getFileContent(), requestBody.getFilePath());
			User updatedUser = userService.update(dbUser);
			
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to create new file... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to create new file... " + dae.getMessage());
		} catch(IOException ioe) {
			ioe.printStackTrace();
			throw new ServletException("Request error while trying to create new file... " + ioe.getMessage());
		}
	}
	
	@RequestMapping(value = "/editfile",
					method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> editFile(@RequestBody FileRequestBody requestBody) throws Exception{
		try {
			ExceptionHandler.checkEditFileBody(requestBody);
			
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			//Comprar o nome se existe e extenção
			//Verificar possibilidade de edição
			
			
			User updateUser = editFileContent(requestBody, dbUser);
			
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to edit file... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to edit file... " + dae.getMessage());
		} catch(IOException ioe) {
			ioe.printStackTrace();
			throw new ServletException("Request error while trying to edit file... " + ioe.getMessage());
		}
	}
	
	private User editFileContent(FileRequestBody requestBody, User dbUser) throws Exception{
		dbUser.getUserDirectory().editFile(requestBody.getFileName(), requestBody.getFileContent(), requestBody.getFilePath());
		User updateUser = userService.update(dbUser);
		
		return updateUser;
	}
	
	@RequestMapping(value = "/renamefolder", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> renameFolder(@RequestBody NewFolderRequestBody requestBody)throws Exception{
		try{
			
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
		
			ExceptionHandler.checkUserInDatabase(dbUser);
		
			dbUser.getUserDirectory().rename(requestBody.getNewName(), requestBody.getOldName(), requestBody.getNewFolderPath());
			User updateUser = userService.update(dbUser);
		
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + dae.getMessage());
		} 
	}
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
}
