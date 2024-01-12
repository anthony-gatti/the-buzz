package edu.lehigh.cse216.rdm325.backend;

/**
 * SimpleRequestUser provides a format for clients to present User info 
 * strings to the server.
 * 
 * NB: since this will be created from JSON, all fields must be public, and we
 *     do not need a constructor.
 */
public class SimpleRequestUser {
    /**
     * The name being provided by the client.
     */
    public String uName;

    /**
     * The email being provided by the client.
     */
    public String uEmail;

    /**
     * The gender being provided by the client.
     */
    public String uGender;

    /**
     * The so being provided by the client.
     */
    public String uSO;

    /**
     * The username being provided by the client.
     */
    public String uUsername;

    /**
     * The note being provided by the client.
     */
    public String uNote;
}