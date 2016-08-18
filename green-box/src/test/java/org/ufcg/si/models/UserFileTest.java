package org.ufcg.si.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ufcg.si.models.UserFile;
import org.ufcg.si.util.ServerConstants;

public class UserFileTest {
	String sb1;
	String sb2;
	String sb3;
	String sb4;
	private UserFile file1;
	private UserFile file2;
	private UserFile file3;
	private UserFile file4;

	@Before
	public void setup() throws Exception {
		sb1 = new String("I see fire");
		sb2 = new String("I see water");
		sb3 = new String("I see nigth");
		sb4 = new String("I see fate");
		file1 = new UserFile("fire", "txt", sb1);
		file2 = new UserFile("water", "txt", sb2);
		file3 = new UserFile("nigth", "txt", sb3);
		file4 = new UserFile("fate", "txt", sb4);

	}

	@Test(expected = NullPointerException.class)
	public void construction() throws Exception {
		UserFile broken1 = new UserFile(null, null, null);
		UserFile broken2 = new UserFile(null, ".txt", sb1);
		UserFile broken3 = new UserFile("broken", ".txt", null);
		UserFile broken4 = new UserFile("broken", null, sb1);
	}


	@Test
	public void testEquals() {
		try {
			Assert.assertFalse(file1.equals(file2));
			Assert.assertEquals(file1, new UserFile("fire", "txt", sb1));
			Assert.assertEquals(file2, new UserFile("water", "txt", sb2));
			Assert.assertEquals(file3, new UserFile("nigth", "txt", sb3));
			Assert.assertEquals(file4, new UserFile("fate", "txt", sb4));
			Assert.assertFalse(file2.equals(new UserFile("nigth", "txt", sb3)));
			Assert.assertTrue(file3.equals(new UserFile("nigth", "txt", sb2)));
			Assert.assertTrue(file4.equals(new UserFile("fate", "txt", sb2)));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testGetContent() {
		UserFile tempFile1;
		UserFile tempFile2;
		UserFile tempFile3;
		UserFile tempFile4;
		try {
			tempFile1 = new UserFile("fire", "txt", sb1);
			tempFile2 = new UserFile("water", "txt", sb2);
			tempFile3 = new UserFile("nigth", "txt", sb3);
			tempFile4 = new UserFile("fate", "txt", sb4);
			Assert.assertEquals(file1.getContent(), tempFile1.getContent());
			Assert.assertEquals(file2.getContent(), tempFile2.getContent());
			Assert.assertEquals(file3.getContent(),tempFile3.getContent());
			Assert.assertEquals(file4.getContent(), tempFile4.getContent());
			
		} catch (Exception e) {
			e.printStackTrace();
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
	public void testGetExtension(){
		Assert.assertEquals(file1.getExtension(), "txt");
		Assert.assertEquals(file2.getExtension(), "txt");
		Assert.assertEquals(file3.getExtension(), "txt");
		Assert.assertEquals(file4.getExtension(), "txt");

		Assert.assertNotEquals(file1.getExtension(), "exe");
		Assert.assertNotEquals(file2.getExtension(), "font");
		Assert.assertNotEquals(file3.getExtension(), "fmt");
		Assert.assertNotEquals(file4.getExtension(), "md");
		
	}

}
