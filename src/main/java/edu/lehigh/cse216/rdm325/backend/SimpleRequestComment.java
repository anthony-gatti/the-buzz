package edu.lehigh.cse216.rdm325.backend;

/**
 * SimpleRequestComment provides a format for clients to present Comment content 
 * strings to the server.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class SimpleRequestComment {
    /**
     * The Comment content being provided by the client.
     */
    public String cContent;

    public String cFile;

    public String fName;

    public String mimetype;

    public String cURL;
}