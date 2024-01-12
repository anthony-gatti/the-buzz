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
public class DataRowUser {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int uId;

    /**
     * The name for this row of data
     */
    public String uName;

    /**
     * The email associated to this row of data
     */
    public String uEmail;

    /**
     * The gender associated to this row of data
     */
    public String uGender;

    /**
     * The so associated to this row of data
     */
    public String uSO;

    /**
     * The username associated to this row of data
     */
    public String uUsername;

    /**
     * The note associated to this row of data
     */
    public String uNote;

    /**
     * The creation date for this row of data.  Once it is set, it cannot be 
     * changed
     */
    public final Date uCreated;

    public Boolean valid;

    /**
     * Create a new DataRowUser with the provided params, and a 
     * creation date based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param name The name string for this row of data
     * 
     * @param email The email string for this row of data
     * 
     * @param gender The gender string for this row of data
     * 
     * @param so The so string for this row of data
     * 
     * @param username The username string for this row of data
     * 
     * @param note The note string for this row of data
     */
    DataRowUser(int id, String name, String email, String gender, String so, String username, String note, Boolean valid) {
        uId = id;
        uName = name;
        uEmail = email;
        uGender = gender;
        uSO = so;
        uUsername = username;
        uNote = note;
        this.valid = valid;
        uCreated = new Date();
    }

    /**
     * Copy constructor to create one datarow from another
     * 
     * @param data for a DataRowUser
     */
    DataRowUser(DataRowUser data) {
        uId = data.uId;
        // NB: Strings and Dates are immutable, so copy-by-reference is safe
        uName = data.uName;
        uEmail = data.uEmail;
        uGender = data.uGender;
        uSO = data.uSO;
        uUsername = data.uUsername;
        uNote = data.uNote;
        uCreated = data.uCreated;
        valid = data.valid;
    }
}