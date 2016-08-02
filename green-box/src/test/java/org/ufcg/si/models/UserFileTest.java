package org.ufcg.si.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ufcg.si.models.UserFile;

public class UserFileTest {
	StringBuffer sb1 = new StringBuffer("I see fire");
	StringBuffer sb2 = new StringBuffer("I see water");
	StringBuffer sb3 = new StringBuffer("I see nigth");
	StringBuffer sb4 = new StringBuffer("I see fate");
	private UserFile file1;
	private UserFile file2;
	private UserFile file3;
	private UserFile file4;

	@Before
	public void setup() throws Exception {
		file1 = new UserFile("fire", ".txt", sb1);
		file2 = new UserFile("water", ".txt", sb2);
		file3 = new UserFile("nigth", ".txt", sb3);
		file4 = new UserFile("fate", ".txt", sb4);

	}
	
	@Test(expected = NullPointerException.class)
	public void construction() throws Exception{
		UserFile broken1 = new UserFile(null, null, null);
		UserFile broken2 = new UserFile(null, ".txt", sb1);
		UserFile broken3 = new UserFile("broken", ".txt", null);
		UserFile broken4 = new UserFile("broken", null, sb1);
	}
	
//	@Test
//	public void construction() throws Exception {
//		try {
//			UserFile broken1 = new UserFile(null, null, null);
//			Assert.fail();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		}
//		try {
//			UserFile broken2 = new UserFile(null, ".txt", sb1);
//			Assert.fail();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		}
//		try {
//			UserFile broken3 = new UserFile("broken", ".txt", null);
//			Assert.fail();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		}
//		try {
//			UserFile broken4 = new UserFile("broken", null, sb1);
//			Assert.fail();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		}
//	}
	@Test
	public void testEquals() {
		try {
			Assert.assertFalse(file1.equals(file2));
			Assert.assertEquals(file1, new UserFile("fire", ".txt", sb1));
			Assert.assertEquals(file2, new UserFile("water", ".txt", sb2));
			Assert.assertEquals(file3, new UserFile("nigth", ".txt", sb3));
			Assert.assertEquals(file4, new UserFile("fate", ".txt", sb4));
			Assert.assertFalse(file2.equals(new UserFile("nigth", ".txt", sb3)));
			Assert.assertTrue(file3.equals(new UserFile("nigth", ".txt", sb2)));
			Assert.assertTrue(file4.equals(new UserFile("fate", ".txt", sb2)));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(file1.getName(), "fire");
		Assert.assertEquals(file2.getName(), "water");
		Assert.assertEquals(file3.getName(), "nigth");
		Assert.assertEquals(file4.getName(), "fate");
		Assert.assertFalse(file1.getName().equals("depths of despair"));
		Assert.assertFalse(file2.getName().equals("somebody else"));
		Assert.assertFalse(file3.getName().equals("not you"));
		Assert.assertFalse(file4.getName().equals("someone"));
	}

	@Test
	public void testGetInternFile() {
		Assert.assertEquals(file1.getInternFile().getPath(), "fire.txt");
		Assert.assertEquals(file2.getInternFile().getPath(), "water.txt");
		Assert.assertEquals(file3.getInternFile().getPath(), "nigth.txt");
		Assert.assertEquals(file4.getInternFile().getPath(), "fate.txt");
		Assert.assertFalse(file1.getInternFile().getPath().equals(file2.getInternFile().getPath()));
		Assert.assertFalse(file1.getInternFile().getPath().equals(file3.getInternFile().getPath()));
		Assert.assertFalse(file1.getInternFile().getPath().equals(file4.getInternFile().getPath()));
		Assert.assertFalse(file3.getInternFile().getPath().equals(file4.getInternFile().getPath()));
		Assert.assertFalse(file2.getInternFile().getPath().equals(file1.getInternFile().getPath()));
	}
}
