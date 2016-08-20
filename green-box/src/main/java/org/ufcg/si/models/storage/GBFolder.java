package org.ufcg.si.models.storage;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.ufcg.si.exceptions.MissingItemException;
import org.ufcg.si.util.ServerConstants;

/**
 * This class represents a User's Directory. It is represented as a graph, since
 * It is possible to have a parent directory and children. Every Directory also
 * contains a List of UserFiles.
 */
@Entity
public class GBFolder {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String path;
	
	@ElementCollection
	private List<GBFile> files;
	@OneToMany(cascade = CascadeType.ALL)
	private List<GBFolder> folders;

	public GBFolder(String name, String path) {
		this.name = name;
		this.files = new ArrayList<>();
		this.folders = new ArrayList<>();
		this.path = path;
	}

	public GBFolder(String name) {
		this(name, name);
	}
	
	public GBFolder() {
		this(null, null);
	}
	
	public void addFile(String name, String extension, String content) throws Exception {
		GBFile newFile = StorageFactory.createFile(name, extension, content, this.path);
		files.add(newFile);
	}
	
	public void addFile(String name, String extension, String content, String path) throws Exception {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		GBFolder folderToAdd = findFolderByName(splPath, 0);
		folderToAdd.addFile(name, extension, content);
	}
	
	public void addFolder(String name) {
		GBFolder newFolder = StorageFactory.createFolder(name, this.path); 
		folders.add(newFolder);
	}
	
	public void addFolder(String name, String path) throws Exception {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		GBFolder folderToAdd = findFolderByName(splPath, 0);
		folderToAdd.addFolder(name);
	}
	
	public void editFile(String name, String newContent, String path) throws Exception {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		GBFolder folder = findFolderByName(splPath, 0);
		folder.findFileByName(name).setContent(newContent);
	}

	public List<GBFile> getFiles() {
		return files;
	}
	
	public List<GBFolder> getFolders() {
		return folders;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
		
		String[] splPath = this.path.split(ServerConstants.PATH_SEPARATOR);
		this.path = "";
		
		for (int i = 0; i < splPath.length - 1; i++) {
			this.path += splPath[i] + "-";
		}
		
		this.path += name;
	}
	
	private GBFolder findFolderByName(String name) throws MissingItemException {
		for (GBFolder folder : this.folders) {
			if (folder.getName().equals(name)) {
				return folder;
			}
		}
		
		throw new MissingItemException("Folder: " + name + " not found in collection: " + this.folders);
	}
	
	private GBFolder findFolderByName(String[] splPath, int currentIndex) throws Exception {
		if (splPath.length == 1) {
			return this;
		} else if (currentIndex == splPath.length - 2) {
			return findFolderByName(splPath[currentIndex + 1]); 
		} else if (currentIndex == 0) {
			return findFolderByName(splPath, currentIndex + 1);
		} else {
			GBFolder childFolder = findFolderByName(splPath[currentIndex]);  
			return childFolder.findFolderByName(splPath, currentIndex + 1);
		}
	}
	
	private GBFile findFileByName(String name) throws MissingItemException {
		for (GBFile file : this.files) {
			if (file.getName().equals(name)) {
				return file;
			}
		}
		
		throw new MissingItemException("File: " + name + " not found in collection: " + this.files);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GBFolder) {
			GBFolder otherFolder = (GBFolder) obj;
			return this.getName().equals(otherFolder.getName()) && this.getPath().equals(otherFolder.getPath());
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "{" 
				+ "Type: " + this.getClass().getName() + ", "
				+ "Id: " + this.id + ", "
				+ "Name: " + this.name + ", "
				+ "Path: " + this.path + ", "
				+ "Child Folders: " + this.folders + ", "
				+ "Child Files: " + this.files
				+ "}";
	}
}
