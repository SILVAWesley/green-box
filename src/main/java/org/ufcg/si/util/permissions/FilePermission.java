package org.ufcg.si.util.permissions;

public enum FilePermission {
	R, RW, OWNER;
	
	public static FilePermission valueOfIgnoreCase(String arg0) {
		for (FilePermission permission : FilePermission.class.getEnumConstants()) {
	        if (permission.name().compareToIgnoreCase(arg0) == 0) {
	            return permission;
	        }
	    }
		
		return null;
	}
}
