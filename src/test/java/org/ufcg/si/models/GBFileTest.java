package org.ufcg.si.models;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ufcg.si.models.storage.FileGB;
import org.ufcg.si.util.ServerConstants;

public class GBFileTest {
	String sb1;
	String sb2;
	String sb3;
	String sb4;
	private FileGB file1;
	private FileGB file2;
	private FileGB file3;
	private FileGB file4;

	@Before
	public void setup() throws Exception {
		sb1 = new String("I see fire");
		sb2 = new String("I see water");
		sb3 = new String("I see nigth");
		sb4 = new String("I see fate");
		file1 = new FileGB("fire", "txt", sb1, "archives/file1");
		file2 = new FileGB("water", "txt", sb2, "archives/file2");
		file3 = new FileGB("nigth", "txt", sb3, "archives/file3");
		file4 = new FileGB("fate", "txt", sb4, "archives/file4");

	}

	

	@Test
	public void testEquals() {
		try {
			Assert.assertNotEquals(file1, file2);
			Assert.assertEquals(file1, new FileGB("fire", "txt", sb1, "archives/file1"));
			Assert.assertEquals(file2, new FileGB("water", "txt", sb2, "archives/file2"));
			Assert.assertEquals(file3, new FileGB("nigth", "txt", sb3, "archives/file3"));
			Assert.assertEquals(file4, new FileGB("fate", "txt", sb4, "archives/file4"));
			Assert.assertFalse(file2.equals(new FileGB("nigth", "txt", sb3, "archives/file3")));
			Assert.assertTrue(file3.equals(new FileGB("nigth", "txt", sb2, "archives/file3")));
			Assert.assertTrue(file4.equals(new FileGB("fate", "txt", sb2, "archives/file4")));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testGetContent() {
		FileGB tempFile1;
		FileGB tempFile2;
		FileGB tempFile3;
		FileGB tempFile4;

		tempFile1 = new FileGB("fire", "txt", sb1, "archives/tempFile1");
		tempFile2 = new FileGB("water", "txt", sb2, "archives/tempFile2");
		tempFile3 = new FileGB("nigth", "txt", sb3, "archives/tempFile3");
		tempFile4 = new FileGB("fate", "txt", sb4, "archives/tempFile4");
		Assert.assertEquals(file1.getContent(), tempFile1.getContent());
		Assert.assertEquals(file2.getContent(), tempFile2.getContent());
		Assert.assertEquals(file3.getContent(), tempFile3.getContent());
		Assert.assertEquals(file4.getContent(), tempFile4.getContent());

		Assert.assertEquals(file1.getContent(), new FileGB("fire", "txt", sb1, "storage/file1").getContent());
		Assert.assertEquals(file2.getContent(), new FileGB("water", "txt", sb2, "storage/file2").getContent());
		Assert.assertEquals(file3.getContent(), new FileGB("nigth", "txt", sb3, "storage/file3").getContent());
		Assert.assertEquals(file4.getContent(), new FileGB("fate", "txt", sb4, "storage/file4").getContent());

	}

	@Test
	public void testSetContent() throws IOException {

		file1.setContent("Fire consumes us all");
		file2.setContent("Water drowns us all");
		file3.setContent("Nigth blinds us all");
		file4.setContent("Fate condemn us all");

		Assert.assertNotEquals(file1.getContent(), "I see fire");
		Assert.assertNotEquals(file2.getContent(), "I see water");
		Assert.assertNotEquals(file3.getContent(), "I see nigth");
		Assert.assertNotEquals(file4.getContent(), "I see fate");

		Assert.assertEquals(file1.getContent(), "Fire consumes us all");
		Assert.assertEquals(file2.getContent(), "Water drowns us all");
		Assert.assertEquals(file3.getContent(), "Nigth blinds us all");
		Assert.assertEquals(file4.getContent(), "Fate condemn us all");

	}

	@Test
	public void testGetName() {
		Assert.assertEquals(file1.getName(), "fire");
		Assert.assertEquals(file2.getName(), "water");
		Assert.assertEquals(file3.getName(), "nigth");
		Assert.assertEquals(file4.getName(), "fate");
		Assert.assertNotEquals(file1.getName(), "depths of despair");
		Assert.assertNotEquals(file2.getName(), "somebody else");
		Assert.assertNotEquals(file3.getName(), "not you");
		Assert.assertNotEquals(file4.getName(), "someone");
	}

	@Test
	public void testSetPath() {
		file1.setPath("storage/file1");
		file2.setPath("storage/file2");
		file3.setPath("storage/file3");
		file4.setPath("storage/file4");

		Assert.assertNotEquals(file1.getPath(), "archives/file1");
		Assert.assertNotEquals(file2.getPath(), "archives/file2");
		Assert.assertNotEquals(file3.getPath(), "archives/file3");
		Assert.assertNotEquals(file4.getPath(), "archives/file4");
		Assert.assertEquals(file1.getPath(), "storage/file1");
		Assert.assertEquals(file2.getPath(), "storage/file2");
		Assert.assertEquals(file3.getPath(), "storage/file3");
		Assert.assertEquals(file4.getPath(), "storage/file4");

	}

	@Test
	public void testGetPath() {
		Assert.assertEquals(file1.getPath(), "archives/file1");
		Assert.assertEquals(file2.getPath(), "archives/file2");
		Assert.assertEquals(file3.getPath(), "archives/file3");
		Assert.assertEquals(file4.getPath(), "archives/file4");
	}

	@Test
	public void testRename() {
		file1.setName("Vulcan");
		file2.setName("Poseidon");
		file3.setName("Nyx");
		file4.setName("Moira");

		Assert.assertNotEquals(file1.getName(), "fire");
		Assert.assertNotEquals(file2.getName(), "water");
		Assert.assertNotEquals(file3.getName(), "nigth");
		Assert.assertNotEquals(file4.getName(), "fate");
		Assert.assertEquals(file1.getName(), "Vulcan");
		Assert.assertEquals(file2.getName(), "Poseidon");
		Assert.assertEquals(file3.getName(), "Nyx");
		Assert.assertEquals(file4.getName(), "Moira");

	}

	@Test
	public void testRename2() {
		file1.setName("Vulcan");
		file1.setExtension("md");
		file2.setName("Poseidon");
		file2.setExtension("md");
		file3.setName("Nyx");
		file3.setExtension("md");
		file4.setName("Moira");
		file4.setExtension("md");

		Assert.assertNotEquals(file1.getName(), "fire");
		Assert.assertNotEquals(file2.getName(), "water");
		Assert.assertNotEquals(file3.getName(), "nigth");
		Assert.assertNotEquals(file4.getName(), "fate");
		Assert.assertEquals(file1.getName(), "Vulcan");
		Assert.assertEquals(file2.getName(), "Poseidon");
		Assert.assertEquals(file3.getName(), "Nyx");
		Assert.assertEquals(file4.getName(), "Moira");

		Assert.assertNotEquals(file1.getExtension(), "txt");
		Assert.assertNotEquals(file2.getExtension(), "txt");
		Assert.assertNotEquals(file3.getExtension(), "txt");
		Assert.assertNotEquals(file4.getExtension(), "txt");
		Assert.assertEquals(file1.getExtension(), "md");
		Assert.assertEquals(file2.getExtension(), "md");
		Assert.assertEquals(file3.getExtension(), "md");
		Assert.assertEquals(file4.getExtension(), "md");

	}

	@Test
	public void testGetExtension() {
		Assert.assertEquals(file1.getExtension(), "txt");
		Assert.assertEquals(file2.getExtension(), "txt");
		Assert.assertEquals(file3.getExtension(), "txt");
		Assert.assertEquals(file4.getExtension(), "txt");

		Assert.assertNotEquals(file1.getExtension(), "exe");
		Assert.assertNotEquals(file2.getExtension(), "font");
		Assert.assertNotEquals(file3.getExtension(), "fmt");
		Assert.assertNotEquals(file4.getExtension(), "md");

	}

	@Test
	public void testToString() {

		Assert.assertEquals(file1.toString(), new FileGB("fire", "txt", sb1, "archives/file1").toString());
		Assert.assertEquals(file2.toString(), new FileGB("water", "txt", sb2, "archives/file2").toString());
		Assert.assertEquals(file3.toString(), new FileGB("nigth", "txt", sb3, "archives/file3").toString());
		Assert.assertEquals(file4.toString(), new FileGB("fate", "txt", sb4, "archives/file4").toString());

		Assert.assertNotEquals(file2.toString(), new FileGB("fire", "txt", sb1, "archives/file1").toString());
		Assert.assertNotEquals(file3.toString(), new FileGB("fire", "txt", sb1, "archives/file1").toString());
		Assert.assertNotEquals(file4.toString(), new FileGB("fire", "txt", sb1, "archives/file1").toString());

		Assert.assertNotEquals(file1.toString(), new FileGB("water", "txt", sb2, "archives/file2").toString());
		Assert.assertNotEquals(file3.toString(), new FileGB("water", "txt", sb2, "archives/file2").toString());
		Assert.assertNotEquals(file4.toString(), new FileGB("water", "txt", sb2, "archives/file2").toString());

	}

	@Test
	public void testHashCode() {
		Assert.assertEquals(file1.hashCode(), new FileGB("fire", "txt", sb1, "archives/file1").hashCode());
		Assert.assertEquals(file2.hashCode(), new FileGB("water", "txt", sb2, "archives/file2").hashCode());
		Assert.assertEquals(file3.hashCode(), new FileGB("nigth", "txt", sb3, "archives/file3").hashCode());
		Assert.assertEquals(file4.hashCode(), new FileGB("fate", "txt", sb4, "archives/file4").hashCode());

		Assert.assertNotEquals(file2.hashCode(), new FileGB("fire", "txt", sb1, "archives/file1").hashCode());
		Assert.assertNotEquals(file3.hashCode(), new FileGB("fire", "txt", sb1, "archives/file1").hashCode());
		Assert.assertNotEquals(file4.hashCode(), new FileGB("fire", "txt", sb1, "archives/file1").hashCode());

		Assert.assertNotEquals(file1.hashCode(), new FileGB("water", "txt", sb2, "archives/file2").hashCode());
		Assert.assertNotEquals(file3.hashCode(), new FileGB("water", "txt", sb2, "archives/file2").hashCode());
		Assert.assertNotEquals(file4.hashCode(), new FileGB("water", "txt", sb2, "archives/file2").hashCode());

	}

}