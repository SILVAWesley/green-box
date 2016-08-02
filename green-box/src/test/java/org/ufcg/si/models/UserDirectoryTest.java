package org.ufcg.si.models;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDirectoryTest {
	private UserDirectory dir1;
	private UserDirectory dir2;
	private UserDirectory dir3;
	private UserDirectory dir4;

	@Before
	public void setUp() {
		dir1 = new UserDirectory("parent");
		dir2 = new UserDirectory("son1", dir1);
		dir3 = new UserDirectory("son2", dir1);
		dir4 = new UserDirectory("grandSon1-1", dir2);
	}

	@Test
	public void testEquals() {
		Assert.assertEquals(dir1, new UserDirectory("parent"));
		Assert.assertEquals(dir2, new UserDirectory("son1"));
		Assert.assertEquals(dir3, new UserDirectory("son2"));
		Assert.assertEquals(dir4, new UserDirectory("grandSon1-1"));
		Assert.assertFalse(dir1.equals(dir2));
		Assert.assertFalse(dir1.equals(dir3));
		Assert.assertFalse(dir1.equals(dir4));
		Assert.assertFalse(dir2.equals(dir3));
		Assert.assertFalse(dir2.equals(dir4));
		Assert.assertFalse(dir4.equals(dir3));

	}

	@Test
	public void testGetName() {
		Assert.assertEquals(dir1.getName(), "parent/");
		Assert.assertEquals(dir2.getName(), "son1/");
		Assert.assertEquals(dir3.getName(), "son2/");
		Assert.assertEquals(dir4.getName(), "grandSon1-1/");
	}

	// @Test
	// public void testGetParent(){
	// Assert.assertTrue(dir1.getParent() == null);
	// Assert.assertEquals(dir2.getParent(), dir1);
	// Assert.assertEquals(dir3.getParent(), dir1);
	// Assert.assertEquals(dir4.getParent(), dir2);
	// }

	@Test
	public void testGetChildren() {
		String sb1 = new String("I see fire");
		String sb2 = new String("I see water");
		String sb3 = new String("I see nigth");
		String sb4 = new String("I see fate");
		List<UserFile> list1 = new ArrayList<UserFile>();
		List<UserFile> list2 = new ArrayList<UserFile>();
		List<UserFile> list3 = new ArrayList<UserFile>();
		List<UserFile> list4 = new ArrayList<UserFile>();
		UserFile file1;
		UserFile file2;
		UserFile file3;
		UserFile file4;

		try {
			dir1.createFile("fire", ".txt", sb1);
			dir1.createFile("water", ".txt", sb2);
			dir2.createFile("water", ".txt", sb2);
			dir2.createFile("fate", ".txt", sb4);
			dir3.createFile("nigth", ".txt", sb3);
			dir3.createFile("fate", ".txt", sb4);
			dir4.createFile("fire", ".txt", sb1);
			dir4.createFile("fate", ".txt", sb4);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			file1 = new UserFile("fire", ".txt", sb1);
			file2 = new UserFile("water", ".txt", sb2);
			file3 = new UserFile("nigth", ".txt", sb3);
			file4 = new UserFile("fate", ".txt", sb4);
			list1.add(file1);
			list1.add(file2);
			list2.add(file2);
			list2.add(file4);
			list3.add(file3);
			list3.add(file4);
			list4.add(file1);
			list4.add(file4);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertEquals(dir1.getFiles(), list1);
		Assert.assertEquals(dir2.getFiles(), list2);
		Assert.assertEquals(dir3.getFiles(), list3);
		Assert.assertEquals(dir4.getFiles(), list4);
	}

	@Test
	public void testcreateDirectory() {


		try {
			dir1.createDirectory("games");
			dir1.createDirectory("music");
			dir2.createDirectory("games");
			dir2.createDirectory("books");
			dir3.createDirectory("music");
			dir3.createDirectory("books");
			dir4.createDirectory("comics");
			dir4.createDirectory("papers");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(dir1.getChildren().get(0).getName(), "parent/games/");
		Assert.assertEquals(dir1.getChildren().get(1).getName(), "parent/music/");
		Assert.assertEquals(dir2.getChildren().get(0).getName(), "son1/games/");
		Assert.assertEquals(dir2.getChildren().get(1).getName(), "son1/books/");
		Assert.assertEquals(dir3.getChildren().get(0).getName(), "son2/music/");
		Assert.assertEquals(dir3.getChildren().get(1).getName(), "son2/books/");
		Assert.assertEquals(dir4.getChildren().get(0).getName(), "grandSon1-1/comics/");
		Assert.assertEquals(dir4.getChildren().get(1).getName(), "grandSon1-1/papers/");

	}
}
