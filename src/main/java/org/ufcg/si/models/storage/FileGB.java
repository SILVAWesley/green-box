	package org.ufcg.si.models.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represents a File of the GreenBox application
 */
@Entity
public class FileGB {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String extension;
	@Column(length = 10000000)
	private String content;
	private String path;
	
	/**
	 * Creates a new FileGB.
	 * @param name
	 * 		The file name
	 * @param extension
	 * 		The file extension
	 * @param content
	 * 		The file content
	 * @param path
	 * 		The file path
	 */
	public FileGB(String name, String extension, String content, String path) {
		this.name = name;
		this.extension = extension;
		this.path = path;
		this.content = content;
	}
	
	/**
	 * Default constructor with all attributes as empty strings
	 */
	public FileGB() {
		this("", "", "", "");
	}
	
	/**
	 * @return The file automated generated ID
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return The file name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Updates the file name
	 * @param newName
	 * 		The new file name
	 */
	public void setName(String newName) {
		this.name = newName;
		String[] splPath = StorageUtilities.splitPath(path);
		splPath[splPath.length - 1] = newName;
		path = StorageUtilities.joinPath(splPath);
	}
	
	/**
	 * @return The file extension
	 */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * Updates the file extension
	 * @param newExtension
	 * 		The file new extension
	 */
	public void setExtension(String newExtension) {
		this.extension = newExtension;
	}
	
	/**
	 * @return the file content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Updates the file content
	 * @param newContent
	 * 		The new file content
	 */
	public void setContent(String newContent) {
		this.content = newContent;
	}
	
	/**
	 * @return The file path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Updates the file path
	 * @param newPath
	 * 		The new file path
	 */
	public void setPath(String newPath) {
		this.path = newPath;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}
	
	// Two files are equals if they have the same name, path and extension
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FileGB) {
			FileGB otherFile = (FileGB) obj;
			return this.getName().equals(otherFile.getName())
				&& this.getPath().equals(otherFile.getPath())
				&& this.getExtension().equals(otherFile.getExtension());
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "{" 
				+ "Type: " + this.getClass().getName() + ", "
				+ "Name: " + this.name + ", "
				+ "Path: " + this.path + ", "
				+ "Content: " + this.content + ", "
				+ "Extension: " + this.extension
				+ "}";
	}
}
