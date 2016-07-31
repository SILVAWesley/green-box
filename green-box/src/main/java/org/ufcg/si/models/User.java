package org.ufcg.si.models;

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
	
	@OneToOne
    private UserDirectory userdirectory;
	
	@Column(unique = true)
    private String username;
	@Column(unique = true)
    private String email;
    private String password;
    
    public User(){

    }
    
    /**
     * This constructor receives three strings: an email, an username and a password
     * to create a new User. The most external UserDirectory is also created. 
     * @param email
     * 		 The user's email
     * @param username
     * 		 The user's name
     * @param password
     * 		 The user's password
     */
    public User(String email, String username, String password) {
        this.userdirectory = new UserDirectory(username);
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Creates a new UserFile in the current UserDirectory
     * @param filename
     * 		The file's name
     * @param fileContent
     * 		The file's content
     * @throws Exception if the file exists but is a directory rather than a regular file,
     *  does not exist but cannot be created, or cannot be opened for any other reason
     */
    public void createFile(String filename, StringBuffer fileContent) throws Exception{
        userdirectory.createFile(filename, ".txt" ,fileContent); // An enum should be created for file extension
    }
    
    /**
     * Creates a new UserDirectory in the current UserDirectory
     * @param directoryName
     * 		The new directory's name
     */
    public void createDirectory(String directoryName){
        userdirectory.createDirectory(directoryName);
    }
    
    // GETTERS

    public String getUsername(){
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserDirectory getUserDirectory(){
        return userdirectory;
    }
    
    public Long getId(){
    	return id;
    }

    @Override
    public String toString() {
    	return "{Username:" + username + ", Email:" + email + "}";
    }
}
