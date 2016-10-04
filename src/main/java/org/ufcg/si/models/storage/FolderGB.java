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
 * This class represents a User's Folder. It is represented as a graph, since
 * It is possible to have children folders. Every Directory also
 * contains a List of FileGBs.
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
	
	/**
	 * Creates a new folder and initializes the lists of files and folders.
	 * @param name
	 * 		The folder name
	 * @param path
	 * 		The folder path
	 */
	public FolderGB(String name, String path) {
		this.name = name;
		this.path = path;
		this.files = new ArrayList<>();
		this.folders = new ArrayList<>();
	}
	
	/**
	 * Creates a new folder, with the path equal to its name. Used when creating
	 * a root folder.
	 * @param name
	 * 		The folder name
	 */
	public FolderGB(String name) {
		this(name, name);
	}
	
	/**
	 * Default constructor that initializes the folder name and path as empty string.
	 */
	public FolderGB() {
		this("", "");
	}
	
	public FolderGB(FolderGB folder) {
		this.name = folder.getName();
		this.path = folder.getPath();
		this.files = new ArrayList<>();
		this.folders = new ArrayList<>();
	}
	
	/**
	 * Creates and adds a file to the folder. An exception is thrown if the file already exists.
	 * @param name
	 * 		The file name
	 * @param extension
	 * 		The file extension
	 * @param content
	 * 		The file content
	 */
	public void addFile(String name, String extension, String content) {
		FileGB newFile = StorageFactory.createFile(name, extension, content, path);
		checkIfIsDuplicatedFile(newFile);
		files.add(newFile);
	}
	
	/**
	 * Creates and adds a file to a folder with a certain path.
	 * An exception is thrown if the file already exists or if there
	 * is no folder for that path.
	 * @param name
	 * 		The file name
	 * @param extension
	 * 		The file extension
	 * @param content
	 * 		The file content
	 * @param path
	 * 		The path of the folder in which the file will be created and added
	 */
	public void addFile(String name, String extension, String content, String path) {
		findFolderByPath(path).addFile(name, extension, content);
	}
	
	/**
	 * Adds a FileGB to the folder. An exception is thrown if the file already exists
	 * @param newFile
	 * 		The file to be added
	 */
	public void addFile(FileGB newFile) {
		checkIfIsDuplicatedFile(newFile);
		files.add(newFile);
	}
	
	/**
	 * Creates and adds a new folder to the folder. An exception is thrown if the folder already exists.
	 * @param name
	 * 		The folder name
	 */
	public void addFolder(String name) {
		FolderGB newFolder = StorageFactory.createFolder(name, path);
		checkIfIsDuplicatedFolder(newFolder);
		folders.add(newFolder);
	}
	
	/**
	 * Creates and adds a new folder in a folder with a certain path.
	 * An exception is thrown if the folder already exists or if there is no
	 * folder with the path.
	 * @param name
	 * 		The folder name
	 * @param path
	 * 		The path of the folder in which the new folder will be created
	 */
	public void addFolder(String name, String path) {
		findFolderByPath(path).addFolder(name);
	}
	
	/**
	 * Adds a FolderGB to the folder. An exception is thrown if the folder already exists.
	 * @param newFolder
	 * 		The folder to be added
	 */
	public void addFolder(FolderGB newFolder) {
		checkIfIsDuplicatedFolder(newFolder);
		folders.add(newFolder);
	}
	
	/**
	 * Edit a file's content. An exception is thrown if you do not have permission to edit
	 * the file's content
	 * @param name
	 * 		The file name
	 * @param newContent
	 * 		The file new content
	 * @param extension
	 * 		The file extension
	 * @param path
	 * 		The file path
	 */
	public void editFileContent(String newContent, String name, String extension, String path) {
		FileGB fileToEdit = findFileByEverything(name, path, extension);
		checkIfFileExists(fileToEdit);
		fileToEdit.setContent(newContent);
	}
	
	/**
	 * Edit a file's name. An exception is thrown if you do not have permission to
	 * change its name.
	 * @param newName
	 * 		The file new name
	 * @param oldName
	 * 		The file old name
	 * @param extension
	 * 		The file extension
	 * @param path
	 * 		The file path
	 */
	public void editFileName(String newName, String oldName, String extension, String path) {	
		FileGB fileToEdit = findFileByEverything(oldName, path, extension);
		FileGB duplicatedFile = findFileByEverything(newName, path, extension);
		
		if (duplicatedFile != null) {
			throw new InvalidDataException("Name '" + newName + "." + extension + "' already in use.");
		}
		
		checkIfFileExists(fileToEdit);
		fileToEdit.setName(newName);
	}
	
	/**
	 * Edit a file's extension. An exception is thrown if you do not have permission to
	 * change its extension.
	 * @param newExtension
	 * 		The file new extension
	 * @param name
	 * 		The file name
	 * @param oldExtension
	 * 		The file old extension
	 * @param path
	 * 		The file path
	 */
	public void editFileExtension(String newExtension, String name, String oldExtension, String path) {
		FileGB fileToEdit = findFileByEverything(name, path, oldExtension);
		FileGB duplicatedFile = findFileByEverything(name, path, newExtension);
		
		if (duplicatedFile != null) {
			throw new InvalidDataException("Name '" + name + "." + newExtension + "' already in use.");
		}
		
		checkIfFileExists(fileToEdit);
		fileToEdit.setExtension(newExtension);
	}
	
	/**
	 * Edit a folder's name. An exception is thrown if you do not have permission to
	 * change its name.
	 * @param newName
	 * 		The new folder name
	 * @param oldName
	 * 		The old folder name
	 * @param path
	 * 		The folder path
	 */
	public void editFolderName(String newName, String oldName, String path) {
		FolderGB duplicatedFolder = findFolderByPathAndName(newName, path);
		
		if (duplicatedFolder != null) {
			throw new InvalidDataException("Name '" + newName + "' already in use.");
		}
		
		findFolderByPathAndName(oldName, path).setName(newName);
	}
	
	/**
	 * Find a file that is a successor of this folder. Returns null if can't find the file.
	 * @param name
	 * 		The file name
	 * @param path
	 * 		The file path
	 * @param extension
	 * 		The file extension
	 * @return the desired file, or null if it could not be found
	 */
	public FileGB findFileByEverything(String name, String path, String extension) {
		FolderGB folder = findFolderByPath(path);
		
		if (folder != null) {
			FileGB file = folder.findFileByNameAndExtension(name, extension);
			return file;
		}
		
		return null;
	}
	
	/**
	 * Find a folder that is a successor of this folder. Returns null if can't find the folder.
	 * @param name
	 * 		The folder name
	 * @param path
	 * 		The folder path
	 * @return the desired folder, or null if it could not be found
	 */
	public FolderGB findFolderByPathAndName(String name, String path) {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		FolderGB folder = findFolderByName(splPath, 0);
		return folder.findFolderByName(name);
	}
	
	/**
	 * Find a folder that is a successor of this folder. Returns null if can't find the folder.
	 * @param path
	 * 		The folder path
	 * @return the desired folder, or null if it could not be found
	 */
	public FolderGB findFolderByPath(String path) {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		return findFolderByName(splPath, 0);	
	}
	
	/**
	 * Find a file that is a successor of this folder. Returns null if can't find the file.
	 * @param name
	 * 		The file name
	 * @param extension
	 * 		The file extension
	 * @return the desired file, or null if it could not be found
	 */
	public FileGB findFileByNameAndExtension(String name, String extension) {
		for (FileGB file : files) {
			if (file.getName().equals(name) && file.getExtension().equals(extension)) {
				return file;
			}
		}
		
		return null;
	}
	
	/**
	 * Find a folder that is a successor of this folder. Returns null if can't find the folder.
	 * @param name
	 * 		The folder name
	 * @return the desired folder, or null if it could not be found
	 */
	public FolderGB findFolderByName(String name) throws MissingItemException {
		for (FolderGB folder : this.folders) {
			if (folder.getName().equals(name)) {
				return folder;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 */
	
	public FileGB deleteFile(String name, String extension, String path) {
		FolderGB folder = findFolderByPath(path);
		FileGB file = folder.findFileByNameAndExtension(name, extension);
		folder.files.remove(file);
		return file;
	}
	
	/**
	 * 
	 */
	
	public FolderGB deleteFolder(String path, String name) {
		FolderGB folder =  findFolderByPath(path);
		FolderGB folderToRemove = findFolderByPathAndName(name, path);
		folder.getFolders().remove(folderToRemove);
		return folderToRemove;
		
	}
	
	
	/**
	 * @return the list of children files
	 */
	public List<FileGB> getFiles() {
		return files;
	}
	
	/**
	 * @return the list of children folders
	 */
	public List<FolderGB> getFolders() {
		return folders;
	}
	
	/**
	 * @return the folder path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @return the folder name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Updates the folder name
	 * @param newName
	 * 		The new folder name
	 */
	public void setName(String newName) {
		String[] splPath = StorageUtilities.splitPath(path);
		int depthLevel = splPath.length - 1;
		List<String> discovered = new ArrayList<String>();
		fixPath(newName, depthLevel);
		name = newName;
		discovered.add(this.getName());
		recursiveSetName(discovered, newName, depthLevel);
	}
	
	// Used to update the antecessors paths
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
	
	// Fixes a path when a successor is renamed
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
	
	// Two folders are equals if they have the same name and path.
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
