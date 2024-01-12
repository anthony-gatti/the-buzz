package edu.lehigh.cse216.rdm325.admin;

/**
 * Data for a row of the Comments table
 */
public class CommentData {
    /**
     * The comment id of the Comment (primary key of Comments table)
     */
    int cId;
    /**
     * The comment (content) of the Comment
     */
    String cComment;
    /**
     * The message id of the Comment (foreign key referencing Messages table)
     */
    int cmId;
    /**
     * The user id of the Comment (foreign key referencing Users table)
     */
    int cuId;
    /**
     * The file url tied to the Comment
     */
    String cFile;
    /**
     * The url tied to the Comment
     */
    String cUrl;
    /**
     * The validity of the Comment
     */
    boolean cValid;
    /**
     * The validity of the Comment's file
     */
    boolean cValidFile;
    /**
     * The validity of the Comment's url
     */
    boolean cValidUrl;

    /**
     * Construct a CommentData Object by providing values for its data fields
     * 
     * @param id The comment id of the Comment
     * @param comment The comment (content) of the Comment
     * @param mId The message id of the Comment
     * @param uId The user id of the Comment
     * @param file The potential url of a file linked to a Comment
     * @param url The potential url linked to a Comment
     * @param valid A boolean that valididates a Comment to be seen on the app
     * @param validFile A boolean that valididates a Comment's file to be seen on the app
     * @param validUrl A boolean that valididates a Comment's url to be seen on the app
     */
    public CommentData(int id, String comment, int mId, int uId, String file, String url, boolean valid, boolean validFile, boolean validUrl){
        cId = id;
        cComment = comment;
        cmId = mId;
        cuId = uId;
        cFile = file;
        cUrl = url;
        cValid = valid;
        cValidFile = validFile;
        cValidUrl = validUrl;
    }
}