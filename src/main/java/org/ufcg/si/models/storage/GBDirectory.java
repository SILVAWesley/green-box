package org.ufcg.si.models.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.ufcg.si.exceptions.NotEnoughAccessLevel;
import org.ufcg.si.util.permissions.FilePermission;
@Entity
public class GBDirectory {
	
	public static final String SHARED_WITH_ME_FOLDER_NAME = "Shared with me";
	public static final String I_SHARED_FOLDER_NAME = "Files you shared";
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	private GBFolder rootFolder; 
	
	@ElementCollection
	private List<GBStorageRelation<GBFile>> sharedFilesPermissions;
	
	public GBDirectory(String name) {
		this.name = name;
		this.rootFolder = new GBFolder(name, "");
		this.rootFolder.addFolder(name);
		this.rootFolder.addFolder(SHARED_WITH_ME_FOLDER_NAME);
		this.rootFolder.addFolder(I_SHARED_FOLDER_NAME);
		this.sharedFilesPermissions = new ArrayList<>();
	}
	
	public GBDirectory() {
		
	}
	
	public void addFile(String name, String extension, String content, String path) throws IOException {
		rootFolder.addFile(name, extension, content, path);
	}

	public void addFolder(String name, String path) {
		rootFolder.addFolder(name, path);
	}
	
	public void removeFile() {
		
	}
	
	public void removeFolder() {
		
	}
	
	public void shareFile(GBDirectory directoryToShareWith, String name, String path, FilePermission permission) {
		GBFile sharingFile = rootFolder.findFileByPathAndName(name, path);
		iSharedFolder().addFile(sharingFile);
		directoryToShareWith.receiveFile(sharingFile);
		directoryToShareWith.sharedFilesPermissions.add(new GBStorageRelation<GBFile>(permission, sharingFile));
		System.out.println(directoryToShareWith.sharedFilesPermissions);
	}
	
	public void editFileContent(String name, String newContent, String path) throws IOException {
		FilePermission permission = findRelationPermission(name, path);
		System.out.println("RODOU ESSE MÉTODO: " + permission);
		if (permission != null) {
			if (permission == FilePermission.R) {
				throw new NotEnoughAccessLevel("You are granted permission to Read-only."); 
			}
		}
		
		rootFolder.editFileContent(name, newContent, path);
	}
	
	public void editFileExtension(String newExtension, String name, String path) { 
		rootFolder.editFileExtension(newExtension, name, path);
	}
	
	public void editFileName(String newName, String oldName, String path) { 
		rootFolder.editFileName(newName, oldName, path);
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
	
	public GBFolder getRootFolder() {
		return rootFolder;
	}
	
	private FilePermission findRelationPermission(String name, String path) {
		for (GBStorageRelation<GBFile> relation : sharedFilesPermissions) {
			System.out.println(relation.getItem().getName() + "     " + relation.getItem().getPath());
			System.out.println(name + "\t\t" + path);
			if (relation.getItem().getName().equals(name)) {
				return relation.getPermission();
			}
		}
		
		return null;
	}
	
	private void receiveFile(GBFile sharedFile) {
		sharedWithMeFolder().addFile(sharedFile);
	}

	private GBFolder sharedWithMeFolder() {
		return rootFolder.findFolderByName(SHARED_WITH_ME_FOLDER_NAME);
	}
	
	private GBFolder iSharedFolder() {
		return rootFolder.findFolderByName(I_SHARED_FOLDER_NAME);
	}
}
