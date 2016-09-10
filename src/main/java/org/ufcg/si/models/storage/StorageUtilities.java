package org.ufcg.si.models.storage;

import org.ufcg.si.util.ServerConstants;

/**
 * Class to do operations on paths
 */
public class StorageUtilities {
	/**
	 * Split a path
	 * @param path
	 * 		The path to be splited
	 * @return an array with the separated components of a path
	 */
	public static String[] splitPath(String path) {
		return path.split(ServerConstants.PATH_SEPARATOR);
	}
	
	/**
	 * Join an array of Strings into a path
	 * @param path
	 * 		The array to be joined
	 * @return the array as a path
	 */
	public static String joinPath(String[] path) {
		return String.join(ServerConstants.PATH_SEPARATOR, path);
	}
}
