package edu.lehigh.cse216.rdm325.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UpvoteDataTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UpvoteDataTest(String testName){
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(UpvoteDataTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        int id = 17;
        int mid = 10;
        int uid = 100;
        UpvoteData d = new UpvoteData(id, mid, uid);

        assertTrue(d.muvId == id);
        assertTrue(d.muvmId == mid);
        assertTrue(d.muvuId == uid);
    }
}
