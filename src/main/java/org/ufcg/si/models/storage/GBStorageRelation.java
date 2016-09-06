package org.ufcg.si.models.storage;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

import org.ufcg.si.util.permissions.FilePermission;

@Embeddable
public class GBStorageRelation <T> {

	private FilePermission permission;
	@OneToOne(cascade = CascadeType.ALL)
	private T item;
	
	public GBStorageRelation(FilePermission permission, T item) {
		this.permission = permission;
		this.item = item;
	}
	
	public GBStorageRelation() {
		
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
	public String toString() {
		return "{"  
			 + "Permission: " + permission
			 + ", Item: " + item 
			 + "}";
	}
}
