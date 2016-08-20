package org.ufcg.si.models.storage;

import org.ufcg.si.util.ServerConstants;

public class StorageFactory {
	public static GBFolder createFolder(String name, String contextPath) {
		return new GBFolder(name, contextPath + ServerConstants.PATH_SEPARATOR + name); 
	}
	
	public static GBFile createFile(String name, String extension, String content, String contextPath) throws Exception {
		return new GBFile(name, extension, content, contextPath + ServerConstants.PATH_SEPARATOR + name); 
	}
}
