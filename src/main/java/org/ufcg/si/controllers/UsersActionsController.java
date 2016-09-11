package org.ufcg.si.controllers;

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
import org.ufcg.si.beans.requests.AddFileBean;
import org.ufcg.si.beans.requests.AddFolderBean;
import org.ufcg.si.beans.requests.EditFileBean;
import org.ufcg.si.beans.requests.RenameFileBean;
import org.ufcg.si.beans.requests.ShareFileBean;
import org.ufcg.si.exceptions.ExceptionHandler;
import org.ufcg.si.exceptions.GreenboxException;
import org.ufcg.si.models.Notification;
import org.ufcg.si.models.User;
import org.ufcg.si.repositories.UserService;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.ServerConstants;
import org.ufcg.si.util.permissions.file.FilePermissions;
import org.ufcg.si.util.requests.RenameFolderRequestBody;

/**
 * This controller class uses JSON data format to be the 
 * endpoint of requests related to directories of users
 * on the server-side.
 * 
 * <strong>This controller needs to a valid token to be accessed.</strong>
 */
@RestController
@RequestMapping(ServerConstants.SERVER_REQUEST_URL + ServerConstants.USERS_ACTIONS_REQUEST_URL)
public class UsersActionsController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}
	
	@RequestMapping(value = "/newfolder", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFolder(@RequestBody AddFolderBean body) throws ServletException {
		try {
			ExceptionHandler.checkAddFolderBody(body);
		
			User dbUser = userService.findByUsername(body.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);

			dbUser.addFolder(body.getFolderName(), body.getFolderPath());
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
	public ResponseEntity<User> addFile(@RequestBody AddFileBean body) throws ServletException {
		try {
			ExceptionHandler.checkAddFileBody(body);
			
			User dbUser = userService.findByUsername(body.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			dbUser.addFile(body.getFileName(), body.getFileExtension(), body.getFileContent(), body.getFilePath());
			User updatedUser = userService.update(dbUser);
			
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to create new file... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to create new file... " + dae.getMessage());
		}
	}
	
	@RequestMapping(value = "/editfile",
					method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> editFile(@RequestBody EditFileBean requestBody) throws Exception{
		try {
			ExceptionHandler.checkEditFileBody(requestBody);
			
			User dbUser = userService.findByUsername(requestBody.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			if (!requestBody.getFileName().equals(requestBody.getClickedFile().getName())) {
				dbUser.editFileName(requestBody.getFileName(), requestBody.getClickedFile().getName(), requestBody.getClickedFile().getExtension(), requestBody.getFilePath());
			}
			
			if (!requestBody.getFileExtension().equals(requestBody.getClickedFile().getExtension())) {
				dbUser.editFileExtension(requestBody.getFileExtension(), requestBody.getFileName(), requestBody.getClickedFile().getExtension(), requestBody.getFilePath());
			}
			
			if (!requestBody.getFileContent().equals(requestBody.getClickedFile().getContent())) {
				dbUser.editFileContent(requestBody.getFileName(), requestBody.getFileContent(), requestBody.getFileExtension(), requestBody.getFilePath());
			}
			
			User updateUser = userService.update(dbUser);
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to edit file... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to edit file... " + dae.getMessage());
		}
	}
	
	@RequestMapping(value = "/renamefolder", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> renameFolder(@RequestBody RenameFolderRequestBody body)throws Exception{
		try {
			ExceptionHandler.checkRenameFolderBody(body);
			User dbUser = userService.findByUsername(body.getUser().getUsername());
		
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			dbUser.editFolderName(body.getNewName(), body.getOldName(), body.getFolderPath());
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
	
	@RequestMapping(value = "/renamefile", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> renameFile(@RequestBody RenameFileBean body) throws ServletException {
		try {
			ExceptionHandler.checkRenameFileBody(body);
			
			User dbUser = userService.findByUsername(body.getUser().getUsername());
			ExceptionHandler.checkUserInDatabase(dbUser);
			
			dbUser.editFileName(body.getNewName(), body.getOldName(), body.getFileExtension(), body.getFolderPath());
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
	
	@RequestMapping(value = "/sharefile", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> shareFile(@RequestBody ShareFileBean body) throws Exception {
		try {
			ExceptionHandler.checkShareFileBody(body);
			
			User sendingUser = userService.findByUsername(body.getUser().getUsername());
			User receivingUser = userService.findByUsername(body.getUserSharedWith().getUsername());
		
			ExceptionHandler.checkUserInDatabase(sendingUser);
			ExceptionHandler.checkUserInDatabase(receivingUser);
			
			sendingUser.shareFile(receivingUser, body.getName(), body.getFolderPath(), body.getFileExtension(), FilePermissions.valueOfIgnoreCase(body.getPermissionLevel()));
			User updateUser = userService.update(sendingUser);
			userService.update(receivingUser);
		
			return new ResponseEntity<>(updateUser, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to share file... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to share file... " + dae.getMessage());
		} 
	}
	
	@RequestMapping(value = "/notifications", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Notification>> listNotifications(@RequestBody User requestBody) throws Exception {
		try {
			User dbUser = userService.findByUsername(requestBody.getUsername());
			
			ExceptionHandler.checkUserInDatabase(dbUser);
			Iterable<Notification> notifications = dbUser.listNotifications();
			
			return new ResponseEntity<>(notifications, HttpStatus.OK);
		} catch(GreenboxException gbe) {
			gbe.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + gbe.getMessage());
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServletException("Request error while trying to rename folder... " + dae.getMessage());
		} 
	}
	
	@RequestMapping(value = "/visit_notifications", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> visitNotifications(@RequestBody User requestBody) throws Exception {
		try {
			User dbUser = userService.findByUsername(requestBody.getUsername());
			
			ExceptionHandler.checkUserInDatabase(dbUser);
			Iterable<Notification> notifications = dbUser.listNotifications();
			
			for (Notification notification : notifications) {
				notification.setIsVisited(true);
			}
			
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
}
