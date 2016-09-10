package org.ufcg.si.util.permissions.folder;

public enum FolderPermissions {
	OWNER(FolderActions.ADD_FILE,
		  FolderActions.ADD_FOLDER,
		  FolderActions.EDIT_FOLDER_NAME),
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
