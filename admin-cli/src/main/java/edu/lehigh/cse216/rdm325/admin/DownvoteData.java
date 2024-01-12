package edu.lehigh.cse216.rdm325.admin;

/**
 * Data for a row of the Downvotes table
 */
public class DownvoteData {
    /**
     * The downvote id of the Downvote (primary key of Downvotes table)
     */
    int mdvId;
    /**
     * The message id of the Downvote (foreign key referencing Messages table)
     */
    int mdvmId;
    /**
     * The user id of the Downvote (foreign key referencing Users table)
     */
    int mdvuId;

    /**
     * Construct an DownvoteData object by provding values for its data fields 
     * 
     * @param id The downvote id of the Downvote
     * @param mId The message id of the Downvote
     * @param uId The user id of the Downvote
     */
    public DownvoteData(int id, int mId, int uId){
        mdvId = id;
        mdvmId = mId;
        mdvuId = uId;
    }
}