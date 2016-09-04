package org.ufcg.si.util.requests;

import org.ufcg.si.models.User;

public class ShareFileRequestBody {
	private User user;
	private String name;
	private String folderPath;
	private User userSharedWith;
	
	public ShareFileRequestBody(User user, User userSharedWith, String name, String folderPath){
		this.user = user;
		this.name = name;
		this.folderPath = folderPath;
		this.userSharedWith = userSharedWith;
	}
	
	public ShareFileRequestBody(){
		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
