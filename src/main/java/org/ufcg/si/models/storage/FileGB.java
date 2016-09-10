	package org.ufcg.si.models.storage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

	public FileGB(String name, String extension, String content, String path) {
		this.name = name;
		this.extension = extension;
		this.path = path;
		this.content = content;
	}
	
	public FileGB() {
		this("", "", "", "");
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		this.name = newName;
		String[] splPath = StorageUtilities.splitPath(path);
		splPath[splPath.length - 1] = newName;
		path = StorageUtilities.joinPath(splPath);
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String newExtension) {
		this.extension = newExtension;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String newContent) {
		this.content = newContent;
	}
	
	public String getPath() {
		return path;
	}
	
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
