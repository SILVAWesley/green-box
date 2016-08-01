package org.ufcg.si.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * This class represents a User's Directory. It is represented as a graph, since
 * It is possible to have a parent directory and children. Every Directory also
 * contains a List of UserFiles.
 */
@Entity
public class UserDirectory {
	@Id
	@GeneratedValue
	private Long id;

	@ElementCollection
	private List<UserFile> files;

	//@ManyToOne
	//private UserDirectory parent;

	@OneToMany(/*mappedBy = "parent",*/ cascade = CascadeType.ALL)
	private List<UserDirectory> children;

	private String name;

	/**
	 * This constructor receives the directory's name and it's parent to create
	 * a new UserDirectory. It also initializes the list of UserFiles and
	 * UserDirectories(Children)
	 * 
	 * @param name
	 *            The directory's name
	 * @param parent
	 *            The directory's parent directory
	 */
	public UserDirectory(String name, UserDirectory parent) {
		this.name = name;
		//this.parent = parent;
		this.files = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	public UserDirectory() {
		this.files = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	// This constructor should be used only when a new User is created
	/**
	 * This constructor receives the directory's name to create a new
	 * UserDirectory without a parent. It also initializes the list of UserFiles
	 * and UserDirectories(Children). This should only be used when a new User
	 * is created, since it is not normal to have a directory without a parent.
	 * 
	 * @param name
	 *            The directory's name
	 */
	public UserDirectory(String name) {
		this(name, null);
	}

	/**
	 * Creates a new UserFile.
	 * 
	 * @param filename
	 *            The new UserFile's name
	 * @param fileExtension
	 *            The new UserFile's extension
	 * @param fileContent
	 *            The new UserFile's initial content
	 * @throws Exception
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 */
	public void createFile(String filename, String fileExtension, StringBuffer fileContent) throws Exception {

		UserFile file = new UserFile(filename, fileExtension, fileContent);

		for (int i = 0; i < this.getFiles().size(); i++) {
			if (this.getFiles().get(i).equals(file)) {
				throw new Exception();
			}
		}

		files.add(file);
	}

	/**
	 * Creates a new UserDirectory, with this one as a Parent.
	 * 
	 * @param directoryName
	 *            The new Directory's name
	 * @throws Exception
	 */
	public void createDirectory(String directoryName) throws Exception {
		UserDirectory dir = new UserDirectory(directoryName, this);

		for (int i = 0; i < this.getFiles().size(); i++) {
			if (this.getChildren().get(i).equals(dir)) {
				throw new Exception("File already in folder.");
			}
		}
		this.children.add(dir);
	}
	
	//public void removeDirectory(String directoryName) throws {
		
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDirectory) {
			UserDirectory temp = (UserDirectory) obj;
			return this.getName().equals(temp.getName());
		} else {
			return false;
		}
	}

	// private boolean recursiveEquals(UserDirectory first, UserDirectory
	// second) {
	// if (this.oneDirectoryEquals(first, second) == false) {
	// return false;
	// } else {
	// if (first.getChildren().size() != second.getChildren().size()) {
	// return false;
	// }
	//
	// for(int i =0; i < first.getChildren().size(); i++){
	// if(this.oneDirectoryEquals(first.getChildren().get(i),
	// second.getChildren().get(i)) == false){
	// return false;
	// }
	// }
	// return true;
	// }
	// }
	//
	// private boolean oneDirectoryEquals(UserDirectory first, UserDirectory
	// second) {
	// if (first == null && second != null || first == null && second != null) {
	// return false;
	// } else if (first == null && second == null) {
	// return true;
	// } else {
	// if (first.getFiles().size() != second.getFiles().size()) {
	// return false;
	// }
	// boolean isEqual = true;
	// for (int i = 0; i < first.getFiles().size(); i++) {
	// if (first.getFiles().get(i).equals(second.getFiles().get(i)) == false) {
	// isEqual = false;
	// break;
	// }
	//
	// }
	// return first.getName().equals(second.getName()) && isEqual;
	// }
	// }

	// GETTERS

	public List<UserFile> getFiles() {
		return files;
	}

	public List<UserDirectory> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	/*public UserDirectory getParent() {
		return parent;
	}*/
}
