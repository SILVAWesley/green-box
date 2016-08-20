package org.ufcg.si.models;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ufcg.si.models.storage.GBFile;
import org.ufcg.si.models.storage.GBFolder;

public class GBFolderTest {
	private GBFolder dir1;
	private GBFolder dir2;
	private GBFolder dir3;
	private GBFolder dir4;
	String sb1;
	String sb2;
	String sb3;
	String sb4;

	@Before
	public void setUp() {
		dir1 = new GBFolder("parent");
		dir2 = new GBFolder("son1");
		dir3 = new GBFolder("son2");
		dir4 = new GBFolder("grandSon1-1");
		sb1 = new String("I see fire");
		sb2 = new String("I see water");
		sb3 = new String("I see nigth");
		sb4 = new String("I see fate");
	}

	@Test
	public void testEquals() {
		Assert.assertEquals(dir1, new GBFolder("parent"));
		Assert.assertEquals(dir2, new GBFolder("son1"));
		Assert.assertEquals(dir3, new GBFolder("son2"));
		Assert.assertEquals(dir4, new GBFolder("grandSon1-1"));
		Assert.assertFalse(dir1.equals(dir2));
		Assert.assertFalse(dir1.equals(dir3));
		Assert.assertFalse(dir1.equals(dir4));
		Assert.assertFalse(dir2.equals(dir3));
		Assert.assertFalse(dir2.equals(dir4));
		Assert.assertFalse(dir4.equals(dir3));

	}

	@Test
	public void testGetName() {
		Assert.assertEquals(dir1.getName(), "parent");
		Assert.assertEquals(dir2.getName(), "son1");
		Assert.assertEquals(dir3.getName(), "son2");
		Assert.assertEquals(dir4.getName(), "grandSon1-1");
	}

