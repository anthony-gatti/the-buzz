package edu.lehigh.cse216.rdm325.backend;


import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import edu.lehigh.cse216.rdm325.backend.GetFiles;

public class Database {
    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all Messages in the database
     */
    private PreparedStatement mSelectAllMessages;

    /**
     * A prepared statement for getting all Comments for a Message in the database
     */
    private PreparedStatement cSelectAllComments;

    /**
     * A prepared statement for getting one Message row from the database
     */
    private PreparedStatement mSelectOneMessage;

    /**
     * A prepared statement for getting one Comment row from the database
     */
    private PreparedStatement cSelectOneComment;

    /**
     * A prepared statement for getting one File attribute from the comment table
     */
    private PreparedStatement cSelectOneFile;

    /**
     * A prepared statement for getting one User row from the database
     */
    private PreparedStatement uSelectOneUser;

    /**
     * A prepared statement for getting one User row from the database
     */
    private PreparedStatement uSelectOneUserOnEmail;

    /**
     * A prepared statement for getting one Upvote row from the database
     */
    private PreparedStatement mSelectOneUpvote;

    /**
     * A prepared statement for getting one Downvote row from the database
     */
    private PreparedStatement mSelectOneDownvote;

    /**
     * A prepared statement for getting the count of upvotes for a Message from the database
     */
    private PreparedStatement cGetCountComments;

    /**
     * A prepared statement for getting the count of upvotes for a Message from the database
     */
    private PreparedStatement mGetCountUpvotes;

    /**
     * A prepared statement for getting the count of downvotes for a Message from the database
     */
    private PreparedStatement mGetCountDownvotes;

    /**
     * A prepared statement for deleting an Upvote row from the database
     */
    private PreparedStatement mDeleteOneUpvote;

    /**
     * A prepared statement for deleting a Downvote row from the database
     */
    private PreparedStatement mDeleteOneDownvote;

    /**
     * A prepared statement for inserting Messages into the database
     */
    private PreparedStatement mInsertOneMessage;

    /**
     * A prepared statement for inserting Comments into the database
     */
    private PreparedStatement cInsertOneComment;

    /**
     * A prepared statement for inserting Users into the database
     */
    private PreparedStatement uInsertOneUser;

    /**
     * A prepared statement for inserting Upvotes into the database
     */
    private PreparedStatement mInsertOneUpvote;

    /**
     * A prepared statement for inserting Downvotes into the database
     */
    private PreparedStatement mInsertOneDownvote;

    /**
     * A prepared statement for updating a single Comment row in the database
     */
    private PreparedStatement cUpdateOneComment;

    /**
     * A prepared statement for updating a single Comment row in the database
     */
    private PreparedStatement cUpdateOneCommentURL;

    /**
     * A prepared statement for updating a single Comment row in the database
     */
    private PreparedStatement cUpdateOneCommentFile;

    /**
     * A prepared statement for updating a single User's name in the database
     */
    private PreparedStatement uUpdateOneName;
    
    /**
     * A prepared statement for updating a single User's email in the database
     */
    private PreparedStatement uUpdateOneEmail;

    /**
     * A prepared statement for updating a single User's gender in the database
     */
    private PreparedStatement uUpdateOneGender;

    /**
     * A prepared statement for updating a single User's SO in the database
     */
    private PreparedStatement uUpdateOneSO;

    /**
     * A prepared statement for updating a single User's username in the database
     */
    private PreparedStatement uUpdateOneUsername;

    /**
     * A prepared statement for updating a single note in the database
     */
    private PreparedStatement uUpdateOneNote;

    /**
     * A prepared statement for creating the table in our database
     */
    /**
    private PreparedStatement mCreateTable;
    */

    /**
     * A prepared statement for dropping the table in our database
     */
    /**
    private PreparedStatement mDropTable;
    */

    /**
     * A prepared statment for liking/unliking a message in our database
     */
    /**
    private PreparedStatement mAOrRLike;
    */

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

