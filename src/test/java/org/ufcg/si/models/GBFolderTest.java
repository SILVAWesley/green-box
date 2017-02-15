package org.ufcg.si.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ufcg.si.models.storage.FileGB;
import org.ufcg.si.models.storage.FolderGB;
import org.ufcg.si.util.ServerConstants;

public class GBFolderTest {
	private FolderGB dir1;
	private FolderGB dir2;
	private FolderGB dir3;
	private FolderGB dir4;
	String sb1;
	String sb2;
	String sb3;
	String sb4;

	@Before
	public void setUp() {
		dir1 = new FolderGB("parent");
		dir2 = new FolderGB("son1");
		dir3 = new FolderGB("son2");
		dir4 = new FolderGB("grandSon1-1");
		sb1 = new String("I see fire");
		sb2 = new String("I see water");
		sb3 = new String("I see nigth");
		sb4 = new String("I see fate");
	}

	@Test
	public void testEquals() {
		Assert.assertEquals(dir1, new FolderGB("parent"));
		Assert.assertEquals(dir2, new FolderGB("son1"));
		Assert.assertEquals(dir3, new FolderGB("son2"));
		Assert.assertEquals(dir4, new FolderGB("grandSon1-1"));
		Assert.assertFalse(dir1.equals(dir2));
		Assert.assertFalse(dir1.equals(dir3));
		Assert.assertFalse(dir1.equals(dir4));
		Assert.assertFalse(dir2.equals(dir3));
		Assert.assertFalse(dir2.equals(dir4));
		Assert.assertFalse(dir4.equals(dir3));

	}

	@Test
	public void testHashCode() {

		Assert.assertEquals(dir1.hashCode(), -1.788798719E9, 0.00001);
		Assert.assertEquals(dir2.hashCode(), 1.13156001E8, 0.00001);
		Assert.assertEquals(dir3.hashCode(), 1.13156033E8, 0.00001);
		Assert.assertEquals(dir4.hashCode(), -3.85982047E8, 0.00001);
	}

	@Test
	public void testToString() {
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(dir1.getName(), "parent");
		Assert.assertEquals(dir2.getName(), "son1");
		Assert.assertEquals(dir3.getName(), "son2");
		Assert.assertEquals(dir4.getName(), "grandSon1-1");
	}

	@Test
	public void testGetPath() {
		Assert.assertEquals(dir1.getPath(), "parent");
		Assert.assertEquals(dir2.getPath(), "son1");
		Assert.assertEquals(dir3.getPath(), "son2");
		Assert.assertEquals(dir4.getPath(), "grandSon1-1");
	}

	@Test
	public void testGetChildren() {
		dir1.addFolder("Poseidon");
		dir1.addFolder("Mare");
		dir1.addFolder("Ocean");

		dir2.addFolder("Ocean");
		dir2.addFolder("Poseidon");
		dir2.addFolder("Mare");

		Assert.assertEquals(dir1.getFolders().get(0).getName(), dir2.getFolders().get(1).getName());
		Assert.assertEquals(dir1.getFolders().get(1).getName(), dir2.getFolders().get(2).getName());
		Assert.assertEquals(dir1.getFolders().get(2).getName(), dir2.getFolders().get(0).getName());
	}

