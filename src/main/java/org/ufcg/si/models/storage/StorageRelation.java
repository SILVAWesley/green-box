package org.ufcg.si.models.storage;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

import org.ufcg.si.util.permissions.file.FilePermissions;

@Embeddable
public class StorageRelation <T> {
	private FilePermissions permission;
	@OneToOne(cascade = CascadeType.ALL)
	private T item;
	
	public StorageRelation(FilePermissions permission, T item) {
		this.permission = permission;
		this.item = item;
	}
	
	public StorageRelation() {
		this(null, null);
	}
	
	public T getItem() {
		return item;
	}
	
	public void setItem(T newItem) {
		this.item = newItem;
	}
	
	public FilePermissions getPermission() {
		return permission;
	}
	
	public void setPermission(FilePermissions newPermission) {
		this.permission = newPermission;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FolderGB) {
			StorageRelation<?> otherRelation = (StorageRelation<?>) obj;
			
			return this.getItem().equals(otherRelation.getItem())
				&& this.getPermission().equals(otherRelation.getPermission());
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "{"  
			 + "Permission: " + permission
			 + ", Item: " + item 
			 + "}";
	}
}
