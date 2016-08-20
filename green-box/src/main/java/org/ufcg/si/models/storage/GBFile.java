package org.ufcg.si.models.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.ufcg.si.util.ServerConstants;

@Embeddable
public class GBFile {
	private String name;
	private String extension;
	@Column(length = 10000000)
	private String content;
	private String path;
	private File file;

	public GBFile(String name, String extension, String content, String path) throws IOException {
		this.name = name;
		this.extension = extension;
		this.path = path;
		
		this.file = new File(ServerConstants.FILES_PATH + name + "." + extension);
		writeContentToFile(content);
		this.content = readContentFromFile();
	}
	
	public GBFile() {
		
	}
	
	private void writeContentToFile(String content) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(content);
		writer.close();
	}

	public String readContentFromFile() throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		StringBuffer content = new StringBuffer();
		
		while(scanner.hasNextLine()) {
			content.append(scanner.nextLine() + ServerConstants.LINE_BREAK);
		}
		
		scanner.close();
		return content.toString();
	}
	
	public String getName() {
		return name;
	}
	
	public String getExtension(){
		return extension;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setContent(String newContent) throws Exception {
		this.content = newContent;
		
		this.file = new File(ServerConstants.FILES_PATH + name + "." + extension);
		writeContentToFile(newContent);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GBFile) {
			GBFile otherFile = (GBFile) obj;
			return this.getName().equals(otherFile.getName()) && this.getPath().equals(otherFile.getPath());
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "{" 
				+ "Type: " + this.getClass().getName() + ", "
				+ "Name: " + this.name + ", "
				+ "Path: " + this.path + ", "
				+ "Content: " + this.content + ", "
				+ "Extension: " + this.extension
				+ "}";
	}
}
