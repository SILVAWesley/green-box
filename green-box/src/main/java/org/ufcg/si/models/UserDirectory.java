package org.ufcg.si.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * This class represents a User's Directory. It is represented as a graph, since
 * It is possible to have a parent directory and children. Every Directory also contains
 * a List of UserFiles.
 */
@Entity
public class UserDirectory {
	@Id
	@GeneratedValue
	private Long id;
	
	@ElementCollection
    private List<UserFile> files;
	
	@ManyToOne
    private UserDirectory parent;
	
	@OneToMany(mappedBy="parent")
    private List<UserDirectory> children;
	
    private String name;
    
    /**
     * This constructor receives the directory's name and it's parent to create
     * a new UserDirectory. It also initializes the list of UserFiles and UserDirectories(Children)
     * @param name
     * 		The directory's name
     * @param parent
     * 		The directory's parent directory
     */
    public UserDirectory(String name, UserDirectory parent){
        this.name = name;
        this.parent = parent;
        this.files = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    // This constructor should be used only when a new User is created
    /**
     * This constructor receives the directory's name to create a new UserDirectory without
     * a parent. It also initializes the list of UserFiles and UserDirectories(Children).
     * This should only be used when a new User is created, since it is not normal to have
     * a directory without a parent.
     * @param name
     * 		The directory's name
     */
    public UserDirectory(String name){
        this(name, null);
    }
    
    /**
     * Creates a new UserFile.
     * @param filename
     * 		The new UserFile's name
     * @param fileExtension
     * 		The new UserFile's extension
     * @param fileContent
     * 		The new UserFile's initial content
     * @throws Exception if the file exists but is a directory rather than a regular file,
     *  does not exist but cannot be created, or cannot be opened for any other reason
     */
    public void createFile(String filename, String fileExtension, StringBuffer fileContent) throws Exception {
        UserFile file = new UserFile(this.name + "/" + filename, fileExtension, fileContent);
        files.add(file);
    }
    
    /**
     * Creates a new UserDirectory, with this one as a Parent. 
     * @param directoryName
     * 		The new Directory's name
     */
    public void createDirectory(String directoryName){
        UserDirectory dir = new UserDirectory(this.name + "/" + directoryName, this);
        this.children.add(dir);
    }



    //GETTERS


    public List<UserFile> getFiles() {
        return files;
    }

    public List<UserDirectory> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public UserDirectory getParent() {
        return parent;
    }
}