	@Test
	public void testAddFolder() {
		try {
			dir1.addFolder("pepe");
			dir1.addFolder("blobo");
			dir1.addFolder("bojack");
			dir2.addFolder("something");
			dir2.addFolder("something else");
			Assert.assertEquals(dir1.getFolders().get(0), new FolderGB("pepe", "parent/pepe"));
			Assert.assertEquals(dir1.getFolders().get(1), new FolderGB("blobo", "parent/blobo"));
			Assert.assertEquals(dir1.getFolders().get(2), new FolderGB("bojack", "parent/bojack"));
			Assert.assertEquals(dir2.getFolders().get(0), new FolderGB("something", "son1/something"));
			Assert.assertEquals(dir2.getFolders().get(1), new FolderGB("something else", "son1/something else"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetFiles() {
		String sb1 = new String("I see fire");
		String sb2 = new String("I see water");
		String sb3 = new String("I see nigth");
		String sb4 = new String("I see fate");
		List<FileGB> list1 = new ArrayList<FileGB>();
		List<FileGB> list2 = new ArrayList<FileGB>();
		List<FileGB> list3 = new ArrayList<FileGB>();
		List<FileGB> list4 = new ArrayList<FileGB>();

		try {
			dir1.addFile("fire", ".txt", sb1);
			dir1.addFile("water", ".txt", sb2);
			dir2.addFile("water", ".txt", sb2);
			dir2.addFile("fate", ".txt", sb4);
			dir3.addFile("nigth", ".txt", sb3);
			dir3.addFile("fate", ".txt", sb4);
			dir4.addFile("fire", ".txt", sb1);
			dir4.addFile("fate", ".txt", sb4);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			list1.add(new FileGB("fire", ".txt", sb1, "parent/fire"));
			list1.add(new FileGB("water", ".txt", sb2, "parent/water"));
			list2.add(new FileGB("water", ".txt", sb2, "son1/water"));
			list2.add(new FileGB("fate", ".txt", sb4, "son1/fate"));
			list3.add(new FileGB("nigth", ".txt", sb3, "son2/nigth"));
			list3.add(new FileGB("fate", ".txt", sb4, "son2/fate"));
			list4.add(new FileGB("fire", ".txt", sb1, "grandSon1-1/fire"));
			list4.add(new FileGB("fate", ".txt", sb4, "grandSon1-1/fate"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertEquals(dir1.getFiles(), list1);
		Assert.assertEquals(dir2.getFiles(), list2);
		Assert.assertEquals(dir3.getFiles(), list3);
		Assert.assertEquals(dir4.getFiles(), list4);
	}

	@Test
	public void testRename() {
		dir1.setName("Izanami");
		dir2.setName("Moira");
		dir3.setName("Scylla");
		dir4.setName("Children of the Sun");

		Assert.assertNotEquals(dir1.getName(), "parent");
		Assert.assertNotEquals(dir2.getName(), "son1");
		Assert.assertNotEquals(dir3.getName(), "son2");
		Assert.assertNotEquals(dir4.getName(), "grandSon1-1");

		Assert.assertEquals(dir1.getName(), "Izanami");
		Assert.assertEquals(dir2.getName(), "Moira");
		Assert.assertEquals(dir3.getName(), "Scylla");
		Assert.assertEquals(dir4.getName(), "Children of the Sun");

	}

	@Test
	public void testRenameFile() {
		dir1.addFile("fire", "txt", sb1);
		dir1.addFile("water", "txt", sb2);
		dir2.addFile("nigth", "txt", sb3);
		dir2.addFile("fate", "txt", sb4);
		dir3.addFile("nigth", "txt", sb3);
		dir3.addFile("fire", "txt", sb1);
		dir4.addFile("nigth", "txt", sb3);
		dir4.addFile("fire", "txt", sb1);
		dir1.editFileName("Aurora", "fire", "txt", "parent");
		dir2.editFileName("Boreal", "fate", "txt", "son1");
		dir3.editFileName("Darkness", "nigth", "txt", "son2");
		dir4.editFileName("Tomorrow", "fire", "txt", "grandSon1-1");

		Assert.assertNotEquals(dir1.getFiles().get(0).getName(), "fire");
		Assert.assertNotEquals(dir2.getFiles().get(1).getName(), "fate");
		Assert.assertNotEquals(dir3.getFiles().get(0).getName(), "nigth");
		Assert.assertNotEquals(dir4.getFiles().get(1).getName(), "fire");

		Assert.assertEquals(dir1.getFiles().get(0).getName(), "Aurora");
		Assert.assertEquals(dir2.getFiles().get(1).getName(), "Boreal");
		Assert.assertEquals(dir3.getFiles().get(0).getName(), "Darkness");
		Assert.assertEquals(dir4.getFiles().get(1).getName(), "Tomorrow");
	}

	@Test
	public void testRenameFile2() {
		dir1.addFile("fire", "txt", sb1);
		dir1.addFile("water", "txt", sb2);
		dir2.addFile("nigth", "txt", sb3);
		dir2.addFile("fate", "txt", sb4);
		dir3.addFile("nigth", "txt", sb3);
		dir3.addFile("fire", "txt", sb1);
		dir4.addFile("nigth", "txt", sb3);
		dir4.addFile("fire", "txt", sb1);
		dir1.editFileName("Aurora", "fire", "txt", "parent");
		dir2.editFileName("Aurora", "fate", "txt", "son1");
		dir3.editFileName("Aurora", "nigth", "txt", "son2");
		dir4.editFileName("Aurora", "fire", "txt", "grandSon1-1");
	}

	@Test
	public void testEditFile() {
		dir1.addFile("water", "txt", sb2);
		dir2.addFile("fate", "txt", sb4);
		dir3.addFile("fire", "txt", sb1);
		dir4.addFile("nigth", "txt", sb3);

		dir1.editFileContent("Madness is only the start", "water", "txt", "parent");
		dir2.editFileContent("Death is only the start", "fate", "txt", "son1");
		dir3.editFileContent("Disgrace thy family", "fire", "txt", "son2");
		dir4.editFileContent("Plague take you", "nigth", "txt", "grandSon1-1");
		Assert.assertNotEquals(dir1.getFiles().get(0).getContent(), "I see water");
		Assert.assertNotEquals(dir2.getFiles().get(0).getContent(), "I see fate");
		Assert.assertNotEquals(dir3.getFiles().get(0).getContent(), "I see fire");
		Assert.assertNotEquals(dir4.getFiles().get(0).getContent(), "I see nigth");

		Assert.assertEquals(dir1.getFiles().get(0).getContent(), "Madness is only the start");
		Assert.assertEquals(dir2.getFiles().get(0).getContent(), "Death is only the start");
		Assert.assertEquals(dir3.getFiles().get(0).getContent(), "Disgrace thy family");
		Assert.assertEquals(dir4.getFiles().get(0).getContent(), "Plague take you");

	}

	@Test
	public void testAddFolder2() {

		try {
			dir1.addFolder("games", "parent");
			dir1.addFolder("music", "parent");
			dir2.addFolder("games", "son1");
			dir2.addFolder("books", "son1");
			dir3.addFolder("music", "son2");
			dir3.addFolder("books", "son2");
			dir4.addFolder("comics", "grandSon1-1");
			dir4.addFolder("papers", "grandSon1-1");

		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertEquals(dir1.getFolders().get(0), new FolderGB("games", "parent/games"));
		Assert.assertEquals(dir1.getFolders().get(1), new FolderGB("music", "parent/music"));
		Assert.assertEquals(dir2.getFolders().get(0), new FolderGB("games", "son1/games"));
		Assert.assertEquals(dir2.getFolders().get(1), new FolderGB("books", "son1/books"));
		Assert.assertEquals(dir3.getFolders().get(0), new FolderGB("music", "son2/music"));
		Assert.assertEquals(dir3.getFolders().get(1), new FolderGB("books", "son2/books"));
		Assert.assertEquals(dir4.getFolders().get(0), new FolderGB("comics", "grandSon1-1/comics"));
		Assert.assertEquals(dir4.getFolders().get(1), new FolderGB("papers", "grandSon1-1/papers"));

	}

	@Test
	public void testCreatDirectorywithPath() {
		try {
			dir1.addFolder("heaven");
			dir1.addFolder("sky", "parent/heaven");
			dir1.addFolder("earth", "parent/heaven/sky");
			dir1.addFolder("midgard", "parent/heaven/sky/earth");
			dir1.addFolder("purgatory", "parent/heaven/sky/earth/midgard");
			dir1.addFolder("hell", "parent/heaven/sky/earth/midgard/purgatory");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FolderGB heaven = dir1.getFolders().get(0);
			FolderGB sky = heaven.getFolders().get(0);
			FolderGB earth = sky.getFolders().get(0);
			FolderGB midgard = earth.getFolders().get(0);
			FolderGB purgatory = midgard.getFolders().get(0);
			FolderGB hell = purgatory.getFolders().get(0);

			Assert.assertEquals(heaven, new FolderGB("heaven", "parent/heaven"));
			Assert.assertEquals(sky, new FolderGB("sky", "parent/heaven/sky"));
			Assert.assertEquals(earth, new FolderGB("earth", "parent/heaven/sky/earth"));
			Assert.assertEquals(midgard, new FolderGB("midgard", "parent/heaven/sky/earth/midgard"));
			Assert.assertEquals(purgatory, new FolderGB("purgatory", "parent/heaven/sky/earth/midgard/purgatory"));
			Assert.assertEquals(hell, new FolderGB("hell", "parent/heaven/sky/earth/midgard/purgatory/hell"));
			Assert.assertNotEquals(earth, new FolderGB("sky", "parent/heaven/sky"));
			Assert.assertNotEquals(hell, new FolderGB("earth", "parent/heaven/sky/earth"));
			Assert.assertNotEquals(heaven, new FolderGB("midgard", "parent/heaven/sky/earth/midgard"));
			Assert.assertNotEquals(midgard, new FolderGB("purgatory", "parent/heaven/sky/earth/midgard/purgatory"));
			Assert.assertNotEquals(sky, new FolderGB("hell", "parent/heaven/sky/earth/midgard/purgatory/hell"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testaddFile() {

		try {
			dir1.addFile("fire", "txt", sb1);
			dir1.addFile("water", "txt", sb2);
			dir2.addFile("nigth", "txt", sb3);
			dir2.addFile("fate", "txt", sb4);
			dir3.addFile("nigth", "txt", sb3);
			dir3.addFile("fire", "txt", sb1);
			dir3.addFile("fate", "txt", sb4);
			dir3.addFile("water", "txt", sb2);
			dir4.addFile("nigth", "txt", sb3);
			dir4.addFile("fire", "txt", sb1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Assert.assertEquals(dir1.getFiles().get(0), new FileGB("fire", "txt", sb1, "parent/fire"));
			Assert.assertEquals(dir1.getFiles().get(1), new FileGB("water", "txt", sb2, "parent/water"));
			Assert.assertEquals(dir2.getFiles().get(0), new FileGB("nigth", "txt", sb3, "son1/nigth"));
			Assert.assertEquals(dir2.getFiles().get(1), new FileGB("fate", "txt", sb4, "son1/fate"));
			Assert.assertEquals(dir3.getFiles().get(0), new FileGB("nigth", "txt", sb3, "son2/nigth"));
			Assert.assertEquals(dir3.getFiles().get(1), new FileGB("fire", "txt", sb1, "son2/fire"));
			Assert.assertEquals(dir3.getFiles().get(2), new FileGB("fate", "txt", sb4, "son2/fate"));
			Assert.assertEquals(dir3.getFiles().get(3), new FileGB("water", "txt", sb2, "son2/water"));
			Assert.assertEquals(dir4.getFiles().get(0), new FileGB("nigth", "txt", sb3, "grandSon1-1/nigth"));
			Assert.assertEquals(dir4.getFiles().get(1), new FileGB("fire", "txt", sb1, "grandSon1-1/fire"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//	@Test
//	public void testDeleteFolder(){
//
//		try {
//			dir1.addFolder("games", "parent");
//			dir1.addFolder("music", "parent");
//			dir2.addFolder("games", "son1");
//			dir2.addFolder("books", "son1");
//			dir3.addFolder("music", "son2");
//			dir3.addFolder("books", "son2");
//			dir4.addFolder("comics", "grandSon1-1");
//			dir4.addFolder("papers", "grandSon1-1");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		Assert.assertEquals(dir1.getFolders().get(0), new FolderGB("games", "parent/games"));
//		Assert.assertEquals(dir1.getFolders().get(1), new FolderGB("music", "parent/music"));
//		Assert.assertEquals(dir2.getFolders().get(0), new FolderGB("games", "son1/games"));
//		Assert.assertEquals(dir2.getFolders().get(1), new FolderGB("books", "son1/books"));
//		Assert.assertEquals(dir3.getFolders().get(0), new FolderGB("music", "son2/music"));
//		Assert.assertEquals(dir3.getFolders().get(1), new FolderGB("books", "son2/books"));
//		Assert.assertEquals(dir4.getFolders().get(0), new FolderGB("comics", "grandSon1-1/comics"));
//		Assert.assertEquals(dir4.getFolders().get(1), new FolderGB("papers", "grandSon1-1/papers"));
//		
//		dir1.deleteFolder("parent/games", "games");
//		dir1.deleteFolder("parent/music", "music");
//		dir2.deleteFolder("son1/games", "games");
//		dir2.deleteFolder("son1/books", "books");
//		dir3.deleteFolder("son2/music", "music");
//		dir3.deleteFolder("son2/books", "books");
//		dir4.deleteFolder("grandSon1-1/comics", "comics");
//		dir4.deleteFolder("grandSon1-1/papers", "papers");
//		Assert.assertEquals(dir1.getFolders(), new ArrayList<FolderGB>());
//
//
//	}
	
	
	@Test
	public void testDeleteFile() {
		String sb1 = new String("I see fire");
		String sb2 = new String("I see water");
		String sb3 = new String("I see nigth");
		String sb4 = new String("I see fate");
		List<FileGB> list1 = new ArrayList<FileGB>();
		List<FileGB> list2 = new ArrayList<FileGB>();
		List<FileGB> list3 = new ArrayList<FileGB>();
		List<FileGB> list4 = new ArrayList<FileGB>();

		try {
			dir1.addFile("fire", "txt", sb1);
			dir1.addFile("water", "txt", sb2);
			dir2.addFile("water", "txt", sb2);
			dir2.addFile("fate", "txt", sb4);
			dir3.addFile("nigth", "txt", sb3);
			dir3.addFile("fate", "txt", sb4);
			dir4.addFile("fire", "txt", sb1);
			dir4.addFile("fate", "txt", sb4);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			list1.add(new FileGB("fire", "txt", sb1, "parent/fire"));
			list1.add(new FileGB("water", "txt", sb2, "parent/water"));
			list2.add(new FileGB("water", "txt", sb2, "son1/water"));
			list2.add(new FileGB("fate", "txt", sb4, "son1/fate"));
			list3.add(new FileGB("nigth", "txt", sb3, "son2/nigth"));
			list3.add(new FileGB("fate", "txt", sb4, "son2/fate"));
			list4.add(new FileGB("fire", "txt", sb1, "grandSon1-1/fire"));
			list4.add(new FileGB("fate", "txt", sb4, "grandSon1-1/fate"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertEquals(dir1.getFiles(), list1);
		Assert.assertEquals(dir2.getFiles(), list2);
		Assert.assertEquals(dir3.getFiles(), list3);
		Assert.assertEquals(dir4.getFiles(), list4);
	
		
		
		dir1.deleteFile("fire", "txt", "parent");
		dir1.deleteFile("water", "txt", "parent");
		dir2.deleteFile("water", "txt", "son1");
		dir2.deleteFile("fate", "txt", "son1");
		dir3.deleteFile("nigth", "txt", "son2");
		dir3.deleteFile("fate", "txt", "son2");
		dir4.deleteFile("fire", "txt", "grandSon1-1");
		dir4.deleteFile("fate", "txt", "grandSon1-1");

		Assert.assertEquals(dir1.getFiles(), new ArrayList<FileGB>());
		Assert.assertEquals(dir2.getFiles(), new ArrayList<FileGB>());
		Assert.assertEquals(dir3.getFiles(), new ArrayList<FileGB>());
		Assert.assertEquals(dir4.getFiles(), new ArrayList<FileGB>());
	}

	@Test
	public void testAddFile2() {
		try {
			dir1.addFolder("heaven");
			dir1.addFolder("sky", "parent/heaven");
			dir1.addFolder("earth", "parent/heaven/sky");
			dir1.addFolder("midgard", "parent/heaven/sky/earth");
			dir1.addFolder("purgatory", "parent/heaven/sky/earth/midgard");
			dir1.addFolder("hell", "parent/heaven/sky/earth/midgard/purgatory");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dir1.addFile("fire", "txt", sb1, "parent/heaven");
			dir1.addFile("water", "txt", sb2, "parent/heaven/sky");
			dir1.addFile("nigth", "txt", sb3, "parent/heaven/sky/earth");
			dir1.addFile("fate", "txt", sb4, "parent/heaven/sky/earth/midgard");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			FolderGB heaven = dir1.getFolders().get(0);
			FolderGB sky = heaven.getFolders().get(0);
			FolderGB earth = sky.getFolders().get(0);
			FolderGB midgard = earth.getFolders().get(0);

			Assert.assertEquals(heaven.getFiles().get(0), new FileGB("fire", "txt", sb1, "parent/heaven/fire"));
			Assert.assertEquals(sky.getFiles().get(0), new FileGB("water", "txt", sb2, "parent/heaven/sky/water"));
			Assert.assertEquals(earth.getFiles().get(0),
					new FileGB("nigth", "txt", sb3, "parent/heaven/sky/earth/nigth"));
			Assert.assertEquals(midgard.getFiles().get(0),
					new FileGB("fate", "txt", sb4, "parent/heaven/sky/earth/midgard/fate"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}