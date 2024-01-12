package edu.lehigh.cse216.rdm325.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import edu.lehigh.cse216.rdm325.backend.FileDTO;

/**
 * Unit test for simple App.
 */
public class DataRowMessageTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataRowMessageTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DataRowMessageTest.class);
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
        String username = "test";
        int upVote = 0;
        int downVote = 10;
        int isUpVote = 0;
        int isDownVote = 0;
        FileDTO file = null;
        String url = "www.google.com";
        Boolean valid = new Boolean(true);
        Boolean validFile = new Boolean(true);
        Boolean validURL = new Boolean(false);
        DataRowMessage d = new DataRowMessage(id, title, content, uid, username, upVote, downVote, isUpVote, isDownVote, file, url, valid, validFile, validURL);

        assertTrue(d.mSubject.equals(title));
        assertTrue(d.mMessage.equals(content));
        assertTrue(d.mid == id);
        assertTrue(d.uid == uid);
        assertTrue(d.uUsername.equals(username));
        assertTrue(d.mUpvotes == upVote);
        assertTrue(d.mDownvotes == downVote);
        assertTrue(d.mIsUpvote == isUpVote);
        assertTrue(d.mIsDownvote == isDownVote);
        assertFalse(d.mCreated == null);
        assertTrue(d.mFile == file);
        assertTrue(d.mURL == url);
        assertTrue(d.valid == valid);
    }

    /**
     * Ensure that the copy constructor works correctly
     */
    public void testCopyconstructor() {
        String title = "Test Subject For Copy";
        String content = "Test Message For Copy";
        int id = 177;
        int uid = 1;
        String username = "test";
        int upVote = 0;
        int downVote = 10;
        int isUpVote = 0;
        int isDownVote = 0;
        FileDTO file = null;
        String url = "www.google.com";
        Boolean valid = new Boolean(true);
        Boolean validFile = new Boolean(true);
        Boolean validURL = new Boolean(false);
        DataRowMessage d = new DataRowMessage(id, title, content, uid, username, upVote, downVote, isUpVote, isDownVote, file, url, valid, validFile, validURL);
        DataRowMessage d2 = new DataRowMessage(d);
        assertTrue(d2.mSubject.equals(d.mSubject));
        assertTrue(d2.mMessage.equals(d.mMessage));
        assertTrue(d2.mid == d.mid);
        assertTrue(d2.uid == d.uid);
        assertTrue(d2.uUsername.equals(d.uUsername));
        assertTrue(d2.mUpvotes == d.mUpvotes);
        assertTrue(d2.mDownvotes == d.mDownvotes);
        assertTrue(d2.mIsUpvote == d.mIsUpvote);
        assertTrue(d2.mIsDownvote == d.mIsDownvote);
        assertTrue(d2.mCreated.equals(d.mCreated));
        assertTrue(d2.mFile == d.mFile);
        assertTrue(d2.mURL == d.mURL);
        assertTrue(d2.valid == d.valid);
        assertTrue(d2.validFile == d.validFile);
        assertTrue(d2.validURL == d.validURL);
    }
}