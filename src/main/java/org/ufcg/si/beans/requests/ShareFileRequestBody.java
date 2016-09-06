package org.ufcg.si.beans.requests;

import org.ufcg.si.models.User;

public class ShareFileRequestBody {
	private User user;
	private String name;
	private String folderPath;
	private User userSharedWith;
	private String permissionLevel;
	
	public ShareFileRequestBody(User user, User userSharedWith, String name, String folderPath, String permissionLevel) {
		this.user = user;
		this.name = name;
		this.folderPath = folderPath;
		this.userSharedWith = userSharedWith;
		this.permissionLevel = permissionLevel;
	}
	
	public ShareFileRequestBody(){
		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getPermissionLevel() {
		return permissionLevel;
	}
	
	public void setPermissionLevel(String newPermissionLevel) {
		this.permissionLevel = newPermissionLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String newFolderPath) {
		this.folderPath = newFolderPath;
	}
	
	public User getUserSharedWith() {
		return userSharedWith;
	}

	public void setUserSharedWith(User newUserSharedWith) {
		this.userSharedWith = newUserSharedWith;
	}
}
