package org.ufcg.si.models;

import java.io.IOException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.ufcg.si.models.storage.FolderManager;
import org.ufcg.si.models.storage.StorageFactory;
import org.ufcg.si.util.permissions.FilePermission;

/**
 * This class represents a Green-Box user
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

	@OneToOne(cascade = CascadeType.ALL)
	private FolderManager directory;

	public User() throws Exception {
		this("email", "dir", "password");
		
	}

	public User(String email, String username, String password) {
		this.directory = StorageFactory.createDirectory(username);
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public void addFile(String name, String extension, String content, String path) throws IOException {
		directory.addFile(name, extension, content, path);
	}
	
	public void addFolder(String name, String path) {
		directory.addFolder(name, path);
	}
	
	public void editFileName(String newName, String oldName, String path) {
		directory.editFileName(newName, oldName, path);
	}
	
	public void editFileContent(String name, String newContent, String path) throws IOException {
		directory.editFileContent(name, newContent, path);
	}
	
	public void editFileExtension(String newExtension, String name, String path) {
		directory.editFileExtension(newExtension, name, path);
	}
	
	public void editFolderName(String newName, String oldName, String path) {
		directory.editFolderName(newName, oldName, path);
	}
	
	public void shareFile(User user, String name, String path, FilePermission filePermission) throws IOException {
		directory.shareFile(user.directory, name, path, filePermission);
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
	public FolderManager getDirectory() {
		return directory;
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
}
