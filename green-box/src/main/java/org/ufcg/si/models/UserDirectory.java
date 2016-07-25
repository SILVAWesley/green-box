package org.ufcg.si.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by Iaron-PC on 19/07/2016.
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
    
    
    public UserDirectory(String name, UserDirectory parent){
        this.name = name;
        this.parent = parent;
        this.files = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    // This constructor should be used only when a new User is created
    public UserDirectory(String name){
        this(name, null);
    }
    
    public void addFile(String filename, String fileExtension, StringBuffer fileContent) throws Exception{
        UserFile file = new UserFile(this.name + "/" + filename, fileExtension, fileContent);
        files.add(file);
    }

    public void addDirectory(String directoryName){
        UserDirectory dir = new UserDirectory(this.name + "/" + directoryName/*, this*/);
        this.children.add(dir);
    }



    //Getters


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
