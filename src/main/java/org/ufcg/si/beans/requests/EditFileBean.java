package org.ufcg.si.beans.requests;

import org.ufcg.si.models.User;
import org.ufcg.si.models.storage.FileGB;

public class EditFileBean {
	private User user;
	private FileGB clickedFile;
	
	private String fileName;
	private String fileExtension;
	private String fileContent;
	private String filePath;
	
	public EditFileBean(User user, String fileName, String fileExtension, String fileContent, String filePath, FileGB clickedFile){
		this.user = user;
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.filePath = filePath;
		this.clickedFile = clickedFile;
		this.fileExtension = fileExtension;
		
		if (this.fileExtension == null || this.fileExtension.equals("")) {
			this.fileExtension = "txt";
		}
	}
	
	public EditFileBean(){
		
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

	public void setFileName(String newFileName) {
		this.fileName = newFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String newFilePath) {
		this.filePath = newFilePath;
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

	public FileGB getClickedFile() {
		return clickedFile;
	}

	public void setClickedFile(FileGB clickedFile) {
		this.clickedFile = clickedFile;
	}
}
