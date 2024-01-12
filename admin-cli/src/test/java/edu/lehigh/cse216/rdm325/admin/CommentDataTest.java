package edu.lehigh.cse216.rdm325.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CommentDataTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CommentDataTest(String testName){
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CommentDataTest.class);
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
        String file = "FA1682189ED";
        String url = "https://www.google.com/help";
        boolean valid = true;
        boolean validFile = true;
        boolean validUrl = true;
        CommentData d = new CommentData(id, content, mid, uid, file, url, valid, validFile, validUrl);

        assertTrue(d.cComment.equals(content));
        assertTrue(d.cmId == mid);
        assertTrue(d.cuId == uid);
        assertTrue(d.cFile.equals(file));
        assertTrue(d.cUrl.equals(url));
        assertTrue(d.cValid == valid);
        assertTrue(d.cValidFile == validFile);
        assertTrue(d.cValidUrl == validUrl);
    }
}
