package org.ufcg.si.util.requests;

import org.ufcg.si.models.User;

public class RenameFileRequestBody {
	private User user;
	private String newName;
	private String oldName;
	private String folderPath;
	
	public RenameFileRequestBody(User user, String newName, String oldName, String folderPath){
		this.user = user;
		this.newName = newName;
		this.oldName = oldName;
		this.folderPath = folderPath;
	}
	
	public RenameFileRequestBody(){
		
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

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String newFolderPath) {
		this.folderPath = newFolderPath;
	}
	
	
	
}
