package edu.lehigh.cse216.rdm325.backend;

import edu.lehigh.cse216.rdm325.backend.DataRowCommentTest;
import edu.lehigh.cse216.rdm325.backend.CreateAuth;
import java.util.Base64;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CreateAuthTest extends TestCase{
    
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CreateAuthTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CreateAuthTest.class);
    }

    /**
     * Testing `createSessionToken`
     */
    public void testCreateSessionToken(){
        String token = CreateAuth.createSessionToken("rdm325@lehigh.edu", "rdm325");
        String encodedString = base64Encoder.encodeToString(("rdm325@lehigh.edu:rdm325").getBytes());
        assertTrue(token.equals(encodedString));
    }

    /**
     * Testing `createAdminToken`
     */
    public void testCreateAdminToken(){
        String token = CreateAuth.createAdminToken("Hello There my good friend");
        String encodedString = base64Encoder.encodeToString(("Hello There my good friend").getBytes());
        assertTrue(token.equals(encodedString));
    }
}
