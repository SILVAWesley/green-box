package org.ufcg.si.beans.requests;

import org.ufcg.si.models.User;

public class AddFileRBean {
	private User user;
	private long memoryUsage;
	
	public AddFileRBean() {
		
	}
	
	public AddFileRBean(User user, long memoryUsage) {
		this.user = user;
		this.memoryUsage = memoryUsage;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public long getMemoryUsage() {
		return memoryUsage;
	}
	
	public void setMemoryUsage(long memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
}
