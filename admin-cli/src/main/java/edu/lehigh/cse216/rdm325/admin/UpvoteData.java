package edu.lehigh.cse216.rdm325.admin;

/**
 * Data for a row of the Upvotes table
 */
public class UpvoteData {
    /**
     * The upvote id of the Upvote (primary key of Upvotes table)
     */
    int muvId;
    /**
     * The message id of the Upvote (foreign key referencing Messages table)
     */
    int muvmId;
    /**
     * The user id of the Upvote (foreign key referencing Users table)
     */
    int muvuId;

    /**
     * Construct an UpvoteData object by provding values for its data fields 
     * 
     * @param id The upvote id of the Upvote
     * @param mId The message id of the Upvote
     * @param uId The user id of the Upvote
     */
    public UpvoteData(int id, int mId, int uId){
        muvId = id;
        muvmId = mId;
        muvuId = uId;
    }
}