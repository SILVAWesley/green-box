package org.ufcg.si.models.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.Session;
import org.ufcg.si.exceptions.InvalidDataException;
import org.ufcg.si.exceptions.NotEnoughAccessLevel;
import org.ufcg.si.models.Notification;
import org.ufcg.si.util.permissions.file.FileActions;
import org.ufcg.si.util.permissions.file.FilePermissions;
import org.ufcg.si.util.permissions.folder.FolderActions;
import org.ufcg.si.util.permissions.folder.FolderPermissions;


/**
 * This class is responsible for managing all the user's actions. As consequence, it is
 * also responsible to manage all files and folders permission levels and notification
 * system
 */
@Entity
public class UserActionsManager {
	public static final String SHARED_WITH_ME_FOLDER_NAME = "Shared with me";
	public static final String I_SHARED_FOLDER_NAME = "Files you shared";
	public static final String TRASH_NAME = "Trash";
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	private FolderGB rootFolder; 
	@OneToMany(cascade = CascadeType.ALL)
	private List<Notification> notifications;
	@ElementCollection
	private Map<FileGB, FilePermissions> filePermissions;
	@ElementCollection
	private Map<FolderGB, FolderPermissions> folderPermissions;
	
	/**
	 * Creates a new UserActionsManager. During it's creation, two maps are created, one
	 * to save all user's folders with its respective permission and another one for files.
	 * It is also created a List to save notifications regarding file sharing. Besides that,
	 * Three folders are created: The root folder, that contains all files and folders created
	 * by the user; The 'shared with me' folder, containing all folders shared with the user;
	 * And an 'I shared folder' with all the files that the user had shared with other users.
	 * 
	 * @param name
	 * 		The manager name, that will also be the root folder's name. Should be the same as
	 * 		the user's username.
	 */
	public UserActionsManager(String name) {
		this.name = name;
		this.filePermissions = new HashMap<>();
		this.folderPermissions = new HashMap<>();
		this.notifications = new ArrayList<>();
		
		this.rootFolder = new FolderGB(name, "");
		this.rootFolder.addFolder(name);
		this.rootFolder.addFolder("Trash");
		this.rootFolder.addFolder(SHARED_WITH_ME_FOLDER_NAME);
		this.rootFolder.addFolder(I_SHARED_FOLDER_NAME);
		
		this.folderPermissions.put(retrieveSharedWithMe(), FolderPermissions.SHARED);
		this.folderPermissions.put(retrieveFilesIShared(), FolderPermissions.SHARED);
		this.folderPermissions.put(retrieveTrash(), FolderPermissions.REMOVED);
	}
	
	/**
	 * Creates a default manager with an empty name.
	 */
	public UserActionsManager() {
		this("");
	}
	
