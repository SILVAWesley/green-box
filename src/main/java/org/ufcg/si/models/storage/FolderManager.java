package org.ufcg.si.models.storage;

import java.io.IOException;
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
import org.ufcg.si.util.permissions.FileActions;
import org.ufcg.si.util.permissions.FilePermission;
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
	private Map<FileGB, FilePermission> filePermissions;
	
	public FolderManager(String name) {
		this.name = name;
		this.rootFolder = new FolderGB(name, "");
		this.rootFolder.addFolder(name);
		this.rootFolder.addFolder(SHARED_WITH_ME_FOLDER_NAME);
		this.rootFolder.addFolder(I_SHARED_FOLDER_NAME);
		this.filePermissions = new HashMap<>();
		this.notifications = new ArrayList<>();
	}
	
	public FolderManager() {
		
	}
	
	public void addFile(String name, String extension, String content, String path) throws IOException {
		rootFolder.addFile(name, extension, content, path);
	}

	public void addFolder(String name, String path) {
		rootFolder.addFolder(name, path);
	}
	
	public void shareFile(FolderManager directoryToShareWith, String name, String path, FilePermission permission) {
		FilePermission filePermission = findFilePermission(name, path);
		
		if (filePermission.isAllowed(FileActions.SHARE)) {
			FileGB sharingFile = rootFolder.findFileByPathAndName(name, path);
			iSharedFolder().addFile(sharingFile);
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
	
	public void editFileContent(String name, String newContent, String path) throws IOException {
		FileGB fileToEdit = rootFolder.findFileByPathAndName(name, path);
		FilePermission permission = findFilePermission(name, fileToEdit.getPath());

		if (permission.isAllowed(FileActions.EDIT_CONTENT)) {
			
			rootFolder.editFileContent(name, newContent, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	public void editFileExtension(String newExtension, String name, String path) {
		FileGB fileToEdit = rootFolder.findFileByPathAndName(name, path);
		FilePermission permission = findFilePermission(name, fileToEdit.getPath());
		
		if (permission.isAllowed(FileActions.EDIT_EXTENSION)) {
			rootFolder.editFileExtension(newExtension, name, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	public void editFileName(String newName, String oldName, String path) {
		FileGB fileToEdit = rootFolder.findFileByPathAndName(oldName, path);
		FilePermission permission = findFilePermission(oldName, fileToEdit.getPath());
		
		if (permission.isAllowed(FileActions.EDIT_NAME)) {
			rootFolder.editFileName(newName, oldName, path);
		} else {
			throw new NotEnoughAccessLevel("Your permission: " + permission + " is not enough to complete the operation.");
		}
	}
	
	public void editFolderName(String newName, String oldName, String path) {
		rootFolder.editFolderName(newName, oldName, path);
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
	
	private FilePermission findFilePermission(String name, String path) {
		Set<FileGB> files = filePermissions.keySet();
		
		for (FileGB file : files) {
			if (file.getName().equals(name) && file.getPath().equals(path)) {
				return filePermissions.get(file);
			}
		}
		
		return FilePermission.OWNER;
	}
	
	private void receiveFile(FileGB sharedFile) {
		sharedWithMeFolder().addFile(sharedFile);
	}

	private FolderGB sharedWithMeFolder() {
		return rootFolder.findFolderByName(SHARED_WITH_ME_FOLDER_NAME);
	}
	
	private FolderGB iSharedFolder() {
		return rootFolder.findFolderByName(I_SHARED_FOLDER_NAME);
	}
}
