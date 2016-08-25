package org.ufcg.si.util.requests;

import org.ufcg.si.models.User;

public class NewFolderRequestBody {
	private User user;
	private String newName;
	private String oldName;
	private String newFolderPath;
	
	public NewFolderRequestBody(User user, String newName, String oldName, String newFolderPath){
		this.user = user;
		this.newName = newName;
		this.oldName = oldName;
		this.newFolderPath = newFolderPath;
	}
	
	public NewFolderRequestBody(){
		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewFolderPath() {
		return newFolderPath;
	}

	public void setNewFolderPath(String newFolderPath) {
		this.newFolderPath = newFolderPath;
	}
	
	
	
}
