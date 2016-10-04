package org.ufcg.si.util.permissions.file;

/**
 * An enum that has every file permission level and the actions each permission allows
 */
public enum FilePermissions {
	R(), 
	RW(FileActions.EDIT_CONTENT), 
	OWNER(FileActions.SHARE, FileActions.EDIT_CONTENT, FileActions.EDIT_EXTENSION, FileActions.EDIT_NAME),
	REMOVED();
	
	private FileActions[] allowedActions;
	
	private FilePermissions(FileActions... allowedActions) {
		this.allowedActions = allowedActions;
	}
	
	public boolean isAllowed(FileActions action) {
		for (int i = 0; i < allowedActions.length; i++) {
			if (allowedActions[i].equals(action)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static FilePermissions valueOfIgnoreCase(String arg0) {
		
		for (FilePermissions permission : FilePermissions.class.getEnumConstants()) {
	        if (permission.name().compareToIgnoreCase(arg0) == 0) {
	            return permission;
	        }
	    }
		
		return null;
	}
}
