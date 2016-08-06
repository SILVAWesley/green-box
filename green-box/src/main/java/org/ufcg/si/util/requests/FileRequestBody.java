package org.ufcg.si.util.requests;

import org.ufcg.si.models.User;

public class FileRequestBody {
	private User user;
	
	// Attribute names pointing to the need of a new class.
	private String fileName;
	private String filePath;
	private String fileExtension;
	private String fileContent;
	
	public FileRequestBody(User user, String fileName, String fileExtension, String filePath, String fileContent) {
		this.user = user;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileExtension = fileExtension;
		this.fileContent = fileContent;
	}

	public User getUser() {
		return user;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public String getFileContent() {
		return fileContent;
	}
}
