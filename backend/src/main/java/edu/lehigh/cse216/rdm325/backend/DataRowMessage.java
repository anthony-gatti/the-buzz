package edu.lehigh.cse216.rdm325.backend;

import java.util.Date;
import edu.lehigh.cse216.rdm325.backend.FileDTO;


/**
 * DataRow holds a row of information.  A row of information consists of
 * an identifier, strings for a "subject" and "message", and a creation date.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public.  That being the case, we will
 * not bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class DataRowMessage {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int mid;

    /**
     * The subject for this row of data
     */
    public String mSubject;

    /**
     * The message for this row of data
     */
    public String mMessage;

    /**
     * The uid for this row of data
     */
    public int uid;

    /**
     * The username of the poster for this row of data
     */
    public String uUsername;

    /**
     * The number of downvotes for this row of data
     */
    public int mUpvotes;

    /**
     * The number of downvotes for this row of data
     */
    public int mDownvotes;

    /**
     * The number indicating is the user has downvoted this row of data
     */
    public int mIsUpvote;

    /**
     * The number indicating is the user has upvoted this row of data
     */
    public int mIsDownvote;

    public FileDTO mFile;

    public String mURL;

    public Boolean valid;

    public Boolean validFile;

    public Boolean validURL;

    /**
     * The creation date for this row of data. Once it is set, it cannot be 
     * changed
     */
    public final Date mCreated;

    /**
     * Create a new DataRowMessage with the provided id, subject, message, uid, username, upvote count, downvote count, a 
     * creation date, etc based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param subject The subject string for this row of data
     * 
     * @param message The message string for this row of data
     * 
     * @param uid The uid int for this row of data
     * 
     * @param username The username asso. for this row of data
     * 
     * @param upvotes the upvote count
     * 
     * @param downvotes the downvote count
     * 
     * @param isUpvoted the upvote indicator per user
     * 
     * @param isDownvoted the downvote indicator per user
     */
    DataRowMessage(int id, String subject, String message, int uid, String username, int upvotes, int downvotes, 
            int isUpvoted, int isDownvoted, FileDTO mFile, String mURL, Boolean valid, Boolean validFile, Boolean validURL) {
        mid = id;
        this.mSubject = subject;
        this.mMessage = message;
        this.uid = uid;
        this.uUsername = username;
        this.mUpvotes = upvotes;
        this.mDownvotes = downvotes;
        this.mIsUpvote = isUpvoted;
        this.mIsDownvote = isDownvoted;
        this.mFile = mFile;
        this.mURL = mURL;
        this.valid = valid;
        this.validFile = validFile;
        this.validURL = validURL;
        mCreated = new Date();
    }

    /**
     * Copy constructor to create one datarow from another
     * 
     * @param data for a DataRowMessage
     */
    DataRowMessage(DataRowMessage data) {
        mid = data.mid;
        // NB: Strings and Dates are immutable, so copy-by-reference is safe
        mSubject = data.mSubject;
        mMessage = data.mMessage;
        uid = data.uid;
        uUsername = data.uUsername;
        mUpvotes = data.mUpvotes;
        mDownvotes = data.mDownvotes;
        mIsUpvote = data.mIsUpvote;
        mIsDownvote = data.mIsDownvote;
        mFile = data.mFile;
        mURL = data.mURL;
        mCreated = data.mCreated;
        valid = data.valid;
        validFile = data.validFile;
        validURL = data.validURL;
    }
}