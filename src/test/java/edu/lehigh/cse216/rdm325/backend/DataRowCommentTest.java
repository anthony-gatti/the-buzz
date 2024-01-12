package edu.lehigh.cse216.rdm325.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DataRowCommentTest 
    extends TestCase
{
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataRowCommentTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DataRowCommentTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        String content = "Test Message";
        int id = 17;
        int uid = 1;
        int mid = 2;
        FileDTO file = null;
        String url = "url";
        Boolean valid = new Boolean(true);
        DataRowComment d = new DataRowComment(id, content, uid, mid, file, url, valid);

        assertTrue(d.cContent.equals(content));
        assertTrue(d.mId == mid);
        assertTrue(d.uId == uid);
        assertFalse(d.cCreated == null);
        assertTrue(d.cFile == file);
        assertTrue(d.cURL.equals(url));
        assertTrue(d.valid == valid);
    }

    /**
     * Ensure that the copy constructor works correctly
     */
    public void testCopyconstructor() {
        String content = "Test Message For Copy";
        int id = 177;
        int uid = 1;
        int mid = 2;
        FileDTO file = null;
        String url = "url";
        Boolean valid = new Boolean(true);
        DataRowComment d = new DataRowComment(id, content, uid, mid, file, url, valid);
        DataRowComment d2 = new DataRowComment(d);
        assertTrue(d2.cContent.equals(d.cContent));
        assertTrue(d2.mId == d.mId);
        assertTrue(d2.uId == d.uId);
        assertTrue(d2.cCreated.equals(d.cCreated));
        assertTrue(d2.cFile == d.cFile);
        assertTrue(d2.cURL.equals(d.cURL));
        assertTrue(d2.valid == d.valid);
    }
}