	/**
	 * Find a folder through a path and creates a new file in this folder, if it exists.
	 * An exception is thrown if there is no folder for the path or if the folder doesn't
	 * have permission to create a file.
	 * @param name
	 * 		The file name
	 * @param extension
	 * 		The file extension (md or txt)
	 * @param content
	 * 		The file initial content
	 * @param path
	 * 		The path to the folder in which the file will be created
	 */
	public void addFile(String name, String extension, String content, String path) {
		FolderGB folder = rootFolder.findFolderByPath(path);
		FolderPermissions permission = findFolderPermission(folder.getName(), folder.getPath());
		
		if (permission.isAllowed(FolderActions.ADD_FILE)) {
			rootFolder.addFile(name, extension, content, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	/**
	 * Find a folder through a path and creates a new folder in this folder, if it exists.
	 * An exception is thrown if there is no folder for the path or if the folder doesn't
	 * have permission to create a folder.
	 * @param name
	 * 		The folder name
	 * @param path
	 * 		The path to the folder in which the new folder will be created
	 */
	public void addFolder(String name, String path) {
		FolderGB folder = rootFolder.findFolderByPath(path);
		FolderPermissions permission = findFolderPermission(folder.getName(), folder.getPath());
		
		if (permission.isAllowed(FolderActions.ADD_FOLDER)) {
			rootFolder.addFolder(name, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	/**
	 * Shares a file with an user. There are two permission levels for a shared file: Read Only (R) and Read Write (RW).
	 * An exception is thrown if the user that will receive the file doesn't exist or if you can't share the desired
	 * file.
	 * @param managerToShareWith
	 * 		The UserActionsMenager of the User that will receive the file.
	 * @param name
	 * 		The name of the file to be shared
	 * @param path
	 * 		The path of the file to be shared
	 * @param extension
	 * 		The extension of the file to be shared
	 * @param permission
	 * 		The sharing permission (R or RW)
	 */
	public void shareFile(UserActionsManager managerToShareWith, String name, String path, String extension, FilePermissions permission) {
		FileGB fileToShare = rootFolder.findFileByEverything(name, path, extension);
		FilePermissions filePermission = findFilePermission(name, extension, fileToShare.getPath());
		
		if (filePermission.isAllowed(FileActions.SHARE)) {
			if (!this.equals(managerToShareWith)) {
				FileGB sharingFile = rootFolder.findFileByEverything(name, path, extension);
				retrieveFilesIShared().addFile(sharingFile);
				managerToShareWith.receiveFile(sharingFile);
				managerToShareWith.filePermissions.put(sharingFile, permission);
				managerToShareWith.notifications.add(new Notification("A file named " + "'" + name + "' " + "was shared with you by : " + this.name + "."));
			} else {
				throw new InvalidDataException("You cannot share a file with yourself.");
			}
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + filePermission + " is not enough to complete the operation.");
		}
	}
	
	/**
	 * Return the iterable of the notification's list
	 * @return
	 * 		The iterable of the notification's list
	 */
	public Iterable<Notification> listNotifications() {
		return notifications;
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
	public void editFileContent(String name, String newContent, String extension, String path) {
		FileGB fileToEdit = rootFolder.findFileByEverything(name, path, extension);
		FilePermissions permission = findFilePermission(name, extension, fileToEdit.getPath());

		if (permission.isAllowed(FileActions.EDIT_CONTENT)) {
			rootFolder.editFileContent(newContent, name, extension, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
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
		FileGB fileToEdit = rootFolder.findFileByEverything(name, path, oldExtension);
		FilePermissions permission = findFilePermission(name, oldExtension, fileToEdit.getPath());
		
		if (permission.isAllowed(FileActions.EDIT_EXTENSION)) {
			rootFolder.editFileExtension(newExtension, name, oldExtension, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
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
		FileGB fileToEdit = rootFolder.findFileByEverything(oldName, path, extension);
		FilePermissions permission = findFilePermission(oldName, extension, fileToEdit.getPath());
		
		if (permission.isAllowed(FileActions.EDIT_NAME)) {
			rootFolder.editFileName(newName, oldName, extension, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
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
		FolderGB folder = rootFolder.findFolderByPathAndName(oldName, path);
		FolderPermissions permission = findFolderPermission(folder.getName(), folder.getPath());
		
		if (permission.isAllowed(FolderActions.EDIT_FOLDER_NAME)) {
			rootFolder.editFolderName(newName, oldName, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @param extension
	 * @param path
	 */
	
	public void deleteFile(String name, String extension, String path) {
		FolderGB folder = rootFolder.findFolderByPath(path);
		FolderPermissions permission = findFolderPermission(folder.getName(), folder.getPath());
		
		if (permission.isAllowed(FolderActions.DELETE_FILE)) {
			FileGB fileToTrash = rootFolder.deleteFile(name, extension, path);
			retrieveTrash().addFile(fileToTrash);
			filePermissions.put(fileToTrash, FilePermissions.REMOVED);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
		
	}
	
	public void deleteFolder(String path, String name) {
		FolderGB folder = rootFolder.findFolderByPath(path);
		FolderPermissions permission = findFolderPermission(folder.getName(), folder.getPath());

		if (permission.isAllowed(FolderActions.DELETE_FOLDER)) {
			FolderGB folderToTrash = rootFolder.deleteFolder(path, name); 
			FolderGB copyFolder = new FolderGB(folderToTrash); 
			retrieveTrash().addFolder(copyFolder);
			folderPermissions.put(copyFolder, FolderPermissions.REMOVED);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	/**
	 * Return the automated generated manager's ID
	 * @return the manager ID
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Return the manager's name, that should be the same as the User
	 * @return the manager name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return the root folder, which is the one that have everything created by
	 * the own user.
	 * @return the root folder
	 */
	public FolderGB getRootFolder() {
		return rootFolder;
	}
	
	/**
	 * Return the list of notifications of files being shared with the User.
	 * @return the list of notification
	 */
	public List<Notification> getNotifications() {
		return notifications;
	}
	
	@Override
	public String toString(){
		return rootFolder.toString();
	}
	
	// If a file doesnt have the R or RW permission, then it is a file created by the User.
	private FilePermissions findFilePermission(String name, String extension, String path) {
		Set<FileGB> files = filePermissions.keySet();
		
		for (FileGB file : files) {
			if (file.getName().equals(name) 
			 && file.getPath().equals(path)
			 && file.getExtension().equals(extension)) {
				return filePermissions.get(file);
			}
		}
		
		return FilePermissions.OWNER;
	}
	
	// If a folder isn't the 'I shared folder' nor the 'Shared with me' then 
	// it was created by the User.
	private FolderPermissions findFolderPermission(String name, String path) {
		Set<FolderGB> folders = folderPermissions.keySet();
		
		for (FolderGB folder : folders) {
			if (folder.getName().equals(name) && folder.getPath().equals(path)) {
				return folderPermissions.get(folder);
			}
		}
		
		return FolderPermissions.OWNER;
	}
	
	/*
	 * Method called when someone shares a file with this user.
	 * Adds the file to the 'Shared with me' folder.
	 */
	private void receiveFile(FileGB sharedFile) {
		retrieveSharedWithMe().addFile(sharedFile);
	}
	
	private FolderGB retrieveSharedWithMe() {
		return rootFolder.findFolderByName(SHARED_WITH_ME_FOLDER_NAME);
	}
	
	private FolderGB retrieveFilesIShared() {
		return rootFolder.findFolderByName(I_SHARED_FOLDER_NAME);
	}
	
	private FolderGB retrieveTrash() {
		return rootFolder.findFolderByName(TRASH_NAME);
	}
}
