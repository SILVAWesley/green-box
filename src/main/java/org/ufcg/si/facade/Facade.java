package org.ufcg.si.facade;

import javax.servlet.ServletException;

import org.jboss.jandex.Main;
import org.springframework.http.ResponseEntity;
import org.ufcg.si.beans.requests.AddFileRequestBody;
import org.ufcg.si.beans.requests.AddFolderRequestBody;
import org.ufcg.si.beans.requests.EditFileRequestBody;
import org.ufcg.si.beans.requests.RegistrationBody;
import org.ufcg.si.controllers.AuthenticationController;
import org.ufcg.si.controllers.UsersActionsController;
import org.ufcg.si.controllers.UsersController;
import org.ufcg.si.models.User;
import org.ufcg.si.models.storage.FileGB;
import org.ufcg.si.repositories.UserServiceImpl;
import org.ufcg.si.util.requests.RenameFileRequestBody;
import org.ufcg.si.util.requests.RenameFolderRequestBody;

public class Facade {
	private UsersController userControl;
	private UsersActionsController userAction;
	private AuthenticationController authenticControl;

	public Facade() {
		this.userControl = new UsersController();
		this.userAction = new UsersActionsController();
		this.authenticControl = new AuthenticationController();
	}

	public void createUser(String username, String email, String password) {
		RegistrationBody requestBody = this.createRegistrationBody(username, email, password);
		try {
			this.userControl.createUser(requestBody);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public ResponseEntity<User> getUser(String username) {
		return userControl.getUser(username);
	}

	public void deleteUser(Long id) {
		this.userControl.deleteUser(id);
	}

	public ResponseEntity<Iterable<User>> getAllUsers() {
		return this.userControl.getAllUsers();
	}

	public void updateUser(User reqBody) {
		this.userControl.updateUser(reqBody);
	}

	public void setUserService(UserServiceImpl userServiceImpl) {
		this.userControl.setUserService(userServiceImpl);
	}
	
	public void login(User requestBody){
		try {
			this.authenticControl.login(requestBody);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public void addFile(User user, String fileName, String fileExtension, String filePath, String fileContent)
			throws ServletException {
		AddFileRequestBody requestBody = this.createAddFileRequestBody(user, fileName, fileExtension, filePath,
				fileContent);
		this.userAction.addFile(requestBody);
	}

	public void editFile(User user, String fileName, String fileExtension, String fileContent, String filePath,
			FileGB clickedFile) {
		EditFileRequestBody requestBody = this.createEditFileRequestBody(user, fileName, fileExtension, fileContent,
				filePath, clickedFile);
		try {
			this.userAction.editFile(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void renameFile(User user, String newName, String oldName, String folderPath){
		RenameFileRequestBody requestBody = this.createRenameFileRequestBody(user, newName, oldName, folderPath);
		try {
			this.userAction.renameFile(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addFolder(User user, String folderName, String folderPath){
		AddFolderRequestBody requestBody = this.createAddFolderRequestBody(user, folderName, folderPath);
		try {
			this.userAction.addFolder(requestBody);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	public void renameFolder(User user, String newName, String oldName, String folderPath){
		RenameFolderRequestBody requestBody = this.createRenameFolderRequestBody(user, newName, oldName, folderPath);
		try {
			this.userAction.renameFolder(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private RenameFolderRequestBody createRenameFolderRequestBody(User user, String newName, String oldName, String folderPath){
		RenameFolderRequestBody requestBody = new RenameFolderRequestBody(user, newName, oldName, folderPath);
		return requestBody;
		
	}
	
	private AddFolderRequestBody createAddFolderRequestBody(User user, String folderName, String folderPath){
		AddFolderRequestBody requestBody = new AddFolderRequestBody(user, folderName, folderPath);
		return requestBody;
	}
	
	private RenameFileRequestBody createRenameFileRequestBody(User user, String newName, String oldName, String folderPath){
		RenameFileRequestBody requestBody = new RenameFileRequestBody(user, newName, oldName, folderPath);
		return requestBody;
	}

	private EditFileRequestBody createEditFileRequestBody(User user, String fileName, String fileExtension,
			String fileContent, String filePath, FileGB clickedFile) {
		EditFileRequestBody requestBody = new EditFileRequestBody(user, fileName, fileExtension, fileContent, filePath,
				clickedFile);
		return requestBody;
	}

	private AddFileRequestBody createAddFileRequestBody(User user, String fileName, String fileExtension,
			String filePath, String fileContent) {
		AddFileRequestBody requestBody = new AddFileRequestBody(user, fileName, fileExtension, filePath, fileContent);
		return requestBody;
	}

	private RegistrationBody createRegistrationBody(String username, String email, String password) {
		RegistrationBody requestBody = new RegistrationBody();
		requestBody.setUsername(username);
		requestBody.setEmail(email);
		requestBody.setPassword(password);
		return requestBody;
	}

}
