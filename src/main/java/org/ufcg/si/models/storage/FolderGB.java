package org.ufcg.si.models.storage;

import java.util.ArrayList;
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
		this.path = path;
		this.files = new ArrayList<>();
		this.folders = new ArrayList<>();
	}
	
	public FolderGB(String name) {
		this(name, name);
	}
	
	public FolderGB() {
		this("", "");
	}
	
	public void addFile(String name, String extension, String content) {
		FileGB newFile = StorageFactory.createFile(name, extension, content, path);
		checkIfIsDuplicatedFile(newFile);
		files.add(newFile);
	}
	
	public void addFile(String name, String extension, String content, String path) {
		findFolderByPath(path).addFile(name, extension, content);
	}
	
	public void addFile(FileGB newFile) {
		checkIfIsDuplicatedFile(newFile);
		files.add(newFile);
	}
	
	public void addFolder(String name) throws InvalidDataException {
		FolderGB newFolder = StorageFactory.createFolder(name, path);
		checkIfIsDuplicatedFolder(newFolder);
		folders.add(newFolder);
	}
	
	public void addFolder(String name, String path) throws MissingItemException, InvalidDataException {
		findFolderByPath(path).addFolder(name);
	}
	
	public void addFolder(FolderGB newFolder) {
		checkIfIsDuplicatedFolder(newFolder);
		folders.add(newFolder);
	}
	
	public void editFileContent(String newContent, String name, String extension, String path) {
		FileGB fileToEdit = findFileByEverything(name, path, extension);
		checkIfFileExists(fileToEdit);
		fileToEdit.setContent(newContent);
	}
	
	public void editFileName(String newName, String oldName, String extension, String path) {	
		FileGB fileToEdit = findFileByEverything(oldName, path, extension);
		FileGB duplicatedFile = findFileByEverything(newName, path, extension);
		
		if (duplicatedFile != null) {
			throw new InvalidDataException("Name '" + newName + "." + extension + "' already in use.");
		}
		
		checkIfFileExists(fileToEdit);
		fileToEdit.setName(newName);
	}
	
	public void editFileExtension(String newExtension, String name, String oldExtension, String path) {
		FileGB fileToEdit = findFileByEverything(name, path, oldExtension);
		FileGB duplicatedFile = findFileByEverything(name, path, newExtension);
		
		if (duplicatedFile != null) {
			throw new InvalidDataException("Name '" + name + "." + newExtension + "' already in use.");
		}
		
		checkIfFileExists(fileToEdit);
		fileToEdit.setExtension(newExtension);
	}
	
	public void editFolderName(String newName, String oldName, String path) {
		findFolderByPathAndName(oldName, path).setName(newName);
	}

	public FileGB findFileByEverything(String name, String path, String extension) {
		FolderGB folder = findFolderByPath(path);
		
		if (folder != null) {
			FileGB file = folder.findFileByNameAndExtension(name, extension);
			return file;
		}
		
		return null;
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
	
	public FileGB findFileByNameAndExtension(String name, String extension) {
		for (FileGB file : files) {
			if (file.getName().equals(name) && file.getExtension().equals(extension)) {
				return file;
			}
		}
		
		return null;
	}
	
	public FolderGB findFolderByName(String name) throws MissingItemException {
		for (FolderGB folder : this.folders) {
			if (folder.getName().equals(name)) {
				return folder;
			}
		}
		
		return null;
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
	
	public void setName(String newName) {
		String[] splPath = StorageUtilities.splitPath(path);
		int depthLevel = splPath.length - 1;
		List<String> discovered = new ArrayList<String>();
		fixPath(newName, depthLevel);
		name = newName;
		discovered.add(this.getName());
		recursiveSetName(discovered, newName, depthLevel);
	}
	
	private void recursiveSetName(List<String> discovered, String newName, int depthLevel) {
		for (FolderGB folder : folders) {
			if (!discovered.contains(folder.getName())) {
				discovered.add(folder.getName());
				folder.fixPath(newName, depthLevel);
				
				for (FileGB file : files) {
					String[] fsplPath = StorageUtilities.splitPath(file.getPath());
					fsplPath[depthLevel] = newName;
					file.setPath(StorageUtilities.joinPath(fsplPath));
				}
				
				folder.recursiveSetName(discovered, newName, depthLevel);
			}
		}
	}
	
	private void fixPath(String newName, int depthLevel) {
		String[] splPath = StorageUtilities.splitPath(path);
		splPath[depthLevel] = newName;
		path = StorageUtilities.joinPath(splPath);
	}
	
	private FolderGB findFolderByName(String[] splPath, int currentIndex) throws MissingItemException {
		if (splPath.length - 1 == currentIndex) {
			return this;
		} else {
			return findFolderByName(splPath[currentIndex + 1]).findFolderByName(splPath, currentIndex + 1);
		}
	}
	
	private void checkIfIsDuplicatedFile(FileGB file) {
		if (files.contains(file)) {
			throw new InvalidDataException("Name '" + file.getName() + "." + file.getExtension() + "' already in use.");
		}
	}
	
	private void checkIfIsDuplicatedFolder(FolderGB folder) {
		if (folders.contains(folder)) {
			throw new InvalidDataException("Name '" + folder.getName() + "' already in use.");
		}
	}
	
	private void checkIfFileExists(FileGB file) {
		if (file == null) {
			throw new MissingItemException("A file was not found in collection: " + files + "...");
		}
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
