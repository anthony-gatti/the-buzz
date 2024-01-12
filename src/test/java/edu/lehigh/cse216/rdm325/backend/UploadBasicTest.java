package edu.lehigh.cse216.rdm325.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class UploadBasicTest 
    extends TestCase
{
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UploadBasicTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(UploadBasicTest.class);
    }

    /**
     * Ensure that the getMimeType function works correctly
     */
    public void testGetMimeType() {
        String xls = "xls";
        String xml = "xml";
        String csv = "csv";
        String pdf = "pdf";
        String jpg = "jpg";
        String png = "png";
        assertTrue(UploadBasic.getMimeType(xls).equals("application/vnd.ms-excel"));
        assertTrue(UploadBasic.getMimeType(xml).equals("text/xml"));
        assertTrue(UploadBasic.getMimeType(csv).equals("text/plain"));
        assertTrue(UploadBasic.getMimeType(pdf).equals("application/pdf"));
        assertTrue(UploadBasic.getMimeType(jpg).equals("image/jpeg"));
        assertTrue(UploadBasic.getMimeType(png).equals("image/png"));
    }
}