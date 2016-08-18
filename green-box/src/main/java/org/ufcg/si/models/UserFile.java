package org.ufcg.si.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import javax.persistence.Embeddable;

import org.ufcg.si.util.ServerConstants;

/**
 * Represents a File in the Green-Box program
 */
@Embeddable
public class UserFile {
	private File internFile;
	private String name;
	private String content;
	private String extension;

	/**
	 * This constructor creates a new UserFile and writes an initial content in
	 * it.
	 * 
	 * @param name
	 *            The File's name
	 * @param extension
	 *            The File's extension
	 * @throws Exception
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 */
	public UserFile(String name, String extension, String content) throws Exception {
		this.name = name;
		this.extension = extension;
		this.internFile = new File("files/" + name + "." + extension);
		writeInFile(content);
		this.content = readFileContent();
	}
	
	/**
	 * Default constructor. Should only be used by the Controller
	 */
	public UserFile() {
		
	}
	
	private void writeInFile(String fileContent) throws Exception {
		//This throws an exception
	 	BufferedWriter writer = new BufferedWriter(new FileWriter(internFile)); 
	 	writer.write(fileContent.toString());
	 	writer.close();
	 }
	
	/**
	 * Reads what's inside the file
	 * @return the file's content
	 * @throws FileNotFoundException if the internFile doesnt exist
	 */
	public String readFileContent() throws FileNotFoundException{
		//This throws an exception
		Scanner scanner = new Scanner(internFile);
		StringBuffer content = new StringBuffer();
		while(scanner.hasNextLine()){
			content.append(scanner.nextLine() + ServerConstants.LINE_BREAK);
		}
		
		scanner.close();
		return content.toString();
	}
	
	// GETTERS
	
	/**
	 * The File's name getter
	 * @return The file's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * The extension getter. The extension represents the internFile format
	 * @return The internFile's format
	 */
	public String getExtension(){
		return extension;
	}
	
	/**
	 * The file's content getter
	 * @return The internFile's content
	 */
	public String getContent() {
		return content;
	}
	
	public void setContent(String newContent) throws Exception {
		this.content = newContent;
		this.internFile = new File("files/" + name + "." + extension);
		writeInFile(newContent);
	}

	/**
	 * Two UserFiles are equals if they have the same name.
	 * @param obj the object to be compared
	 * @return if the obj is equals to this object
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserFile) {
			UserFile temp = (UserFile) obj;
			return this.name.equals(temp.getName());
		} else {
			return false;
		}
	}
}
