package edu.lehigh.cse216.rdm325.admin;

/**
 * Data for a row in the Messages table
 */
public class MessageData {
    /**
     * The message id (primary key) of this Message
     */
    int mId;
     /**
     * The subject stored in this message
     */
    String mSubject;
    /**
     * The message stored in this Message
     */
    String mMessage;
    /**
     * The userId tied to the Message
     */
    int muId;
    /**
     * The file url tied to the Message
     */
    String mFile;
    /**
     * The url tied to the Message
     */
    String mUrl;
    /**
     * The validity of the Message
     */
    boolean mValid;
    /**
     * The validity of the Message's file
     */
    boolean mValidFile;
    /**
     * The validity of the Message's url
     */
    boolean mValidUrl;
        

    /**
     * Construct a MessageData object by providing values for its data fields
     * 
     * @param id The id of the Message
     * @param subject The subject of the Message
     * @param message The message of the Message
     * @param uId The user id of the Message
     * @param file The potential url of a file linked to a Message
     * @param url The potential url linked to a Message
     * @param valid A boolean that valididates a Message to be seen on the app
     * @param validFile A boolean that valididates a Message's file to be seen on the app
     * @param validUrl A boolean that valididates a Message's url to be seen on the app
     */
    public MessageData(int id, String subject, String message, int uId, String file, String url, boolean valid, boolean validFile, boolean validUrl) {
        mId = id;
        mSubject = subject;
        mMessage = message;
        muId = uId;
        mFile = file;
        mUrl = url;
        mValid = valid;
        mValidFile = validFile;
        mValidUrl = validUrl;
    }
}