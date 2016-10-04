package org.ufcg.si.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ufcg.si.models.User;
import org.ufcg.si.models.storage.FolderGB;

public class UserTest {

	private User user0;
	private User user1;
	private User user2;
	private User user3;
	private User user4;

	@Before
	public void setup() throws Exception {
		user0 = new User();
		user1 = new User();
		user2 = new User("someone@domain.com", "someone", "123456");
		user3 = new User("", "", "");
		user4 = new User(" ", " ", " ");
	}

	@Test
	public void testEquals() {
		Assert.assertEquals(user2, new User("someone@domain.com", "someone", "123456"));
		Assert.assertEquals(user3, new User("", "", ""));
		Assert.assertEquals(user4, new User(" ", " ", " "));
	}

	@Test
	public void testGetEmail() {
		Assert.assertEquals(user2.getEmail(), "someone@domain.com");
		Assert.assertEquals(user3.getEmail(), "");
		Assert.assertEquals(user4.getEmail(), " ");

	}

	@Test
	public void testGetUsername() {
		Assert.assertEquals(user2.getUsername(), "someone");
		Assert.assertEquals(user3.getUsername(), "");
		Assert.assertEquals(user4.getUsername(), " ");

	}

	@Test
	public void testGetPassword() {
		Assert.assertEquals(user2.getPassword(), "123456");
		Assert.assertEquals(user3.getPassword(), "");
		Assert.assertEquals(user4.getPassword(), " ");

	}

	@Test
	public void testSetUsername() {
		user1.setUsername("Socrates");
		user2.setUsername("Platao");
		user3.setUsername("Aristoteles");
		user4.setUsername("Homero");
		Assert.assertEquals(user1.getUsername(), "Socrates");
		Assert.assertEquals(user2.getUsername(), "Platao");
		Assert.assertEquals(user3.getUsername(), "Aristoteles");
		Assert.assertEquals(user4.getUsername(), "Homero");
	}

	

}
