package org.ufcg.si.models.storage;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

import org.ufcg.si.util.permissions.FilePermission;

@Embeddable
public class StorageRelation <T> {

	private FilePermission permission;
	@OneToOne(cascade = CascadeType.ALL)
	private T item;
	
	public StorageRelation(FilePermission permission, T item) {
		this.permission = permission;
		this.item = item;
	}
	
	public StorageRelation() {
		
	}
	
	public T getItem() {
		return item;
	}
	
	public void setItem(T newItem) {
		this.item = newItem;
	}
	
	public FilePermission getPermission() {
		return permission;
	}
	
	public void setPermission(FilePermission newPermission) {
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageRelation other = (StorageRelation) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (permission != other.permission)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{"  
			 + "Permission: " + permission
			 + ", Item: " + item 
			 + "}";
	}
}