	@Test
	public void testGetChildDirectory() {
		try {
			dir1.createDirectory("pepe");
			dir1.createDirectory("blobo");
			dir1.createDirectory("bojack");
			dir2.createDirectory("something");
			dir2.createDirectory("something else");
			Assert.assertEquals(dir1.findFolderByName("pepe"), new GBFolder("pepe"));
			Assert.assertEquals(dir1.findFolderByName("blobo"), new GBFolder("blobo"));
			Assert.assertEquals(dir1.findFolderByName("bojack"), new GBFolder("bojack"));
			Assert.assertEquals(dir2.findFolderByName("something"), new GBFolder("something"));
			Assert.assertEquals(dir2.findFolderByName("something else"), new GBFolder("something else"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(expected = Exception.class)
	public void testGetChildwithError() throws Exception {
		dir1.findFolderByName("something");
		dir2.findFolderByName("something");
		dir3.findFolderByName("something");
		dir4.findFolderByName("something");
	}

	@Test
	public void testGetFiles() {
		String sb1 = new String("I see fire");
		String sb2 = new String("I see water");
		String sb3 = new String("I see nigth");
		String sb4 = new String("I see fate");
		List<GBFile> list1 = new ArrayList<GBFile>();
		List<GBFile> list2 = new ArrayList<GBFile>();
		List<GBFile> list3 = new ArrayList<GBFile>();
		List<GBFile> list4 = new ArrayList<GBFile>();
		GBFile file1;
		GBFile file2;
		GBFile file3;
		GBFile file4;

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
			file1 = new GBFile("fire", ".txt", sb1);
			file2 = new GBFile("water", ".txt", sb2);
			file3 = new GBFile("nigth", ".txt", sb3);
			file4 = new GBFile("fate", ".txt", sb4);
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

		Assert.assertEquals(dir1.getChildren().get(0), new GBFolder("games"));
		Assert.assertEquals(dir1.getChildren().get(1), new GBFolder("music"));
		Assert.assertEquals(dir2.getChildren().get(0), new GBFolder("games"));
		Assert.assertEquals(dir2.getChildren().get(1), new GBFolder("books"));
		Assert.assertEquals(dir3.getChildren().get(0), new GBFolder("music"));
		Assert.assertEquals(dir3.getChildren().get(1), new GBFolder("books"));
		Assert.assertEquals(dir4.getChildren().get(0), new GBFolder("comics"));
		Assert.assertEquals(dir4.getChildren().get(1), new GBFolder("papers"));

	}

	@Test
	public void testCreatDirectorywithPath() {
		try {
			dir1.createDirectory("heaven");
			dir1.createDirectory("sky", "heaven");
			dir1.createDirectory("earth", "heaven-sky");
			dir1.createDirectory("midgard", "heaven-sky-earth");
			dir1.createDirectory("purgatory", "heaven-sky-earth-midgard");
			dir1.createDirectory("hell", "heaven-sky-earth-midgard-purgatory");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			GBFolder heaven = dir1.findFolderByName("heaven");
			GBFolder sky = heaven.findFolderByName("sky");
			GBFolder earth = sky.findFolderByName("earth");
			GBFolder midgard = earth.findFolderByName("midgard");
			GBFolder purgatory = midgard.findFolderByName("purgatory");
			GBFolder hell = purgatory.findFolderByName("hell");

			Assert.assertEquals(heaven, new GBFolder("heaven"));
			Assert.assertEquals(sky, new GBFolder("sky"));
			Assert.assertEquals(earth, new GBFolder("earth"));
			Assert.assertEquals(midgard, new GBFolder("midgard"));
			Assert.assertEquals(purgatory, new GBFolder("purgatory"));
			Assert.assertEquals(hell, new GBFolder("hell"));
			Assert.assertEquals(heaven, new GBFolder("heaven"));
			Assert.assertNotEquals(earth, new GBFolder("sky"));
			Assert.assertNotEquals(hell, new GBFolder("earth"));
			Assert.assertNotEquals(heaven, new GBFolder("midgard"));
			Assert.assertNotEquals(midgard, new GBFolder("purgatory"));
			Assert.assertNotEquals(sky, new GBFolder("hell"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateFile() {

		try {
			dir1.createFile("fire", "txt", sb1);
			dir1.createFile("water", "txt", sb2);
			dir2.createFile("nigth", "txt", sb3);
			dir2.createFile("fate", "txt", sb4);
			dir3.createFile("nigth", "txt", sb3);
			dir3.createFile("fire", "txt", sb1);
			dir3.createFile("fate", "txt", sb4);
			dir3.createFile("water", "txt", sb2);
			dir4.createFile("nigth", "txt", sb3);
			dir4.createFile("fire", "txt", sb1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Assert.assertEquals(dir1.getFiles().get(0), new GBFile("fire", "txt", sb1));
			Assert.assertEquals(dir1.getFiles().get(1), new GBFile("water", "txt", sb2));
			Assert.assertEquals(dir2.getFiles().get(0), new GBFile("nigth", "txt", sb3));
			Assert.assertEquals(dir2.getFiles().get(1), new GBFile("fate", "txt", sb4));
			Assert.assertEquals(dir3.getFiles().get(0), new GBFile("nigth", "txt", sb3));
			Assert.assertEquals(dir3.getFiles().get(1), new GBFile("fire", "txt", sb1));
			Assert.assertEquals(dir3.getFiles().get(2), new GBFile("fate", "txt", sb4));
			Assert.assertEquals(dir3.getFiles().get(3), new GBFile("water", "txt", sb2));
			Assert.assertEquals(dir4.getFiles().get(0), new GBFile("nigth", "txt", sb3));
			Assert.assertEquals(dir4.getFiles().get(1), new GBFile("fire", "txt", sb1));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testCreatFileWithPath() {
		try {
			dir1.createDirectory("heaven");
			dir1.createDirectory("sky", "heaven");
			dir1.createDirectory("earth", "heaven-sky");
			dir1.createDirectory("midgard", "heaven-sky-earth");
			dir1.createDirectory("purgatory", "heaven-sky-earth-midgard");
			dir1.createDirectory("hell", "heaven-sky-earth-midgard-purgatory");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dir1.createFile("fire", "txt", sb1, "heaven");
			dir1.createFile("water", "txt", sb2, "heaven-sky");
			dir1.createFile("nigth", "txt", sb3, "heaven-sky-earth");
			dir1.createFile("fate", "txt", sb4, "heaven-sky-earth-midgard");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			GBFolder heaven = dir1.findFolderByName("heaven");
			GBFolder sky = heaven.findFolderByName("sky");
			GBFolder earth = sky.findFolderByName("earth");
			GBFolder midgard = earth.findFolderByName("midgard");

			Assert.assertEquals(heaven.getFiles().get(0), new GBFile("fire", "txt", sb1));
			Assert.assertEquals(sky.getFiles().get(0), new GBFile("water", "txt", sb2));
			Assert.assertEquals(earth.getFiles().get(0), new GBFile("nigth", "txt", sb3));
			Assert.assertEquals(midgard.getFiles().get(0), new GBFile("fate", "txt", sb4));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
