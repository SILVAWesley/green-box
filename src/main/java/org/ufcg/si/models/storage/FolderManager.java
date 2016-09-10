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

import org.ufcg.si.exceptions.NotEnoughAccessLevel;
import org.ufcg.si.models.Notification;
import org.ufcg.si.util.permissions.file.FileActions;
import org.ufcg.si.util.permissions.file.FilePermissions;
import org.ufcg.si.util.permissions.folder.FolderActions;
import org.ufcg.si.util.permissions.folder.FolderPermissions;

@Entity
public class FolderManager {
	public static final String SHARED_WITH_ME_FOLDER_NAME = "Shared with me";
	public static final String I_SHARED_FOLDER_NAME = "Files you shared";
	
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
	
	public FolderManager(String name) {
		this.name = name;
		this.filePermissions = new HashMap<>();
		this.folderPermissions = new HashMap<>();
		this.notifications = new ArrayList<>();
		
		this.rootFolder = new FolderGB(name, "");
		this.rootFolder.addFolder(name);
		this.rootFolder.addFolder(SHARED_WITH_ME_FOLDER_NAME);
		this.rootFolder.addFolder(I_SHARED_FOLDER_NAME);
		
		this.folderPermissions.put(retrieveSharedWithMe(), FolderPermissions.SHARED);
		this.folderPermissions.put(retrieveFilesIShared(), FolderPermissions.SHARED);
	}
	
	public FolderManager() {
		this("");
	}
	
	public void addFile(String name, String extension, String content, String path) {
		FolderGB folder = rootFolder.findFolderByPath(path);
		FolderPermissions permission = findFolderPermission(folder.getName(), folder.getPath());
		
		if (permission.isAllowed(FolderActions.ADD_FILE)) {
			rootFolder.addFile(name, extension, content, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}

	public void addFolder(String name, String path) {
		FolderGB folder = rootFolder.findFolderByPath(path);
		FolderPermissions permission = findFolderPermission(folder.getName(), folder.getPath());
		
		if (permission.isAllowed(FolderActions.ADD_FOLDER)) {
			rootFolder.addFolder(name, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	public void shareFile(FolderManager directoryToShareWith, String name, String path, String extension, FilePermissions permission) {
		FilePermissions filePermission = findFilePermission(name, extension, path);
		
		if (filePermission.isAllowed(FileActions.SHARE)) {
			FileGB sharingFile = rootFolder.findFileByEverything(name, path, extension);
			retrieveFilesIShared().addFile(sharingFile);
			directoryToShareWith.receiveFile(sharingFile);
			directoryToShareWith.filePermissions.put(sharingFile, permission);
			directoryToShareWith.notifications.add(new Notification("A file named " + "'" + name + "' " + "was shared with you by : " + this.name + "."));
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + filePermission + " is not enough to complete the operation.");
		}
	}
	
	public Iterable<Notification> listNotifications() {
		return notifications;
	}
	
	public void editFileContent(String name, String newContent, String extension, String path) {
		FileGB fileToEdit = rootFolder.findFileByEverything(name, path, extension);
		FilePermissions permission = findFilePermission(name, extension, fileToEdit.getPath());

		if (permission.isAllowed(FileActions.EDIT_CONTENT)) {
			rootFolder.editFileContent(newContent, name, extension, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	public void editFileExtension(String newExtension, String name, String oldExtension, String path) {
		FileGB fileToEdit = rootFolder.findFileByEverything(name, path, oldExtension);
		FilePermissions permission = findFilePermission(name, oldExtension, fileToEdit.getPath());
		
		if (permission.isAllowed(FileActions.EDIT_EXTENSION)) {
			rootFolder.editFileExtension(newExtension, name, oldExtension, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	public void editFileName(String newName, String oldName, String extension, String path) {
		FileGB fileToEdit = rootFolder.findFileByEverything(oldName, path, extension);
		FilePermissions permission = findFilePermission(oldName, extension, fileToEdit.getPath());
		
		if (permission.isAllowed(FileActions.EDIT_NAME)) {
			rootFolder.editFileName(newName, oldName, extension, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	public void editFolderName(String newName, String oldName, String path) {
		FolderGB folder = rootFolder.findFolderByPathAndName(oldName, path);
		FolderPermissions permission = findFolderPermission(folder.getName(), folder.getPath());
		
		if (permission.isAllowed(FolderActions.EDIT_FOLDER_NAME)) {
			rootFolder.editFolderName(newName, oldName, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public FolderGB getRootFolder() {
		return rootFolder;
	}
	
	public List<Notification> getNotifications() {
		return notifications;
	}
	
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
	
	private FolderPermissions findFolderPermission(String name, String path) {
		Set<FolderGB> folders = folderPermissions.keySet();
		
		for (FolderGB folder : folders) {
			if (folder.getName().equals(name) && folder.getPath().equals(path)) {
				return folderPermissions.get(folder);
			}
		}
		
		return FolderPermissions.OWNER;
	}
	
	private void receiveFile(FileGB sharedFile) {
		retrieveSharedWithMe().addFile(sharedFile);
	}

	private FolderGB retrieveSharedWithMe() {
		return rootFolder.findFolderByName(SHARED_WITH_ME_FOLDER_NAME);
	}
	
	private FolderGB retrieveFilesIShared() {
		return rootFolder.findFolderByName(I_SHARED_FOLDER_NAME);
	}
}
