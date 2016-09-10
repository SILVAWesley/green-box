package org.ufcg.si.models.storage;

import org.ufcg.si.util.ServerConstants;

public class StorageUtilities {
	public static String[] splitPath(String path) {
		return path.split(ServerConstants.PATH_SEPARATOR);
	}
	
	public static String joinPath(String[] path) {
		return String.join(ServerConstants.PATH_SEPARATOR, path);
	}
}
