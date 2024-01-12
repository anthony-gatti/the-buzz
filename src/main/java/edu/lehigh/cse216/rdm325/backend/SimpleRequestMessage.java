package edu.lehigh.cse216.rdm325.backend;

/**
 * SimpleRequestMessage provides a format for clients to present a Message's subject and message 
 * strings to the server.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class SimpleRequestMessage {
    /**
     * The title being provided by the client.
     */
    public String mSubject;

    /**
     * The message being provided by the client.
     */
    public String mMessage;

    public String mFile;

    public String fName;

    public String mimetype;

    public String mURL;
}