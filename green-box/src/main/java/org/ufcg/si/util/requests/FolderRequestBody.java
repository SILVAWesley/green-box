package org.ufcg.si.util.requests;

import org.ufcg.si.models.User;

public class FolderRequestBody {
	private User user;
	
	private String folderName;
	private String folderPath;
	
	public FolderRequestBody(User user, String folderName, String folderPath) {
		this.user = user;
		this.folderName = folderName;
		this.folderPath = folderPath;
	}
	
	public FolderRequestBody() {
		
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public String getFolderName() {
		return folderName;
	}
	
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
}