    /**
     * Initializes Prepared Statements
     * @return a database
     */
    private Database createPreparedStatements(){
        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblData"
            //     as constants, and then build the strings for the statements
            //     from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 
            // creation/deletion, so multiple executions will cause an exception

            // Standard CRUD operations
            this.mDeleteOneUpvote = this.mConnection.prepareStatement("DELETE FROM upvotes WHERE uvid = ?");
            this.mDeleteOneDownvote = this.mConnection.prepareStatement("DELETE FROM downvotes WHERE dvid = ?");

            this.mInsertOneMessage = this.mConnection.prepareStatement("INSERT INTO messages VALUES (DEFAULT, ?, ?, ?, ?, ?, true)");
            this.cInsertOneComment = this.mConnection.prepareStatement("INSERT INTO comments VALUES (DEFAULT, ?, ?, ?, ? , ?, true)");
            this.uInsertOneUser = this.mConnection.prepareStatement("INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)");
            this.mInsertOneUpvote = this.mConnection.prepareStatement("INSERT INTO upvotes VALUES (DEFAULT, ?, ?)");
            this.mInsertOneDownvote = this.mConnection.prepareStatement("INSERT INTO downvotes VALUES (DEFAULT, ?, ?)");

            this.mSelectAllMessages = this.mConnection.prepareStatement("SELECT * FROM messages");
            this.cSelectAllComments = this.mConnection.prepareStatement("SELECT * FROM comments WHERE mid=?");

            this.cGetCountComments = this.mConnection.prepareStatement("SELECT COUNT(*) FROM comments WHERE mid=?");

            this.mSelectOneMessage = this.mConnection.prepareStatement("SELECT * FROM messages WHERE mid = ?");
            this.cSelectOneComment = this.mConnection.prepareStatement("SELECT * FROM comments WHERE cid = ?");
            this.cSelectOneFile = this.mConnection.prepareStatement("SELECT file FROM comments where cid = ?");
            this.uSelectOneUser = this.mConnection.prepareStatement("SELECT * FROM users WHERE uid = ?");
            this.uSelectOneUserOnEmail = this.mConnection.prepareStatement("SELECT * FROM users WHERE email = ?");
            this.mSelectOneUpvote = this.mConnection.prepareStatement("SELECT * FROM upvotes WHERE mid = ? AND uid = ?");
            this.mSelectOneDownvote = this.mConnection.prepareStatement("SELECT * FROM downvotes WHERE mid = ? AND uid = ?");
            this.mGetCountUpvotes = this.mConnection.prepareStatement("SELECT COUNT(*) FROM upvotes WHERE mid = ?");
            this.mGetCountDownvotes = this.mConnection.prepareStatement("SELECT COUNT(*) FROM downvotes WHERE mid = ?");

            this.cUpdateOneComment = this.mConnection.prepareStatement("UPDATE comments SET content = ? WHERE cid = ?");
            this.cUpdateOneCommentURL = this.mConnection.prepareStatement("UPDATE comments SET url = ? WHERE cid = ?");
            this.cUpdateOneCommentFile = this.mConnection.prepareStatement("UPDATE comments SET file = ? WHERE cid = ?");

            this.uUpdateOneName = this.mConnection.prepareStatement("UPDATE users SET name = ? WHERE uid = ?");
            this.uUpdateOneEmail = this.mConnection.prepareStatement("UPDATE users SET email = ? WHERE uid = ?");
            this.uUpdateOneGender = this.mConnection.prepareStatement("UPDATE users SET gender = ? WHERE uid = ?");
            this.uUpdateOneSO = this.mConnection.prepareStatement("UPDATE users SET so = ? WHERE uid = ?");
            this.uUpdateOneUsername = this.mConnection.prepareStatement("UPDATE users SET username = ? WHERE uid = ?");
            this.uUpdateOneNote = this.mConnection.prepareStatement("UPDATE users SET note = ? WHERE uid = ?");
        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            this.disconnect();
            return null;
        }
        return this;
    }

    /**
     * Get a fully-configured connection to the database
     * 
     * @param host The IP address or hostname of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param path The path to use, can be null
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    public static Database getDatabase(String host, String port, String path, String user, String pass) {
        if( path==null || "".equals(path) ){
            path="/";
        }

        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            String dbUrl = "jdbc:postgresql://" + host + ':' + port + path;
            Connection conn = DriverManager.getConnection(dbUrl, user, pass);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        }

        db = db.createPreparedStatements();
        return db;
    } 

    /**
    * Get a fully-configured connection to the database
    * 
    * @param db_url The url to the database
    * @param port_default port to use if absent in db_url
    * 
    * @return A Database object, or null if we cannot connect properly
    */
    public static Database getDatabase(String db_url, String port_default) {
        try {
            URI dbUri = new URI(db_url);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String port = dbUri.getPort() == -1 ? port_default : Integer.toString(dbUri.getPort());

            return getDatabase(host, port, path, username, password);
        } catch (URISyntaxException s) {
            System.out.println("URI Syntax Error");
            return null;
        }
    }

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    /**
     * Insert a Message row into the database
     * 
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * @param uId The user Id associated with this new row
     * 
     * @return The number of rows that were inserted
     */
    Integer insertRowMessage(String subject, String message, int uId, String file, String url) {
        Integer count = 0;
        try {
            mInsertOneMessage.setString(1, subject);
            mInsertOneMessage.setString(2, message);
            mInsertOneMessage.setInt(3, uId);
            mInsertOneMessage.setString(4, file);
            mInsertOneMessage.setString(5, url);
            count += mInsertOneMessage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert a Comment row into the database
     * 
     * @param content The content for this new row
     * @param uId The user Id asscociated with this new row
     * @param mId The message Id asscociated with this new row
     * 
     * @return The number of rows that were inserted
     */
    Integer insertRowComment(String content, int uId, int mId, String file, String url) {
        Integer count = 0;
        try {
            cInsertOneComment.setString(1, content);
            cInsertOneComment.setInt(2, uId);
            cInsertOneComment.setInt(3, mId);
            cInsertOneComment.setString(4, file);
            cInsertOneComment.setString(5, url);
            count += cInsertOneComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert a User row into the database
     * 
     * @param name The name for this new row
     * @param email The email for this new row
     * @param gender The gender for this new row
     * @param so The so for this new row
     * @param username The username for this new row
     * @param note The note for this new row
     * 
     * @return The number of rows that were inserted
     */
    Integer insertRowUser(String name, String email, String gender, String so, String username, String note) {
        Integer count = 0;
        try {
            uInsertOneUser.setString(1, name);
            uInsertOneUser.setString(2, email);
            uInsertOneUser.setString(3, gender);
            uInsertOneUser.setString(4, so);
            uInsertOneUser.setString(5, username);
            uInsertOneUser.setString(6, note);
            count += uInsertOneUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Insert an upvote row into the database
     * 
     * @param mId The message Id asscociated with this new row
     * @param uId The user Id asscociated with this new row
     * 
     * @return The number of rows that were inserted
     */
    Integer insertRowUpvote(int mId, int uId) {
        Integer count = 0;
        try {
            mInsertOneUpvote.setInt(1, mId);
            mInsertOneUpvote.setInt(2, uId);
            count += mInsertOneUpvote.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

     /**
     * Insert a downvote row into the database
     * 
     * @param mId The message Id asscociated with this new row
     * @param uId The user Id asscociated with this new row
     * 
     * @return The number of rows that were inserted
     */
    Integer insertRowDownvote(int mId, int uId) {
        Integer count = 0;
        try {
            mInsertOneDownvote.setInt(1, mId);
            mInsertOneDownvote.setInt(2, uId);
            count += mInsertOneDownvote.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Query the database for a list of all Messages
     * 
     * @param uid The uid of the user requesting
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<DataRowMessage> selectAllMessages(int uid) {
        ArrayList<DataRowMessage> res = new ArrayList<DataRowMessage>();
        try {
            ResultSet rs = mSelectAllMessages.executeQuery();
            while (rs.next()) {
                mGetCountUpvotes.setInt(1, rs.getInt("mid"));
                mGetCountDownvotes.setInt(1, rs.getInt("mid"));
                uSelectOneUser.setInt(1, rs.getInt("uid"));
                ResultSet rs2 = mGetCountUpvotes.executeQuery();
                ResultSet rs3 = mGetCountDownvotes.executeQuery();
                ResultSet rs4 = uSelectOneUser.executeQuery();
                rs2.next();
                rs3.next();
                rs4.next();
                Integer isUpvote = selectOneUpvote(rs.getInt("mid"), uid);
                if(isUpvote != null && isUpvote != -1){
                    isUpvote = 1;
                } else {
                    isUpvote = 0;
                }
                Integer isDownvote = selectOneDownvote(rs.getInt("mid"), uid);
                if(isDownvote != null && isDownvote != -1){
                    isDownvote = 1;
                } else {
                    isDownvote = 0;
                }
                //https://stackoverflow.com/questions/3247067/how-do-i-check-that-a-java-string-is-not-all-whitespaces
                if (rs.getString("file").trim().length() > 0){
                    FileDTO file = new FileDTO();
                    file.fData = rs.getString("file");
                    res.add(new DataRowMessage(rs.getInt("mid"), rs.getString("subject"), rs.getString("message"), rs.getInt("uid"), rs4.getString("username"), 
                        rs2.getInt("count"), rs3.getInt("count"), isUpvote, isDownvote, file, rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl")));
                }
                else {
                    res.add(new DataRowMessage(rs.getInt("mid"), rs.getString("subject"), rs.getString("message"), rs.getInt("uid"), rs4.getString("username"), 
                        rs2.getInt("count"), rs3.getInt("count"), isUpvote, isDownvote, null, rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl")));
                }
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a list of all comments for a given message
     * 
     * @param mId The message Id asscociated with this group of comments
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<DataRowComment> selectAllComments(int mId) {
        ArrayList<DataRowComment> res = new ArrayList<DataRowComment>();
        try {
            cSelectAllComments.setInt(1, mId);
            ResultSet rs = cSelectAllComments.executeQuery();
            while (rs.next()) {
                if (rs.getString("file").trim().length() > 0){
                    FileDTO file = new FileDTO();
                    file.fData = rs.getString("file");
                    res.add(new DataRowComment(rs.getInt("cid"), rs.getString("content"), rs.getInt("uid"), rs.getInt("mid"), file, rs.getString("url"), rs.getBoolean("valid")));
                }
                else {
                    res.add(new DataRowComment(rs.getInt("cid"), rs.getString("content"), rs.getInt("uid"), rs.getInt("mid"), null, rs.getString("url"), rs.getBoolean("valid")));
                }
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query the database for a count of the list of all comments for a given message
     * 
     * @param mId The message Id asscociated with this group of comments
     * 
     * @return the count of comments for this post
     */
    Integer getCountComments(int mId) {
        Integer res = -1;
        try {
            cGetCountComments.setInt(1, mId);
            ResultSet rs = cGetCountComments.executeQuery();
            if (rs.next()) {
                res = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Get all data for a specific Message row, by ID
     * 
     * @param id The id of the Message row being requested
     * 
     * @param uid The uid of the user requesting
     * 
     * @return The data for the requested Message row, or null if the ID was invalid
     */
    DataRowMessage selectOneMessage(int id, int uid) {
        DataRowMessage res = null;
        try {
            mSelectOneMessage.setInt(1, id);
            mGetCountUpvotes.setInt(1, id);
            mGetCountDownvotes.setInt(1, id);
            ResultSet rs = mSelectOneMessage.executeQuery();
            ResultSet rs2 = mGetCountUpvotes.executeQuery();
            ResultSet rs3 = mGetCountDownvotes.executeQuery();       
            if (rs.next() && rs2.next() && rs3.next()) {
                uSelectOneUser.setInt(1, rs.getInt("uid"));
                ResultSet rs4 = uSelectOneUser.executeQuery();
                rs4.next();
                Integer isUpvote = selectOneUpvote(id, uid);
                if(isUpvote != null && isUpvote != -1){
                    isUpvote = 1;
                } else {
                    isUpvote = 0;
                }
                Integer isDownvote = selectOneDownvote(id, uid);
                if(isDownvote != null && isDownvote != -1){
                    isDownvote = 1;
                } else {
                    isDownvote = 0;
                }
                if (rs.getString("file").trim().length() > 0){
                    FileDTO file = new FileDTO();
                    file.fData =rs.getString("file");
                    res = new DataRowMessage(rs.getInt("mid"), rs.getString("subject"), rs.getString("message"), rs.getInt("uid"), 
                        rs4.getString("username"), rs2.getInt("count"), rs3.getInt("count"), isUpvote, isDownvote, file, 
                        rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl"));
                }
                else{
                    res = new DataRowMessage(rs.getInt("mid"), rs.getString("subject"), rs.getString("message"), rs.getInt("uid"), 
                        rs4.getString("username"), rs2.getInt("count"), rs3.getInt("count"), isUpvote, isDownvote, null, 
                        rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl"));
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    String selectOneFile(int cId){
        try{
            cSelectOneFile.setInt(1, cId);
            ResultSet rs = cSelectOneFile.executeQuery();
            if (rs.next()) {
                if (rs.getString("file").trim().length() > 0){
                    return rs.getString("file").trim();
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all data for a specific Comment row, by ID
     * 
     * @param id The id of the Comment row being requested
     * 
     * @return The data for the requested Comment row, or null if the ID was invalid
     */
    DataRowComment selectOneComment(int id) {
        DataRowComment res = null;
        try {
            cSelectOneComment.setInt(1, id);
            ResultSet rs = cSelectOneComment.executeQuery();
            if (rs.next()) {
                if (rs.getString("file").trim().length() > 0){
                    FileDTO file = new FileDTO();
                    file.fData=rs.getString("file");
                    res = new DataRowComment(rs.getInt("cid"), rs.getString("content"), rs.getInt("uid"), rs.getInt("mid"), file, rs.getString("url"), rs.getBoolean("valid"));
                }
                else {
                    res = new DataRowComment(rs.getInt("cid"), rs.getString("content"), rs.getInt("uid"), rs.getInt("mid"), null, rs.getString("url"), rs.getBoolean("valid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific User row, by ID
     * 
     * @param id The id of the User row being requested
     * 
     * @return The data for the requested User row, or null if the ID was invalid
     */
    DataRowUser selectOneUser(int id) {
        DataRowUser res = null;
        try {
            uSelectOneUser.setInt(1, id);
            ResultSet rs = uSelectOneUser.executeQuery();
            if (rs.next()) {
                res = new DataRowUser(rs.getInt("uid"), rs.getString("name"), rs.getString("email"), rs.getString("gender"), rs.getString("so"), rs.getString("username"), rs.getString("note"), rs.getBoolean("valid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get the uid specific User row, by email
     * 
     * @param email The email of the User row being requested
     * 
     * @return The uid for the requested User row, or null if the email was invalid
     */
    Integer selectOneUserId(String email){
        Integer res = null;
        try {
            uSelectOneUserOnEmail.setString(1, email);
            ResultSet rs = uSelectOneUserOnEmail.executeQuery();
            if (rs.next()) {
                res = rs.getInt("uid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific upvote, by ID
     * 
     * @param mId The mId of the Upvote row being requested
     * @param uId The uId of the Upvote row being requested
     * 
     * @return The data for the requested Upvote row, or null if the ID was invalid
     */
    Integer selectOneUpvote(int mId, int uId) {
        Integer res = -1;
        try {
            mSelectOneUpvote.setInt(1, mId);
            mSelectOneUpvote.setInt(2, uId);
            ResultSet rs = mSelectOneUpvote.executeQuery();
            if (rs.next()) {
                res = rs.getInt("uvid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

   /**
     * Get all data for a specific downvote, by ID
     * 
     * @param mId The mId of the Downvote row being requested
     * @param uId The uId of the UDownote row being requested
     * 
     * @return The data for the requested Downvote row, or null if the ID was invalid
     */
    Integer selectOneDownvote(int mId, int uId) {
        Integer res = -1;
        try {
            mSelectOneDownvote.setInt(1, mId);
            mSelectOneDownvote.setInt(2, uId);
            ResultSet rs = mSelectOneDownvote.executeQuery();
            if (rs.next()) {
                res = rs.getInt("dvid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete an upvote by ID
     * 
     * @param id The id of the Upvote row to delete
     * 
     * @return The number of Upvote rows that were deleted.  -1 indicates an error.
     */
    Integer deleteRowUpvote(int id) {
        Integer res = -1;
        try {
            mDeleteOneUpvote.setInt(1, id);
            res = mDeleteOneUpvote.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a downvote by ID
     * 
     * @param id The id of the Downvote row to delete
     * 
     * @return The number of Downvote rows that were deleted.  -1 indicates an error.
     */
    Integer deleteRowDownvote(int id) {
        Integer res = -1;
        try {
            mDeleteOneDownvote.setInt(1, id);
            res = mDeleteOneDownvote.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the content for a row in the comment database
     * 
     * @param id The id of the Comment row to update
     * @param content The new comment contents
     * 
     * @return The number of Comment rows that were updated.  -1 indicates an error.
     */
    Integer updateOneComment(int id, String content) {
        Integer res = -1;
        try {
            cUpdateOneComment.setInt(2, id);
            cUpdateOneComment.setString(1, content);
            res = cUpdateOneComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the URL for a row in the comment database
     * 
     * @param id The id of the Comment row to update
     * @param url The new comment url
     * 
     * @return The number of Comment rows that were updated.  -1 indicates an error.
     */
    Integer updateOneCommentURL(int id, String url) {
        Integer res = -1;
        try {
            cUpdateOneCommentURL.setInt(2, id);
            cUpdateOneCommentURL.setString(1, url);
            res = cUpdateOneCommentURL.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the URL for a row in the comment database
     * 
     * @param id The id of the Comment row to update
     * @param url The new comment url
     * 
     * @return The number of Comment rows that were updated.  -1 indicates an error.
     */
    Integer updateOneCommentFile(int id, String file) {
        Integer res = -1;
        try {
            cUpdateOneCommentFile.setInt(2, id);
            cUpdateOneCommentFile.setString(1, file);
            res = cUpdateOneCommentFile.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the name for a row in the user database
     * 
     * @param id The id of the User row to update
     * @param name The new User name
     * 
     * @return The number of User rows that were updated.  -1 indicates an error.
     */
    Integer updateOneName(int id, String name) {
        Integer res = -1;
        try {
            uUpdateOneName.setInt(2, id);
            uUpdateOneName.setString(1, name);
            res = uUpdateOneName.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

   /**
     * Update the email for a row in the user database
     * 
     * @param id The id of the User row to update
     * @param email The new User email
     * 
     * @return The number of User rows that were updated.  -1 indicates an error.
     */
    Integer updateOneEmail(int id, String email) {
        Integer res = -1;
        try {
            uUpdateOneEmail.setInt(2, id);
            uUpdateOneEmail.setString(1, email);
            res = uUpdateOneEmail.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the gender for a row in the user database
     * 
     * @param id The id of the User row to update
     * @param gender The new User gender
     * 
     * @return The number of User rows that were updated.  -1 indicates an error.
     */
    Integer updateOneGender(int id, String gender) {
        Integer res = -1;
        try {
            uUpdateOneGender.setInt(2, id);
            uUpdateOneGender.setString(1, gender);
            res = uUpdateOneGender.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the so for a row in the user database
     * 
     * @param id The id of the User row to update
     * @param so The new User so
     * 
     * @return The number of User rows that were updated.  -1 indicates an error.
     */
    Integer updateOneSO(int id, String so) {
        Integer res = -1;
        try {
            uUpdateOneSO.setInt(2, id);
            uUpdateOneSO.setString(1, so);
            res = uUpdateOneSO.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the username for a row in the user database
     * 
     * @param id The id of the User row to update
     * @param username The new User username
     * 
     * @return The number of User rows that were updated.  -1 indicates an error.
     */
    Integer updateOneUsername(int id, String username) {
        Integer res = -1;
        try {
            uUpdateOneUsername.setInt(2, id);
            uUpdateOneUsername.setString(1, username);
            res = uUpdateOneUsername.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the note for a row in the user database
     * 
     * @param id The id of the User row to update
     * @param note The new User note
     * 
     * @return The number of User rows that were updated.  -1 indicates an error.
     */
    Integer updateOneNote(int id, String note) {
        Integer res = -1;
        try {
            uUpdateOneNote.setInt(2, id);
            uUpdateOneNote.setString(1, note);
            res = uUpdateOneNote.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Add a like to the given message
     * 
     * @param id The id of the row to add a like to
     * @param likes The number of likes of the message
     * 
     * @return The number of rows updated (mostly just for error handling, -1 indicates error)
     */
    /**
    int addLike(int id, int likes) {
        int res = -1;
        try {
            mAOrRLike.setInt(2, id);
            mAOrRLike.setInt(1, likes + 1);
            res = mAOrRLike.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    } 
    */
    /**
     * Add a like to the given message
     * 
     * @param id The id of the row to add a like to
     * @param likes The number of likes of the message
     * 
     * @return The number of rows updated (mostly just for error handling, -1 indicates error)
     */
    /**
    int removeLike(int id, int likes) {
        int res = -1;
        try {
            mAOrRLike.setInt(2, id);
            mAOrRLike.setInt(1, likes - 1);
            res = mAOrRLike.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    } 
    */
    /**
     * Create tblData.  If it already exists, this will print an error
     */
    /* 
    void createTable() {
        try {
            mCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * Remove tblData from the database.  If it does not exist, this will print
     * an error.
     */
    /*
    void dropTable() {
        try {
            mDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
}