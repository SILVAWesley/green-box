package org.ufcg.si.facade;

import javax.servlet.ServletException;

import org.jboss.jandex.Main;
import org.springframework.http.ResponseEntity;
import org.ufcg.si.beans.requests.AddFolderBean;
import org.ufcg.si.beans.requests.AddNDeleteFileBean;
import org.ufcg.si.beans.requests.EditFileBean;
import org.ufcg.si.beans.requests.RegistrationBean;
import org.ufcg.si.beans.requests.RenameFileBean;
import org.ufcg.si.controllers.AuthenticationController;
import org.ufcg.si.controllers.UsersActionsController;
import org.ufcg.si.controllers.UsersController;
import org.ufcg.si.models.User;
import org.ufcg.si.models.storage.FileGB;
import org.ufcg.si.repositories.UserServiceImpl;

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
		RegistrationBean requestBody = this.createRegistrationBody(username, email, password);
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
		AddNDeleteFileBean requestBody = this.createAddNDeleteFileRequestBody(user, fileName, fileExtension, filePath,
				fileContent);
		this.userAction.addFile(requestBody);
	}

	public void editFile(User user, String fileName, String fileExtension, String fileContent, String filePath,
			FileGB clickedFile) {
		EditFileBean requestBody = this.createEditFileRequestBody(user, fileName, fileExtension, fileContent,
				filePath, clickedFile);
		try {
			this.userAction.editFile(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void renameFile(User user, String newName, String oldName, String folderPath, String fileExtension){
		RenameFileBean requestBody = this.createRenameFileRequestBody(user, newName, oldName, folderPath, fileExtension);
		try {
			this.userAction.renameFile(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addFolder(User user, String folderName, String folderPath){
		AddFolderBean requestBody = this.createAddFolderRequestBody(user, folderName, folderPath);
		try {
			this.userAction.addFolder(requestBody);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	public void renameFolder(User user, String newName, String oldName, String folderPath){
		RenameFolderBean requestBody = this.createRenameFolderRequestBody(user, newName, oldName, folderPath);
		try {
			this.userAction.renameFolder(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private RenameFolderBean createRenameFolderRequestBody(User user, String newName, String oldName, String folderPath){
		RenameFolderBean requestBody = new RenameFolderBean(user, newName, oldName, folderPath);
		return requestBody;
		
	}
	
	private AddFolderBean createAddFolderRequestBody(User user, String folderName, String folderPath){
		AddFolderBean requestBody = new AddFolderBean(user, folderName, folderPath);
		return requestBody;
	}
	
	private RenameFileBean createRenameFileRequestBody(User user, String newName, String oldName, String folderPath, String fileExtension){
		RenameFileBean requestBody = new RenameFileBean(user, newName, oldName, folderPath, fileExtension);
		return requestBody;
	}

	private EditFileBean createEditFileRequestBody(User user, String fileName, String fileExtension,
			String fileContent, String filePath, FileGB clickedFile) {
		EditFileBean requestBody = new EditFileBean(user, fileName, fileExtension, fileContent, filePath,
				clickedFile);
		return requestBody;
	}

	private AddFileBean createAddFileRequestBody(User user, String fileName, String fileExtension,
			String filePath, String fileContent) {
		AddFileBean requestBody = new AddFileBean(user, fileName, fileExtension, filePath, fileContent);
		return requestBody;
	}

	private RegistrationBean createRegistrationBody(String username, String email, String password) {
		RegistrationBean requestBody = new RegistrationBean();
		requestBody.setUsername(username);
		requestBody.setEmail(email);
		requestBody.setPassword(password);
		return requestBody;
	}

}
