package edu.lehigh.cse216.rdm325.backend;

/**
 * StructuredResponseUser provides a format for success and failure messages for logging in.
 * 
 * NB: since this will be converted into JSON, all fields must be public.
 */
public class StructuredResponseUser {
    /**
     * The status is a string that the application can use to quickly determine
     * if the response indicates an error.  Values will probably just be "ok" or
     * "error", but that may evolve over time.
     */
    public String mStatus;

    /**
     * The message is only useful when this is an error, or when data is null.
     */
    public String mMessage;

    /**
     * The Session Token can be used by the client for later API access
     */
    public String mSessionToken;

    /**
     * The uid associated with the logged in User
     */
    public int uid;

    /**
     * Construct a StructuredResponseUser by providing a status, session token, and uid.
     * If the status is not provided, set it to "invalid".
     * 
     * @param status The status of the response, typically "ok" or "error"
     * @param message The message to go along with an error status
     * @param data the Session Token as a String
     * @param uid the uid of the user as an int
     */
    public StructuredResponseUser(String status, String message, String data, int uid) {
        mStatus = (status != null) ? status : "invalid";
        mMessage = message;
        mSessionToken = data;
        this.uid = uid;
    }
}