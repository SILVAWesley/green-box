package org.ufcg.si.util;

public enum FileExtension {
	TXT, MD;
	
	public static FileExtension valueOfIgnoreCase(String arg0) {
		for (FileExtension extension : FileExtension.class.getEnumConstants()) {
	        if (extension.name().compareToIgnoreCase(arg0) == 0) {
	            return extension;
	        }
	    }
		
		return null;
	}
}
