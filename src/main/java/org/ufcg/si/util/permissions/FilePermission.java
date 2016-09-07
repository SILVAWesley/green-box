package org.ufcg.si.util.permissions;

import java.util.Arrays;

public enum FilePermission {
	R(), 
	RW(FileActions.EDIT_CONTENT), 
	OWNER(FileActions.SHARE, FileActions.EDIT_CONTENT, FileActions.EDIT_EXTENSION, FileActions.EDIT_NAME);
	
	private FileActions[] allowedActions;
	
	private FilePermission(FileActions... allowedActions) {
		this.allowedActions = allowedActions;
	}
	
	public boolean isAllowed(FileActions action) {
		System.out.println(Arrays.toString(allowedActions));
		for (int i = 0; i < allowedActions.length; i++) {
			if (allowedActions[i].equals(action)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static FilePermission valueOfIgnoreCase(String arg0) {
		for (FilePermission permission : FilePermission.class.getEnumConstants()) {
	        if (permission.name().compareToIgnoreCase(arg0) == 0) {
	            return permission;
	        }
	    }
		
		return null;
	}
}
