package edu.lehigh.cse216.rdm325.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DataRowUserTest 
    extends TestCase
{
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataRowUserTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DataRowUserTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        int id = 17;
        String name = "Robert Magnus";
        String email = "rdm325@lehigh.edu";
        String gender = "male";
        String SO = "straight";
        String username = "rdm325";
        String note = "hello";
        Boolean valid = new Boolean(true);
        DataRowUser d = new DataRowUser(id, name, email, gender, SO, username, note, valid);

        assertTrue(d.uName.equals(name));
        assertTrue(d.uEmail.equals(email));
        assertTrue(d.uGender.equals(gender));
        assertTrue(d.uSO.equals(SO));
        assertTrue(d.uUsername.equals(username));
        assertTrue(d.uNote.equals(note));
        assertFalse(d.uCreated == null);
        assertTrue(d.valid == valid);
    }

    /**
     * Ensure that the copy constructor works correctly
     */
    public void testCopyconstructor() {
        int id = 17;
        String name = "Robert Magnus";
        String email = "rdm325@lehigh.edu";
        String gender = "male";
        String SO = "straight";
        String username = "rdm325";
        String note = "hello";
        Boolean valid = new Boolean(true);
        DataRowUser d = new DataRowUser(id, name, email, gender, SO, username, note, valid);
        DataRowUser d2 = new DataRowUser(d);
        assertTrue(d2.uName.equals(d.uName));
        assertTrue(d2.uEmail.equals(d.uEmail));
        assertTrue(d2.uGender.equals(d.uGender));
        assertTrue(d2.uSO.equals(d.uSO));
        assertTrue(d2.uUsername.equals(d.uUsername));
        assertTrue(d2.uNote.equals(d.uNote));
        assertTrue(d2.uCreated.equals(d.uCreated));
        assertTrue(d2.valid == d.valid);
    }
}