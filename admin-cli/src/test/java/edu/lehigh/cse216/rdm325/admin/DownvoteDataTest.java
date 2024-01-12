package edu.lehigh.cse216.rdm325.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DownvoteDataTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DownvoteDataTest(String testName){
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DownvoteDataTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        int id = 17;
        int mid = 10;
        int uid = 100;
        DownvoteData d = new DownvoteData(id, mid, uid);

        assertTrue(d.mdvId == id);
        assertTrue(d.mdvmId == mid);
        assertTrue(d.mdvuId == uid);
    }
}
