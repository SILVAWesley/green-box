package org.ufcg.si.util;

/**
 * This enum represents all possible file extensions in the GreenBox application
 */
public enum FileExtensions {
	TXT, MD;
	
	public static FileExtensions valueOfIgnoreCase(String arg0) {
		for (FileExtensions extension : FileExtensions.class.getEnumConstants()) {
	        if (extension.name().compareToIgnoreCase(arg0) == 0) {
	            return extension;
	        }
	    }
		
		return null;
	}
}
