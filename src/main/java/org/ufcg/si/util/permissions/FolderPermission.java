package org.ufcg.si.util.permissions;

public enum FolderPermission {
	OWNER(FolderActions.ADD_FILE,
		  FolderActions.ADD_FOLDER,
		  FolderActions.EDIT_NAME),
	SHARED();
	
	private FolderActions[] allowedActions;
	
	private FolderPermission(FolderActions... allowedActions) {
		this.allowedActions = allowedActions;
	}
}
