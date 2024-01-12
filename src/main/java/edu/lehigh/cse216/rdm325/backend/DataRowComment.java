package edu.lehigh.cse216.rdm325.backend;

import java.util.Date;

/**
 * DataRow holds a row of information.  A row of information consists of
 * an identifier, strings for a "subject" and "message", and a creation date.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public.  That being the case, we will
 * not bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class DataRowComment {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int cId;

    /**
     * The content for this row of data
     */
    public String cContent;

    /**
     * The user Id associated to this row of data
     */
    public final int uId;

    /**
     * The user mId associated to this row of data
     */
    public final int mId;

    public FileDTO cFile;

    public String cURL;
    
    public Boolean valid;

    /**
     * The creation date for this row of data.  Once it is set, it cannot be 
     * changed
     */
    public final Date cCreated;

    /**
     * Create a new DataRow with the provided id and subject/message, and a 
     * creation date based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param content The content string for this row of data
     * 
     * @param uId The uid int for this row of data
     * 
     * @param mId The likes mid for this row of data
     */
    DataRowComment(int id, String content, int uId, int mId, FileDTO cFile, String cURL, Boolean valid) {
        this.cId = id;
        this.cContent = content;
        this.uId = uId;
        this.mId = mId;
        this.cFile = cFile;
        this.cURL = cURL;
        this.valid = valid;
        cCreated = new Date();
    }

    /**
     * Copy constructor to create one datarow from another
     * @param data for a DataRowComment
     */
    DataRowComment(DataRowComment data) {
        cId = data.cId;
        // NB: Strings and Dates are immutable, so copy-by-reference is safe
        cContent = data.cContent;
        uId = data.uId;
        mId = data.mId;
        cFile = data.cFile;
        cURL = data.cURL;
        cCreated = data.cCreated;
        valid = data.valid;
    }
}