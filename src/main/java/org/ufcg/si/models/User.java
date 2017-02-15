package org.ufcg.si.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.ufcg.si.models.storage.StorageFactory;
import org.ufcg.si.models.storage.UserActionsManager;
import org.ufcg.si.util.permissions.file.FilePermissions;

import easyaccept.EasyAccept;

/**
 * This class represents a Green-Box user. Work as a storage for user informations and a facade for
 * user actions.
 */
@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String username;
	@Column(unique = true)
	private String email;
	private String password;
	
	private long memoryUsage;
	private long timeUsage;

	@OneToOne(cascade = CascadeType.ALL)
	private UserActionsManager userActionsManager;
	
	/**
	 * User's default constructor, with default attributes
	 */
	public User() {
		this("email", "dir", "password");
	}
	
	/**
	 * User's constructor that creates the user with a certain email, username and password.
	 * This constructor also creates a userActionsManager.
	 * @param email
	 * 		The user's email
	 * @param username
	 * 		The user's username
	 * @param password
	 * 		The user's password
	 */
	public User(String email, String username, String password) {
		this.userActionsManager = StorageFactory.createFolderManager(username);
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Find a folder through a path and creates a new file in this folder, if it exists.
	 * An exception is thrown if there is no folder for the path or if the folder doesn't
	 * have permission to create a file.
	 * @param name
	 * 		The file name
	 * @param extension
	 * 		The file extension (md or txt)
	 * @param content
	 * 		The file initial content
	 * @param path
	 * 		The path to the folder in which the file will be created
	 */
	public void addFile(String name, String extension, String content, String path) {
		userActionsManager.addFile(name, extension, content, path);
	}
	
	/**
	 * Find a folder through a path and creates a new folder in this folder, if it exists.
	 * An exception is thrown if there is no folder for the path or if the folder doesn't
	 * have permission to create a folder.
	 * @param name
	 * 		The folder name
	 * @param path
	 * 		The path to the folder in which the new folder will be created
	 */
	public void addFolder(String name, String path) {
		userActionsManager.addFolder(name, path);
	}
	
	/**
	 * Edit a file's name. An exception is thrown if you do not have permission to
	 * change its name.
	 * @param newName
	 * 		The file new name
	 * @param oldName
	 * 		The file old name
	 * @param extension
	 * 		The file extension
	 * @param path
	 * 		The file path
	 */
	public void editFileName(String newName, String oldName, String extension, String path) {
		userActionsManager.editFileName(newName, oldName, extension, path);
	}
	
	/**
	 * Edit a file's content. An exception is thrown if you do not have permission to edit
	 * the file's content
	 * @param name
	 * 		The file's name
	 * @param newContent
	 * 		The file's new content
	 * @param extension
	 * 		The file's extension
	 * @param path
	 * 		The file's path
	 */
	public void editFileContent(String name, String newContent, String extension, String path) {
		userActionsManager.editFileContent(name, newContent, extension, path);
	}
	
	/**
	 * Edit a file's extension. An exception is thrown if you do not have permission to
	 * change its extension.
	 * @param newExtension
	 * 		The file new extension
	 * @param name
	 * 		The file name
	 * @param oldExtension
	 * 		The file old extension
	 * @param path
	 * 		The file path
	 */
	public void editFileExtension(String newExtension, String name, String oldExtension, String path) {
		userActionsManager.editFileExtension(newExtension, name, oldExtension, path);
	}
	
	/**
	 * Edit a folder's name. An exception is thrown if you do not have permission to
	 * change its name.
	 * @param newName
	 * 		The new folder name
	 * @param oldName
	 * 		The old folder name
	 * @param path
	 * 		The folder path
	 */
	public void editFolderName(String newName, String oldName, String path) {
		userActionsManager.editFolderName(newName, oldName, path);
	}
	
	/**
	 * Shares a file with an user. There are two permission levels for a shared file: Read Only (R) and Read Write (RW).
	 * An exception is thrown if the user that will receive the file doesn't exist or if you can't share the desired
	 * file.
	 * @param managerToShareWith
	 * 		The UserActionsMenager of the User that will receive the file.
	 * @param name
	 * 		The name of the file to be shared
	 * @param path
	 * 		The path of the file to be shared
	 * @param extension
	 * 		The extension of the file to be shared
	 * @param permission
	 * 		The sharing permission (R or RW)
	 */
	public void shareFile(User user, String name, String path, String extension, FilePermissions filePermission) {
		userActionsManager.shareFile(user.userActionsManager, name, path, extension, filePermission);
	}
	
	/**
	 * 
	 */
	
	public void deleteFile(String name, String extension, String path) {
		userActionsManager.deleteFile(name, extension, path);
	}
	
	/**
	 * 
	 */
	
	public void deleteFolder(String path, String name) {
		userActionsManager.deleteFolder(path, name);
	}
	
	/**
	 * Return the iterable of the notification's list
	 * @return
	 * 		The iterable of the notification's list
	 */
	public Iterable<Notification> listNotifications() {
		return userActionsManager.listNotifications();
	}
	
	/**
	 * The username getter. The username is used for authentication in the Green-Box application
	 * @return the User's username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Updates the User's username
	 * @param newUsername
	 * 		New username
	 */
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	/**
	 * The email getter. The email is used for authentication in the Green-Box application
	 * @return the User's email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * The password getter. The password is used for authentication on the Green-Box application
	 * @return the User's password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * The userDirectory getter. The userDirectory is the user's most external directory.
	 * @return the User's most external UserDirectory
	 */
	public UserActionsManager getDirectory() {
		return userActionsManager;
	}
	
	/**
	 * The ID getter. The ID is used when saving a User in the database and for unique identification
	 * @return the User's ID
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Receives an object and checks if it's equal to the User.
	 * Users are considered equals if they have the same username, email and password.
	 * @param obj
	 * 		The object to be compared with the User.
	 * @return if the obj is equals to this object
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User temp = (User) obj;

			return this.getUsername().equals(temp.getUsername()) && this.getEmail().equals(temp.getEmail())
					&& this.getPassword().equals(temp.getPassword());
		} else {
			return false;
		}

	}
	
	@Override
	public String toString(){
		String result = "Username: " + this.username + " Email: " + this.email + " PW: " + this.password + "\n" + this.userActionsManager.toString();
		return result;
	}

	public long getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(long memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public long getTimeUsage() {
		return timeUsage;
	}

	public void setTimeUsage(long timeUsage) {
		this.timeUsage = timeUsage;
	}
	
	/*public static void main(String[] args) {
		args = new String[]{"org.ufcg.si.models.User", "src/test/java/org/ufcg/si/models/UserEasyAcceptTest.txt"};
		EasyAccept.main(args);
	}*/
	
	
}
