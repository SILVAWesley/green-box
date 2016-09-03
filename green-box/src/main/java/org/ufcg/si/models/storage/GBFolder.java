package org.ufcg.si.models.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.jboss.jandex.Main;
import org.ufcg.si.exceptions.InvalidDataException;
import org.ufcg.si.exceptions.MissingItemException;
import org.ufcg.si.util.ServerConstants;

/**
 * This class represents a User's Directory. It is represented as a graph, since
 * It is possible to have a parent directory and children. Every Directory also
 * contains a List of UserFiles.
 */
@Entity
public class GBFolder {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String path;
	
	@ElementCollection
	private List<GBFile> files;
	@OneToMany(cascade = CascadeType.ALL)
	private List<GBFolder> folders;

	public GBFolder(String name, String path) {
		this.name = name;
		this.files = new ArrayList<>();
		this.folders = new ArrayList<>();
		this.path = path;
	}

	public static void main(String[] args) {
		GBFolder p1 = new GBFolder("p1", "p1");
		GBFolder p2 = new GBFolder("p2", "p1/p2");
		GBFolder p3 = new GBFolder("p2", "p1/p3");
		GBFolder p4 = new GBFolder("p4", "p1/p2/p4");
		GBFolder p5 = new GBFolder("p4", "p1/p2/p4/p5");
		
		p1.addFolder("p2", "p1");
		p1.addFolder("p3", "p1");
		p1.addFolder("p4", "p1/p2");
		p1.addFolder("p5", "p1/p2/p4");
		
		p1.rename("a");
		System.out.println(p1);
	}
	
	public GBFolder(String name) {
		this(name, name);
	}
	
	public GBFolder() {
		this(null, null);
	}
	
	public void addFile(String name, String extension, String content) throws IOException {
		GBFile newFile = StorageFactory.createFile(name, extension, content, this.path);
		
		if (files.contains(newFile)) {
			throw new InvalidDataException("Invalid name " + name + " already in use."); 
		}
		
		files.add(newFile);
	}
	
	public void addFile(String name, String extension, String content, String path) throws IOException, InvalidDataException {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		GBFolder folderToAdd = findFolderByName(splPath, 0);
		folderToAdd.addFile(name, extension, content);
	}
	
	public void addFolder(String name) throws InvalidDataException {
		GBFolder newFolder = StorageFactory.createFolder(name, this.path);
		
		if (folders.contains(newFolder)) {
			throw new InvalidDataException("Invalid name " + name + " already in use."); 
		}
		
		folders.add(newFolder);
	}
	
	public void addFolder(String name, String path) throws MissingItemException, InvalidDataException {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		GBFolder folderToAdd = findFolderByName(splPath, 0);
		folderToAdd.addFolder(name);
	}
	
	public void editFile(String name, String newContent, String path) throws IOException {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		GBFolder folder = findFolderByName(splPath, 0);
		folder.findFileByName(name).setContent(newContent);
	}
	
	public void rename(String newName, String name, String path) {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		GBFolder parentOfFolderToRename = findFolderByName(splPath, 0);
		parentOfFolderToRename.findFolderByName(name).rename(newName);
	}
	
	public void rename(String newName) {
		if (path == null) {
			path = "";
		}
		
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		int depthLevel = splPath.length - 1;
		List<String> discovered = new ArrayList<String>();
		renamePath(newName, depthLevel);
		name = newName;
		discovered.add(this.getName());
		recursiveRename(discovered, newName, depthLevel);
	}
	
	private void renamePath(String newName, int depthLevel) {
		String[] splPath = path.split(ServerConstants.PATH_SEPARATOR);
		splPath[depthLevel] = newName;
		path = String.join(ServerConstants.PATH_SEPARATOR, splPath);
	}
	
	private void recursiveRename(List<String> discovered, String newName, int depthLevel) {
		for (GBFolder folder : folders) {
			if (!discovered.contains(folder.getName())) {
				discovered.add(folder.getName());
				folder.renamePath(newName, depthLevel);
				
//				for (GBFile file : files) {
//					String[] fsplPath = path.split(ServerConstants.PATH_SEPARATOR);
//					fsplPath[depthLevel] = newName;
//					file = String.join(ServerConstants.PATH_SEPARATOR, fsplPath);
//				}
				
				folder.recursiveRename(discovered, newName, depthLevel);
			}
		}
	}

	public List<GBFile> getFiles() {
		return files;
	}
	
	public List<GBFolder> getChildren() {
		return folders;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getName() {
		return name;
	}
	
	private GBFolder findFolderByName(String name) throws MissingItemException {
		for (GBFolder folder : this.folders) {
			if (folder.getName().equals(name)) {
				return folder;
			}
		}
		
		throw new MissingItemException("Folder: " + name + " not found in collection: " + this.folders);
	}
	
	private GBFolder findFolderByName(String[] splPath, int currentIndex) throws MissingItemException {
		if (splPath.length - 1 == currentIndex) {
			return this;
		} else {
			return findFolderByName(splPath[currentIndex + 1]).findFolderByName(splPath, currentIndex + 1);
		}
	}
	
	private GBFile findFileByName(String name) throws MissingItemException {
		for (GBFile file : this.files) {
			if (file.getName().equals(name)) {
				return file;
			}
		}
		
		throw new MissingItemException("File: " + name + " not found in collection: " + this.files);
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
		if (obj instanceof GBFolder) {
			GBFolder otherFolder = (GBFolder) obj;
			return this.getName().equals(otherFolder.getName()) && this.getPath().equals(otherFolder.getPath());
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "{" 
				+ "Type: " + this.getClass().getName() + ", "
				+ "Id: " + this.id + ", "
				+ "Name: " + this.name + ", "
				+ "Path: " + this.path + ", "
				+ "Child Folders: " + this.folders + ", "
				+ "Child Files: " + this.files
				+ "}";
	}
}
