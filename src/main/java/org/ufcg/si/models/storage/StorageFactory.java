package org.ufcg.si.models.storage;

import org.ufcg.si.util.ServerConstants;

/**
 * Factory responsible for the creation of FolderGBs, FileGBs and UserActionsManager
 */
public class StorageFactory {
	
	/**
	 * Creates a new folder, adjusting it's path
	 * @param name
	 * 		The folder name
	 * @param contextPath
	 * 		The path of the folder in which the folder will be created
	 * @return the created folder
	 */
	public static FolderGB createFolder(String name, String contextPath) {
		return new FolderGB(name, contextPath + ServerConstants.PATH_SEPARATOR + name); 
	}
	
	/**
	 * Creates a new folder, adjusting it's file
	 * @param name
	 * 		The file name
	 * @param extension
	 * 		The file extension
	 * @param content
	 * 		The file content
	 * @param contextPath
	 * 		The path of the folder in which the file will be created
	 * @return the created file
	 */
	public static FileGB createFile(String name, String extension, String content, String contextPath) {
		return new FileGB(name, extension, content, contextPath + ServerConstants.PATH_SEPARATOR + name); 
	}
	
	/**
	 * Create a new UserActionsManager
	 * @param name
	 * 		The UserActionsManager name
	 * @return the manager created
	 */
	public static UserActionsManager createFolderManager(String name) {
		return new UserActionsManager(name);
	}
	
	/**
	 * Create a new folder to be the user root folder
	 * @param username
	 * 		The user's username
	 * @return the folder created
	 */
	public static FolderGB createRootFolder(String username) {
		FolderGB folder = new FolderGB("root");
		folder.addFolder(username);
		folder.addFolder("Shared with me");
		return folder;
	}
}
