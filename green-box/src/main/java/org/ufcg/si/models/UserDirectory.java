package org.ufcg.si.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class represents a User's Directory. It is represented as a graph, since
 * It is possible to have a parent directory and children. Every Directory also
 * contains a List of UserFiles.
 */
@Entity
public class UserDirectory {
	@Id
	@GeneratedValue
	private Long id;

	@ElementCollection
	private List<UserFile> files;

	@OneToMany(cascade = CascadeType.ALL)
	private List<UserDirectory> children;

	private String name;
	private String path;

	/**
	 * This constructor receives the directory's name and it's parent to create
	 * a new UserDirectory. It also initializes the list of UserFiles and
	 * UserDirectories(Children)
	 * 
	 * @param name
	 *            The directory's name
	 * @param parent
	 *            The directory's parent directory
	 */
	public UserDirectory(String name, String path) {
		this.name = name;
		this.files = new ArrayList<>();
		this.children = new ArrayList<>();
		this.path = path;
	}

	public UserDirectory() {
		this.files = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	// This constructor should be used only when a new User is created
	/**
	 * This constructor receives the directory's name to create a new
	 * UserDirectory without a parent. It also initializes the list of UserFiles
	 * and UserDirectories(Children). This should only be used when a new User
	 * is created, since it is not normal to have a directory without a parent.
	 * 
	 * @param name
	 *            The directory's name
	 */
	public UserDirectory(String name) {
		this(name, name);
	}

	/**
	 * Creates a new UserFile.
	 * 
	 * @param filename
	 *            The new UserFile's name
	 * @param fileExtension
	 *            The new UserFile's extension
	 * @param fileContent
	 *            The new UserFile's initial content
	 * @throws Exception
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 */
	public void createFile(String fileName, String fileExtension, String fileContent) throws Exception {
		UserFile file = new UserFile(fileName, fileExtension, fileContent);
		if(files.contains(file)) throw new Exception("Invalid name: Name already in use");
		files.add(file);
	}
	
	/**
	 * This method find the last create directory by the path, and create a new file in the directory 
	 * 
	 * @param fileName The new File's name
	 * @param fileExtension The extension of the file	
	 * @param fileContent The content of the file
	 * @param filePath The path to found the directory where we will create the file
	 * @throws Exception
	 */
	public void createFile(String fileName, String fileExtension, String fileContent, String filePath) throws Exception{
		String[] pathFolders = filePath.split("-");
		recursiveCreateFile(pathFolders, 0, fileName, fileExtension, fileContent);
	}
	
	private void recursiveCreateFile(String[] pathFolders, int actualIndex, String fileName, String fileExtension, String fileContent) throws Exception {
		if(actualIndex == pathFolders.length - 1){
			getChildDirectory(pathFolders[actualIndex]).createFile(fileName, fileExtension, fileContent);
		}else{ 
			getChildDirectory(pathFolders[actualIndex]).recursiveCreateFile(pathFolders, ++actualIndex, fileName, fileExtension, fileContent);
		}
	}
	
	/**
	 * Creates a new UserDirectory, with this one as a Parent.
	 * 
	 * @param directoryName
	 *            The new Directory's name
	 * @throws Exception if there is already a directory with the directoryName
	 */
	
	public void createDirectory(String directoryName) throws Exception {
		UserDirectory dir = new UserDirectory(directoryName, this.path + "/" + directoryName);
		if(children.contains(dir)) throw new Exception("Invalid name: Name already in use");
		this.children.add(dir);
	}
	
	/**
	 * 
	 * This method create a new directory inside of another directory 
	 * 
	 * @param directoryName The new Directory's name
	 * @param directoryPath The path of new directory	
	 * @throws Exception if there is no directory in the directoryPath
	 */
	public void createDirectory(String directoryName, String directoryPath) throws Exception{
		String[] pathFolders = directoryPath.split("-");
		recursiveCreateDirectory(pathFolders, 0, directoryName);
	}
	
	private void recursiveCreateDirectory(String[] pathFolders, int actualIndex, String directoryName) throws Exception {
		if(actualIndex == pathFolders.length - 1){
			getChildDirectory(pathFolders[actualIndex]).createDirectory(directoryName);
		}else{ 
			getChildDirectory(pathFolders[actualIndex]).recursiveCreateDirectory(pathFolders, ++actualIndex, directoryName);
		}
	}
	
	public void editFile(String directoryPath, String fileName, String newContent) throws Exception {
		String[] pathFolders = directoryPath.split("-");
		recursiveEditFile(pathFolders, 0, fileName, newContent);
	}
	
	private void recursiveEditFile(String[] pathFolders, int actualIndex, String fileName, String newContent) throws Exception {
		if(actualIndex == pathFolders.length - 1){
			getChildDirectory(pathFolders[actualIndex]).getChildFile(fileName).setContent(newContent);
		}else{ 
			getChildDirectory(pathFolders[actualIndex]).recursiveEditFile(pathFolders, ++actualIndex, fileName, newContent);
		}
	}
	
	public UserFile getChildFile(String fileName) throws Exception{
		for(UserFile file: this.files){
			if(file.getName().equals(fileName)){
				return  file;
			}
		}
		throw new Exception("File not found");
	} 
	
	/**
	 * Find the child of a directory
	 * 
	 * @param dirName The directory name you want Find Your child
	 * @return The child of the directory
	 * @throws Exception if there is no directory with the dirName
	 */
	public UserDirectory getChildDirectory(String dirName) throws Exception{
		for(UserDirectory dir: this.children){
			if(dir.getName().equals(dirName)){
				return dir;
			}
		}
		throw new Exception("Directory not found");
	}
	
	/**
	 * Compares an object to this one to see if they are equals. Two UserDirectories are equals
	 * if they have the same name.
	 * @param obj
	 * 		The object to be compared
	 * @return if obj is equals to the object
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDirectory) {
			UserDirectory temp = (UserDirectory) obj;
			return this.getName().equals(temp.getName());
		} else {
			return false;
		}
	}

	// GETTERS
	
	
	/**
	 * The files getter. Files is a list of intern files.
	 * @return A list of files inside of this object
	 */
	public List<UserFile> getFiles() {
		return files;
	}
	
	/**
	 * The children getter. Children is a list of intern directories.
	 * @return The list of directories
	 */
	public List<UserDirectory> getChildren() {
		return children;
	}
	
	/**
	 * The name getter. The name is used to identify the directory.
	 * @return The directory's name
	 */
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
	/**
	 * Updates the directories name
	 * @param name
	 * 		The new directory's name.
	 */
	public void setName(String name){
		this.name = name;
	}

}
