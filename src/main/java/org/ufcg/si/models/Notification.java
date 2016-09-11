package org.ufcg.si.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represents a notification the the user receives when someone shares a
 * file with them.
 */
@Entity
public class Notification {
	@Id
	@GeneratedValue
	private Long id;
	
	private String content;
	private boolean isVisited;
	
	/**
	 * The constructor that creates a notification with a certain content
	 * @param content
	 * 		The notification's content
	 */
	public Notification(String content) {
		this.content = content;
		this.isVisited = false;
	}
	
	/**
	 * Default constructor
	 */
	public Notification() {
		this("");
	}
	
	/**
	 * @return The notification's content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Updates the notification's content
	 * @param content
	 * 		The new notification's content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * The isVisited status represents if the notification was
	 * already seen.
	 * @return the notification status regarding if it was seen.
	 */
	public boolean getIsVisited() {
		return isVisited;
	}
	
	/**
	 * Updates the isVisited status that represents if the notification
	 * was already seen.
	 * @param visited
	 * 		The new notification status regarding if it was seen.
	 */
	public void setIsVisited(boolean visited) {
		this.isVisited = visited;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Notification) {
			Notification temp = (Notification) obj;
			return this.getContent().equals(temp.getContent());
		} else {
			return false;
		}

	}
	
	@Override
	public String toString() {
		return "{" 
				+ "Type: " + this.getClass().getName() + ", "
				+ "Id: " + this.id + ", "
				+ "Content: " + this.content + ", "
				+ "}";
	}
}
