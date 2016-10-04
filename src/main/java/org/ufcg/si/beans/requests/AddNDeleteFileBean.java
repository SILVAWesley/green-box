package org.ufcg.si.beans.requests;

import org.ufcg.si.models.User;

public class AddNDeleteFileBean {
	private User user;
	private String fileName;
	private String filePath;
	private String fileExtension;
	private String fileContent;

	public AddNDeleteFileBean(User user, String fileName, String fileExtension, String filePath, String fileContent) {
		this.user = user;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileExtension = fileExtension;
		this.fileContent = fileContent;
	}
	
	public AddNDeleteFileBean() {
		
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileExtension() {
		return fileExtension;
	}
	
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
}
