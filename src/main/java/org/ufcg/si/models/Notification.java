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
}
