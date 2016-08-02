package org.ufcg.si.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * This class represents a Green-Box user
 */
@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private UserDirectory userdirectory;

	@Column(unique = true)
	private String username;
	@Column(unique = true)
	private String email;
	private String password;

	public User() throws Exception {
		this.userdirectory = new UserDirectory("root");
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
		this.userdirectory = new UserDirectory(username);
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
		userdirectory.createFile(filename, ".txt", content); 
	}

	/**
	 * Creates a new UserDirectory in the current UserDirectory
	 * 
	 * @param directoryName
	 *            The new directory's name
	 * @throws Exception 
	 */
	public void createDirectory(String directoryName) throws Exception {
		userdirectory.createDirectory(directoryName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User temp = (User) obj;

			return this.getUsername().equals(temp.getUsername()) && this.getEmail().equals(temp.getEmail())
					&& this.getPassword().equals(temp.getPassword())
					&& this.getUserDirectory().equals(temp.getUserDirectory());
		} else {
			return false;
		}

	}
	// GETTERS

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public UserDirectory getUserDirectory() {
		return userdirectory;
	}

	public Long getId() {
		return id;
	}

}
