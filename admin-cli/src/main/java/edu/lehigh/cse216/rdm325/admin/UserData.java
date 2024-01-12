package edu.lehigh.cse216.rdm325.admin;

/**
 * Data for a row of the Users table
 */
public class UserData {
    /**
     * The user id (primary key of Users table)
     */
    int uId;
    /**
     * The name of the User 
     */
    String uName;
    /**
     * The email of the User
     */
    String uEmail;
    /**
     * The gender of the User
     */
    String uGender;
    /**
     * The sexual orientation of the User
     */
    String uSO;
    /**
     * The username of the User
     */
    String uUsername;
    /**
     * A note about the User
     */
    String uNote;
    /**
     * The file url tied to the User
     */
    boolean uValid;

    /**
     * Construct a UserData object by providing values for its data fields
     * 
     * @param id The id of the User
     * @param name The name of the User
     * @param email The email of the User
     * @param gender The gender of the User
     * @param SO The sexual orientation of the User
     * @param username The username of the User
     * @param note A note about the User
     * @param valid A boolean that valididates a User to be seen on the app
     */
    public UserData(int id, String name, String email, String gender, String SO, String username, String note, boolean valid){
        uId = id;
        uName = name;
        uEmail = email;
        uGender = gender;
        uSO = SO;
        uUsername = username;
        uNote = note;
        uValid = valid;
    }
}