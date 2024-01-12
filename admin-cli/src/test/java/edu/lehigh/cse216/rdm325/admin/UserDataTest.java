package edu.lehigh.cse216.rdm325.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UserDataTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UserDataTest(String testName){
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(UserDataTest.class);
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
        boolean valid = true;
        UserData d = new UserData(id, name, email, gender, SO, username, note, valid);

        assertTrue(d.uName.equals(name));
        assertTrue(d.uEmail.equals(email));
        assertTrue(d.uGender.equals(gender));
        assertTrue(d.uSO.equals(SO));
        assertTrue(d.uUsername.equals(username));
        assertTrue(d.uNote.equals(note));
        assertTrue(d.uValid == valid);
    }
}
