package edu.lehigh.cse216.rdm325.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MessageDataTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MessageDataTest(String testName){
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MessageDataTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        String title = "Test Subject";
        String content = "Test Message";
        int id = 17;
        int uid = 1;
        String file = "FA1682189ED";
        String url = "https://www.google.com/help";
        boolean valid = true;
        boolean validFile = true;
        boolean validUrl = true;
        MessageData d = new MessageData(id, title, content, uid, file, url, valid, validFile, validUrl);

        assertTrue(d.mSubject.equals(title));
        assertTrue(d.mMessage.equals(content));
        assertTrue(d.mId == id);
        assertTrue(d.muId == uid);
        assertTrue(d.mFile.equals(file));
        assertTrue(d.mUrl.equals(url));
        assertTrue(d.mValid == valid);
        assertTrue(d.mValidFile == validFile);
        assertTrue(d.mValidUrl == validUrl);
    }
}
