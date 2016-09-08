package org.ufcg.si.models.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.ufcg.si.exceptions.InvalidDataException;
import org.ufcg.si.exceptions.MissingItemException;
import org.ufcg.si.util.ServerConstants;

/**
 * This class represents a User's Directory. It is represented as a graph, since
 * It is possible to have a parent directory and children. Every Directory also
 * contains a List of UserFiles.
 */
@Entity
public class FolderGB {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String path;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<FileGB> files;
	@OneToMany(cascade = CascadeType.ALL)
	private List<FolderGB> folders;

	public FolderGB(String name, String path) {
		this.name = name;
		this.files = new ArrayList<>();
		this.folders = new ArrayList<>();
		this.path = path;
	}
	
	public FolderGB() {
		
	}
	
	public FolderGB(String name) {
		this(name, name);
	}
	
	public void addFile(String name, String extension, String content) throws IOException {
		FileGB newFile = StorageFactory.createFile(name, extension, content, this.path);
		
		if (files.contains(newFile)) {
			throw new InvalidDataException("Invalid name: " + name + " already in use."); 
		}
		
		files.add(newFile);
	}
	
	public void addFile(String name, String extension, String content, String path) throws IOException, InvalidDataException {
		findFolderByPath(path).addFile(name, extension, content);
	}
	
	public void addFile(FileGB file) {
		files.add(file);
	}
	
	public void addFolder(String name) throws InvalidDataException {
		FolderGB newFolder = StorageFactory.createFolder(name, this.path);
		
		if (folders.contains(newFolder)) {
			throw new InvalidDataException("Invalid name " + name + " already in use."); 
		}
		
		folders.add(newFolder);
	}
	
	public void addFolder(String name, String path) throws MissingItemException, InvalidDataException {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		System.out.println("Splited path : " + Arrays.toString(splPath));
		FolderGB folderToAdd = findFolderByName(splPath, 0);
		folderToAdd.addFolder(name);
	}
	
	public void addFolder(FolderGB folder) {
		folders.add(folder);
	}
	
	public void editFileContent(String name, String newContent, String path) throws IOException {
		findFileByPathAndName(name,path).setContent(newContent);
	}
	
	public void editFileName(String newName, String oldName, String path) {
		findFileByPathAndName(oldName,path).rename(newName);
	}
	
	public void editFileExtension(String newExtension, String name, String path) {
		findFileByPathAndName(name, path).rename(name, newExtension);
	}
	
	public void editFolderName(String newName, String oldName, String path) {
		findFolderByPathAndName(oldName, path).editFolderName(newName);
	}
	
	public void editFolderName(String newName) {
		if (path == null) {
			path = "";
		}
		
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		int depthLevel = splPath.length - 1;
		List<String> discovered = new ArrayList<String>();
		renamePath(newName, depthLevel);
		name = newName;
		discovered.add(this.getName());
		recursiveRename(discovered, newName, depthLevel);
	}
	
	public List<FileGB> getFiles() {
		return files;
	}
	
	public List<FolderGB> getFolders() {
		return folders;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getName() {
		return name;
	}

	public FileGB findFileByPathAndName(String name, String path) {
		FolderGB folder = findFolderByPath(path);
		return folder.findFileByName(name);
	}
	
	public FolderGB findFolderByPathAndName(String name, String path) {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		FolderGB folder = findFolderByName(splPath, 0);
		return folder.findFolderByName(name);
	}
	
	public FolderGB findFolderByPath(String path) {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		return findFolderByName(splPath, 0);	
	}
	
	public FolderGB findFolderByName(String name) throws MissingItemException {
		for (FolderGB folder : this.folders) {
			if (folder.getName().equals(name)) {
				return folder;
			}
		}
		
		throw new MissingItemException("Folder: " + name + " not found in collection: " + this.folders);
	}
	
	private void recursiveRename(List<String> discovered, String newName, int depthLevel) {
		for (FolderGB folder : folders) {
			if (!discovered.contains(folder.getName())) {
				discovered.add(folder.getName());
				folder.renamePath(newName, depthLevel);
				
				for (FileGB file : files) {
					String[] fsplPath = file.getPath().split(ServerConstants.PATH_SEPARATOR);
					fsplPath[depthLevel] = newName;
					file.setPath(String.join(ServerConstants.PATH_SEPARATOR, fsplPath));
				}
				
				folder.recursiveRename(discovered, newName, depthLevel);
			}
		}
	}
	
	private void renamePath(String newName, int depthLevel) {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		splPath[depthLevel] = newName;
		path = String.join(ServerConstants.PATH_SEPARATOR, splPath);
	}
	
	private FolderGB findFolderByName(String[] splPath, int currentIndex) throws MissingItemException {
		if (splPath.length - 1 == currentIndex) {
			return this;
		} else {
			return findFolderByName(splPath[currentIndex + 1]).findFolderByName(splPath, currentIndex + 1);
		}
	}

	public FileGB findFileByName(String name) throws MissingItemException {
		for (FileGB file : this.files) {
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
		if (obj instanceof FolderGB) {
			FolderGB otherFolder = (FolderGB) obj;
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
