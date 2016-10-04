package org.ufcg.si.util.permissions.folder;

/**
 * An enum that has every folder permission level and the actions each permission allows
 */
public enum FolderPermissions {
	OWNER(FolderActions.ADD_FILE,
		  FolderActions.ADD_FOLDER,
		  FolderActions.EDIT_FOLDER_NAME,
		  FolderActions.DELETE_FILE,
		  FolderActions.DELETE_FOLDER,
		  FolderActions.CLEAN_TRASH),
	REMOVED(),
	SHARED();
	
	private FolderActions[] allowedActions;
	
	private FolderPermissions(FolderActions... allowedActions) {
		this.allowedActions = allowedActions;
	}
	
	public boolean isAllowed(FolderActions action) {
		for (int i = 0; i < allowedActions.length; i++) {
			if (allowedActions[i].equals(action)) {
				return true;
			}
		}
		
		return false;
	}
}
