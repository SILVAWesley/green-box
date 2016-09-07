package org.ufcg.si.models.storage;

import java.io.IOException;

import org.ufcg.si.util.ServerConstants;

public class StorageFactory {
	public static FolderGB createFolder(String name, String contextPath) {
		return new FolderGB(name, contextPath + ServerConstants.PATH_SEPARATOR + name); 
	}
	
	public static FileGB createFile(String name, String extension, String content, String contextPath) throws IOException {
		return new FileGB(name, extension, content, contextPath + ServerConstants.PATH_SEPARATOR + name); 
	}
	
	public static FolderManager createDirectory(String name) {
		return new FolderManager(name);
	}
	
	public static FolderGB createRootFolder(String username) {
		FolderGB folder = new FolderGB("root");
		folder.addFolder(username);
		folder.addFolder("Shared with me");
		return folder;
	}
}
