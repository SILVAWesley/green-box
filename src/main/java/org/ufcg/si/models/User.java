package org.ufcg.si.models;

import java.io.IOException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.ufcg.si.models.storage.GBFile;
import org.ufcg.si.models.storage.GBFolder;
import org.ufcg.si.models.storage.StorageFactory;

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
	private GBFolder rootFolder;

	public User() throws Exception {
		this("email", "dir", "password");
		
	}

	/**
	 * This constructor receives three strings: an email, an username and a
	 * password to create a new User. The most external UserDirectory is also
	 * created.
	 * 
	 * @param email
	 *            The user's email
	 * @param username
	 *            The user's name
	 * @param password
	 *            The user's password
	 */
	public User(String email, String username, String password) {
		this.rootFolder = StorageFactory.createRootFolder(username);
		this.username = username;
		this.email = email;
		this.password = password;
	}

	/**
	 * Creates a new UserFile in the current UserDirectory
	 * 
	 * @param filename
	 *            The file's name
	 * @param fileContent
	 *            The file's content
	 * @throws Exception
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 */
	public void createFile(String filename, String  content) throws Exception {
		// An enum should be created for file extension
		//userdirectory.createFile(filename, "txt", content); 
	}

	/**
	 * Creates a new UserDirectory in the current UserDirectory
	 * 
	 * @param directoryName
	 *            The new directory's name
	 * @throws Exception 
	 */
	public void createDirectory(String directoryName) throws Exception {
		//userdirectory.createDirectory(directoryName);
	}
	
	public void shareFile(User user, String name, String path) throws IOException {
		GBFile file = this.rootFolder.findFileByName(name, path);
		user.recieveFile(file);
	}
	
	public void recieveFile(GBFile file) throws IOException {
		this.rootFolder.findFolderByName("Shared with me").addFile(file);
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
	
	// GETTERS AND SETTERS
	
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
	public GBFolder getUserDirectory() {
		return rootFolder;
	}
	
	/**
	 * The ID getter. The ID is used when saving a User in the database and for unique identification
	 * @return the User's ID
	 */
	public Long getId() {
		return id;
	}

	public GBFolder getSharedWithMeFolder() {
		return this.rootFolder.findFolderByName("Shared with me");
	}

	public void setSharedWithMeFolder(GBFolder sharedWithMeFolder) {
		
	}
}
