package org.ufcg.si.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Notification {
	@Id
	@GeneratedValue
	private Long id;
	
	private String content;
	private boolean isVisited;
	
	public Notification(String content) {
		this.content = content;
	}
	
	public Notification() {
		
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean getIsVisited() {
		return isVisited;
	}
	
	public void setIsVisited(boolean visited) {
		this.isVisited = visited;
	}
}
