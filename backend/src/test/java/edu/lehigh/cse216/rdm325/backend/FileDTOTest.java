package edu.lehigh.cse216.rdm325.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class FileDTOTest 
    extends TestCase
{
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FileDTOTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(FileDTOTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        String data = "Test Data";
        String mime = "png";
        String name = "test.png";
        FileDTO d = new FileDTO(data,mime, name);

        assertTrue(d.fData.equals(data));
        assertTrue(d.fName.equals(name));
        assertTrue(d.mimeType.equals(mime));
    }

    /**
     * Ensure that the makeString function works correctly
     */
    public void testMakeString() {
        String data = "Test Data";
        String mime = "png";
        String name = "test.png";
        FileDTO d = new FileDTO(data,mime, name);
        assertTrue(d.makeString().equals("fData:" + data + " mimeType:" + mime + " fName:" + name));
    }

    /**
     * Ensure that the toDTO function works correctly
     */
    public void testToDTO() {
        String data = "Test/Data";
        String mime = "png";
        String name = "test.png";
        FileDTO dto = new FileDTO(data, mime, name);
        String json = dto.makeString();
        FileDTO d = FileDTO.toDTO(json);
        assertTrue(d.fData.equals(data));
        assertTrue(d.fName.equals(name));
        assertTrue(d.mimeType.equals(mime));
    }
}