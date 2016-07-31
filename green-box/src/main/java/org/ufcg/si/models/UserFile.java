package org.ufcg.si.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.persistence.Embeddable;

/**
 * Represents a File in the Green-Box program
 */
@Embeddable
public class UserFile {
	private File internFile;
	private String name;

	/**
	 * This constructor creates a new UserFile and writes an initial content in
	 * it.
	 * 
	 * @param name
	 *            The File's name
	 * @param extension
	 *            The File's extension
	 * @param content
	 *            The File's initial content
	 * @throws Exception
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 */
	public UserFile(String name, String extension, StringBuffer content) throws Exception {
		this.name = name;
		this.internFile = new File(name + extension);
		writeInFile(content);
	}

	// This method receives a StringBuffer with the content to be written in the
	// file
	private void writeInFile(StringBuffer fileContent) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(internFile)); // This
																				// throws
																				// an
																				// exception.
		writer.write(fileContent.toString());
		writer.close();
	}

	// GETTERS

	public String getName() {
		return name;
	}

	// We should check later if this get should exist
	public File getInternFile() {
		return internFile;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserFile) {
			UserFile temp = (UserFile) obj;
			return this.getInternFile().equals(temp.getInternFile()) && this.getName().equals(temp.getName());

		} else {
			return false;
		}
	}

}
