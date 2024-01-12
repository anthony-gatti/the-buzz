package edu.lehigh.cse216.rdm325.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * class for all db operations
 */
public class Database {
    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    // PREPARED STATEMENTS FOR MESSAGES TABLE
    /**
     * A prepared statement for creating the messages table in our database
     */
    private PreparedStatement mCreateMessagesTable;
    /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropMessagesTable;
    /**
     * A prepared statement for inserting a message into the database
     */
    private PreparedStatement mInsertOneMessage;
    /**
     * A prepared statement for getting all messages in the database
     */
    private PreparedStatement mSelectAllMessages;
    /**
     * A prepared statement for getting all messages in the database that are valid
     */
    private PreparedStatement mSelectAllValidMessages;
    /**
     * A prepared statement for getting all messages in the database that are invalid
     */
    private PreparedStatement mSelectAllInvalidMessages;
    /**
     * A prepared statement for getting one message from the database
     */
    private PreparedStatement mSelectOneMessage;
    /**
     * A prepared statement for updating a single message in the database
     */
    private PreparedStatement mUpdateOneMessage;
    /**
     * A prepared statement for updating valid to true of a single message in the database
     */
    private PreparedStatement mUpdateOneValidMessage;
    /**
     * A prepared statement for updating valid to false of a single message in the database
     */
    private PreparedStatement mUpdateOneInvalidMessage;
    /**
     * A prepared statement for updating valid to true of a single message in the database
     */
    private PreparedStatement mUpdateOneValidMessageFile;
    /**
     * A prepared statement for updating valid to false of a single message in the database
     */
    private PreparedStatement mUpdateOneInvalidMessageFile;
    /**
     * A prepared statement for updating valid to true of a single message in the database
     */
    private PreparedStatement mUpdateOneValidMessageUrl;
    /**
     * A prepared statement for updating valid to false of a single message in the database
     */
    private PreparedStatement mUpdateOneInvalidMessageUrl;
    /**
     * A prepared statement for deleting a message from the database
     */
    private PreparedStatement mDeleteOneMessage;
    

    // PREPARED STATEMENTS FOR USERS TABLE
    /**
     * A prepared statement for creating the users table in our database
     */
    private PreparedStatement mCreateUsersTable;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mDropUsersTable;
    /**
     * A prepared statement for inserting a user into the database
     */
    private PreparedStatement mInsertOneUser;
    /**
     * A prepared statement for selecting all users from the our database
     */
    private PreparedStatement mSelectAllUsers;
    /**
     * A prepared statement for selecting all users from the our database that are valid
     */
    private PreparedStatement mSelectAllValidUsers;
    /**
     * A prepared statement for selecting all users from the our database that are invalid
     */
    private PreparedStatement mSelectAllInvalidUsers;
    /**
     * A prepared statement for selecting a user from the database
     */
    private PreparedStatement mSelectOneUser;
    /**
     * A prepared statement for updating a user in the database
     */
    private PreparedStatement mUpdateOneUser;
    /**
     * A prepared statement for updating valid to true of a single user in the database
     */
    private PreparedStatement mUpdateOneValidUser;
    /**
     * A prepared statement for updating valid to false of a single user in the database
     */
    private PreparedStatement mUpdateOneInvalidUser;
    /**
     * A prepared statement for deleting a user in the database
     */
    private PreparedStatement mDeleteOneUser;


    // PREPARED STATEMENTS FOR COMMENTS TABLE
    /**
     * A prepared statement for creating the comments table in our database
     */
    private PreparedStatement mCreateCommentsTable;
    /**
     * A prepared statement for dropping the comments table in our database
     */
    private PreparedStatement mDropCommentsTable;
    /**
     * A prepared statement for inserting a comment into the database
     */
    private PreparedStatement mInsertOneComment;
    /**
     * A prepared statement for inserting a comment into the database
     */
    private PreparedStatement mSelectAllComments;
    /**
     * A prepared statement for inserting a comment into the database that are valid
     */
    private PreparedStatement mSelectAllValidComments;
    /**
     * A prepared statement for inserting a comment into the database that are invalid
     */
    private PreparedStatement mSelectAllInvalidComments;
    /**
     * A prepared statement for inserting a comment into the database
     */
    private PreparedStatement mSelectOneComment;
    /**
     * A prepared statement for inserting a comment into the database
     */
    private PreparedStatement mUpdateOneComment;
    /**
     * A prepared statement for updating valid to true of a single comment in the database
     */
    private PreparedStatement mUpdateOneValidComment;
    /**
     * A prepared statement for updating valid to false of a single comment in the database
     */
    private PreparedStatement mUpdateOneInvalidComment;
    /**
     * A prepared statement for updating valid to true of a single comment in the database
     */
    private PreparedStatement mUpdateOneValidCommentFile;
    /**
     * A prepared statement for updating valid to false of a single comment in the database
     */
    private PreparedStatement mUpdateOneInvalidCommentFile;
    /**
     * A prepared statement for updating valid to true of a single comment in the database
     */
    private PreparedStatement mUpdateOneValidCommentUrl;
    /**
     * A prepared statement for updating valid to false of a single comment in the database
     */
    private PreparedStatement mUpdateOneInvalidCommentUrl;
    /**
     * A prepared statement for inserting a comment into the database
     */
    private PreparedStatement mDeleteOneComment;


    // PREPARED STATEMENTS FOR UPVOTES TABLE
    /**
     * A prepared statement for creating the upvotes table in our database
     */
    private PreparedStatement mCreateUpvotesTable;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mDropUpvotesTable;
    /**
     * A prepared statement for inserting an upvote into the database
     */
    private PreparedStatement mInsertOneUpvote;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mSelectAllUpvotes;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mSelectOneUpvote;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mUpdateOneUpvote;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mDeleteOneUpvote;
    
    
    // PREPARED STATEMENTS FOR DOWNVOTES TABLE
    /**
     * A prepared statement for creating the downvotes table in our database
     */
    private PreparedStatement mCreateDownvotesTable;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mDropDownvotesTable;
    /**
     * A prepared statement for inserting a downvote into the database
     */
    private PreparedStatement mInsertOneDownvote;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mSelectAllDownvotes;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mSelectOneDownvote;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mUpdateOneDownvote;
    /**
     * A prepared statement for dropping the users table in our database
     */
    private PreparedStatement mDeleteOneDownvote;

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

    /**
     * Get a fully-configured connection to the database
     * 
     * @param ip   The IP address of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String ip, String port, String user, String pass) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/", user, pass);
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

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblData"
            //     as constants, and then build the strings for the statements
            //     from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 

            // creation/deletion of messages table, table 1
            db.mCreateMessagesTable = db.mConnection.prepareStatement("CREATE TABLE messages (mId SERIAL, subject VARCHAR(50) NOT NULL, message VARCHAR(2048) NOT NULL, uId INT NOT NULL, PRIMARY KEY (mId), FOREIGN KEY(uId) REFERENCES users (uId), file VARCHAR(65535) DEFAULT '' NOT NULL, url VARCHAR(65535) DEFAULT '' NOT NULL, valid BOOLEAN DEFAULT TRUE, validFile BOOLEAN DEFAULT TRUE, validUrl BOOLEAN DEFAULT TRUE)");
            db.mDropMessagesTable = db.mConnection.prepareStatement("DROP TABLE messages");

            // Standard CRUD operations for Messages
            db.mInsertOneMessage = db.mConnection.prepareStatement("INSERT INTO messages VALUES (default, ?, ?, ?, ?, ?)");
            db.mSelectAllMessages = db.mConnection.prepareStatement("SELECT mId, subject, message, uId, file, url, valid, validfile, validurl FROM messages");
            db.mSelectAllValidMessages = db.mConnection.prepareStatement("SELECT mId, subject, message, uId, file, url, valid, validfile, validurl FROM messages WHERE valid = TRUE");
            db.mSelectAllInvalidMessages = db.mConnection.prepareStatement("SELECT mId, subject, message, uId, file, url, valid, validfile, validurl FROM messages WHERE valid = FALSE");
            db.mSelectOneMessage = db.mConnection.prepareStatement("SELECT * from messages WHERE mId=?");
            db.mUpdateOneMessage = db.mConnection.prepareStatement("UPDATE messages SET subject = ?, message = ?, uId = ?, file = ?, url = ? WHERE mId = ?");
            db.mUpdateOneValidMessage = db.mConnection.prepareStatement("UPDATE messages SET valid = TRUE WHERE mId = ?");
            db.mUpdateOneInvalidMessage = db.mConnection.prepareStatement("UPDATE messages SET valid = FALSE WHERE mId = ?");
            db.mUpdateOneValidMessageFile = db.mConnection.prepareStatement("UPDATE messages SET validFile = TRUE WHERE mId = ?");
            db.mUpdateOneInvalidMessageFile = db.mConnection.prepareStatement("UPDATE messages SET validFile = FALSE WHERE mId = ?");
            db.mUpdateOneValidMessageUrl = db.mConnection.prepareStatement("UPDATE messages SET validUrl = TRUE WHERE mId = ?");
            db.mUpdateOneInvalidMessageUrl = db.mConnection.prepareStatement("UPDATE messages SET validUrl = FALSE WHERE mId = ?");
            db.mDeleteOneMessage = db.mConnection.prepareStatement("DELETE FROM messages WHERE mId = ?");
            
            // creation/deletion of user table, table 2
            db.mCreateUsersTable = db.mConnection.prepareStatement("CREATE TABLE users (uId SERIAL, name VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, gender VARCHAR(50) NOT NULL, SO VARCHAR(50) NOT NULL, username VARCHAR(50) NOT NULL, note VARCHAR(50) NOT NULL, PRIMARY KEY (uId), valid BOOLEAN DEFAULT TRUE)");
            db.mDropUsersTable = db.mConnection.prepareStatement("DROP TABLE users");

            // Standard CRUD operations for Users
            db.mInsertOneUser = db.mConnection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?, ?, ?, ?)");
            db.mSelectAllUsers = db.mConnection.prepareStatement("SELECT uId, name, email, gender, SO, username, note, valid FROM users");
            db.mSelectAllValidUsers = db.mConnection.prepareStatement("SELECT uId, name, email, gender, SO, username, note, valid FROM users WHERE valid = TRUE");
            db.mSelectAllInvalidUsers = db.mConnection.prepareStatement("SELECT uId, name, email, gender, SO, username, note, valid FROM users WHERE valid = FALSE");
            db.mSelectOneUser = db.mConnection.prepareStatement("SELECT * from users WHERE uId=?");
            db.mUpdateOneUser = db.mConnection.prepareStatement("UPDATE users SET name = ?, email = ?, gender = ?, SO = ?, username = ?, note = ?  WHERE uId = ?");
            db.mUpdateOneValidUser = db.mConnection.prepareStatement("UPDATE users SET valid = TRUE WHERE uId = ?");
            db.mUpdateOneInvalidUser = db.mConnection.prepareStatement("UPDATE users SET valid = FALSE WHERE uId = ?");
            db.mDeleteOneUser = db.mConnection.prepareStatement("DELETE FROM users WHERE uId = ?");

            // creation/deletion of comments table, table 3
            db.mCreateCommentsTable = db.mConnection.prepareStatement("CREATE TABLE comments (cId SERIAL, content VARCHAR(2048) NOT NULL, uId INT NOT NULL, mId INT NOT NULL, PRIMARY KEY (cId), FOREIGN KEY (uId) REFERENCES users (uId), FOREIGN KEY (mId) REFERENCES messages (mId), file VARCHAR(65535) DEFAULT '' NOT NULL, url VARCHAR(65535) DEFAULT '' NOT NULL, valid BOOLEAN DEFAULT TRUE, validFile BOOLEAN DEFAULT TRUE, validUrl BOOLEAN DEFAULT TRUE)");
            db.mDropCommentsTable = db.mConnection.prepareStatement("DROP TABLE comments");

            // Standard CRUD operations for Comments
            db.mInsertOneComment = db.mConnection.prepareStatement("INSERT INTO comments VALUES (default, ?, ?, ?, ?, ?)");
            db.mSelectAllComments = db.mConnection.prepareStatement("SELECT cId, content, uId, mId, file, url, valid, validfile, validurl FROM comments");
            db.mSelectAllValidComments = db.mConnection.prepareStatement("SELECT cId, content, uId, mId, file, url, valid, validfile, validurl FROM comments WHERE valid = TRUE");
            db.mSelectAllInvalidComments = db.mConnection.prepareStatement("SELECT cId, content, uId, mId, file, url, valid, validfile, validurl FROM comments WHERE valid = FALSE");
            db.mSelectOneComment = db.mConnection.prepareStatement("SELECT * from comments WHERE cId=?");
            db.mUpdateOneComment = db.mConnection.prepareStatement("UPDATE comments SET content = ?, uId = ?, mId = ?, file = ?, url = ? WHERE cId = ?");
            db.mUpdateOneValidComment = db.mConnection.prepareStatement("UPDATE comments SET valid = TRUE WHERE cId = ?");
            db.mUpdateOneInvalidComment = db.mConnection.prepareStatement("UPDATE comments SET valid = FALSE WHERE cId = ?");
            db.mUpdateOneValidCommentFile = db.mConnection.prepareStatement("UPDATE comments SET validFile = TRUE WHERE cId = ?");
            db.mUpdateOneInvalidCommentFile = db.mConnection.prepareStatement("UPDATE comments SET validFile = FALSE WHERE cId = ?");
            db.mUpdateOneValidCommentUrl = db.mConnection.prepareStatement("UPDATE comments SET validUrl = TRUE WHERE cId = ?");
            db.mUpdateOneInvalidCommentUrl = db.mConnection.prepareStatement("UPDATE comments SET validUrl = FALSE WHERE cId = ?");
            db.mDeleteOneComment = db.mConnection.prepareStatement("DELETE FROM comments WHERE uId = ?");

            // creation/deletion of upvotes table, table 4
            db.mCreateUpvotesTable = db.mConnection.prepareStatement("CREATE TABLE upvotes (uvId SERIAL, mId INT NOT NULL, uId INT NOT NULL, PRIMARY KEY (uvId), FOREIGN KEY (mId) REFERENCES messages (mId), FOREIGN KEY (uId) REFERENCES users (uId))");
            db.mDropUpvotesTable = db.mConnection.prepareStatement("DROP TABLE upvotes");

            // Standard CRUD operations for Upvotes
            db.mInsertOneUpvote = db.mConnection.prepareStatement("INSERT INTO upvotes VALUES (default, ?, ?)");
            db.mSelectAllUpvotes = db.mConnection.prepareStatement("SELECT uvId, mId, uId FROM upvotes");
            db.mSelectOneUpvote = db.mConnection.prepareStatement("SELECT * from upvotes WHERE uvId=?");
            db.mUpdateOneUpvote = db.mConnection.prepareStatement("UPDATE upvotes SET mId = ?, uId = ? WHERE uvId = ?");
            db.mDeleteOneUpvote = db.mConnection.prepareStatement("DELETE FROM upvotes WHERE uId = ?");

            // creation/deletion of downvotes table, table 5
            db.mCreateDownvotesTable = db.mConnection.prepareStatement("CREATE TABLE downvotes (dvId SERIAL, mId INT NOT NULL, uId INT NOT NULL, PRIMARY KEY (dvId), FOREIGN KEY (mId) REFERENCES messages (mId), FOREIGN KEY (uId) REFERENCES users (uId))");
            db.mDropDownvotesTable = db.mConnection.prepareStatement("DROP TABLE downvotes");

            // Standard CRUD operations for Downvotes
            db.mInsertOneDownvote = db.mConnection.prepareStatement("INSERT INTO downvotes VALUES (default, ?, ?)");
            db.mSelectAllDownvotes = db.mConnection.prepareStatement("SELECT dvId, mId, uId FROM downvotes");
            db.mSelectOneDownvote = db.mConnection.prepareStatement("SELECT * from downvotes WHERE dvId=?");
            db.mUpdateOneDownvote = db.mConnection.prepareStatement("UPDATE downvotes SET mId = ?, uId = ? WHERE dvId = ?");
            db.mDeleteOneDownvote = db.mConnection.prepareStatement("DELETE FROM downvotes WHERE uId = ?");

        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
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
     * Method to insert a new message into the database
     * 
     * @param subject The subject for the new Message
     * @param message The message body for the Message
     * @param uId The user id of the new Message
     * @param file The file url of the Message
     * @param url The url of the Message
     * 
     * @return The number of rows that were inserted (1)
     */
    int insertMessage(String subject, String message, int uId, String file, String url) {
        int res = -1;
            try {
                mInsertOneMessage.setString(1, subject);
                mInsertOneMessage.setString(2, message);
                mInsertOneMessage.setInt(3, uId);
                mInsertOneMessage.setString(4, file);
                mInsertOneMessage.setString(5, url);
                res = mInsertOneMessage.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to insert a new User into the database
     * 
     * @param name The name of the new User
     * @param email The email of the new User
     * @param gender The gender of the new User
     * @param SO The sexual orientation of the new User
     * @param username The username of the new User
     * @param note A note about the new User
     * 
     * @return The number of rows that were inserted (1)
     */
    int insertUser(String name, String email, String gender, String SO, String username, String note) {
        int res = -1;
            try {
                mInsertOneUser.setString(1, name);
                mInsertOneUser.setString(2, email);
                mInsertOneUser.setString(3, gender);
                mInsertOneUser.setString(4, SO);
                mInsertOneUser.setString(5, username);
                mInsertOneUser.setString(6, note);
                res = mInsertOneUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to insert a new Comment into the database
     * 
     * @param content The content of the Comment
     * @param uId The user id of the Comment
     * @param mId The message id of the Comment
     * @param file The file url of the Comment
     * @param url The url of the Comment
     * 
     * @return The number of rows that were inserted (1)
     */
    int insertComment(String content, int uId, int mId, String file, String url) {
        int res = -1;
            try {
                mInsertOneComment.setString(1, content);
                mInsertOneComment.setInt(2, uId);
                mInsertOneComment.setInt(3, mId);
                mInsertOneComment.setString(4, file);
                mInsertOneComment.setString(5, url);
                res = mInsertOneComment.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to insert a new Upvote into the database
     * 
     * @param mId The message id of the Upvote
     * @param uId The user id of the Upvote
     * 
     * @return The number of rows that were inserted (1)
     */
    int insertUpvote(int mId, int uId) {
        int res = -1;
            try {
                mInsertOneUpvote.setInt(1, mId);
                mInsertOneUpvote.setInt(2, uId);
                res = mInsertOneUpvote.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to insert a new Downvote into the database
     * 
     * @param mId The message id of the Downvote
     * @param uId The user id of the Downvote
     * 
     * @return The number of rows that were inserted (1)
     */
    int insertDownvote(int mId, int uId) {
        int res = -1;
            try {
                mInsertOneDownvote.setInt(1, mId);
                mInsertOneDownvote.setInt(2, uId);
                res = mInsertOneDownvote.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Query the database for a list of all Messages 
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<MessageData> selectAllMessages() {
        ArrayList<MessageData> res = new ArrayList<MessageData>();
            try {
                ResultSet rs = mSelectAllMessages.executeQuery();
                while (rs.next()) {
                    res.add(new MessageData(rs.getInt("mId"), rs.getString("subject"), rs.getString("message"), rs.getInt("uId"), rs.getString("file"), rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all Messages that are valid
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<MessageData> selectAllValidMessages() {
        ArrayList<MessageData> res = new ArrayList<MessageData>();
            try {
                ResultSet rs = mSelectAllValidMessages.executeQuery();
                while (rs.next()) {
                    res.add(new MessageData(rs.getInt("mId"), rs.getString("subject"), rs.getString("message"), rs.getInt("uId"), rs.getString("file"), rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all Messages that are invalid
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<MessageData> selectAllInvalidMessages() {
        ArrayList<MessageData> res = new ArrayList<MessageData>();
            try {
                ResultSet rs = mSelectAllInvalidMessages.executeQuery();
                while (rs.next()) {
                    res.add(new MessageData(rs.getInt("mId"), rs.getString("subject"), rs.getString("message"), rs.getInt("uId"), rs.getString("file"), rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all Users
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<UserData> selectAllUsers() {
        ArrayList<UserData> res = new ArrayList<UserData>();
            try {
                ResultSet rs = mSelectAllUsers.executeQuery();
                while (rs.next()) {
                    res.add(new UserData(rs.getInt("uId"), rs.getString("name"), rs.getString("email"), rs.getString("gender"), rs.getString("SO"), rs.getString("username"), rs.getString("note"), rs.getBoolean("valid")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all valid Users
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<UserData> selectAllValidUsers() {
        ArrayList<UserData> res = new ArrayList<UserData>();
            try {
                ResultSet rs = mSelectAllValidUsers.executeQuery();
                while (rs.next()) {
                    res.add(new UserData(rs.getInt("uId"), rs.getString("name"), rs.getString("email"), rs.getString("gender"), rs.getString("SO"), rs.getString("username"), rs.getString("note"), rs.getBoolean("valid")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all invalid Users
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<UserData> selectAllInvalidUsers() {
        ArrayList<UserData> res = new ArrayList<UserData>();
            try {
                ResultSet rs = mSelectAllInvalidUsers.executeQuery();
                while (rs.next()) {
                    res.add(new UserData(rs.getInt("uId"), rs.getString("name"), rs.getString("email"), rs.getString("gender"), rs.getString("SO"), rs.getString("username"), rs.getString("note"), rs.getBoolean("valid")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all Comments
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<CommentData> selectAllComments() {
        ArrayList<CommentData> res = new ArrayList<CommentData>();
            try {
                ResultSet rs = mSelectAllComments.executeQuery();
                while (rs.next()) {
                    res.add(new CommentData(rs.getInt("cId"), rs.getString("content"), rs.getInt("uId"), rs.getInt("mId"), rs.getString("file"), rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all valid Comments
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<CommentData> selectAllValidComments() {
        ArrayList<CommentData> res = new ArrayList<CommentData>();
            try {
                ResultSet rs = mSelectAllValidComments.executeQuery();
                while (rs.next()) {
                    res.add(new CommentData(rs.getInt("cId"), rs.getString("content"), rs.getInt("uId"), rs.getInt("mId"), rs.getString("file"), rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all invalid Comments
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<CommentData> selectAllInvalidComments() {
        ArrayList<CommentData> res = new ArrayList<CommentData>();
            try {
                ResultSet rs = mSelectAllInvalidComments.executeQuery();
                while (rs.next()) {
                    res.add(new CommentData(rs.getInt("cId"), rs.getString("content"), rs.getInt("uId"), rs.getInt("mId"), rs.getString("file"), rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validfile"), rs.getBoolean("validurl")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all Upvotes
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<UpvoteData> selectAllUpvotes() {
        ArrayList<UpvoteData> res = new ArrayList<UpvoteData>();
            try {
                ResultSet rs = mSelectAllUpvotes.executeQuery();
                while (rs.next()) {
                    res.add(new UpvoteData(rs.getInt("uvId"), rs.getInt("mId"), rs.getInt("uId")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Query the database for a list of all Downvotes
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<DownvoteData> selectAllDownvotes() {
        ArrayList<DownvoteData> res = new ArrayList<DownvoteData>();
            try {
                ResultSet rs = mSelectAllDownvotes.executeQuery();
                while (rs.next()) {
                    res.add(new DownvoteData(rs.getInt("dvId"), rs.getInt("mId"), rs.getInt("uId")));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }


    /**
     * Get all data for a specific Message, by ID
     *
     * @param mId The id of the message being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    MessageData selectOneMessage(int mId) {
        MessageData res = null;
            try {
                mSelectOneMessage.setInt(1, mId);
                ResultSet rs = mSelectOneMessage.executeQuery();
                if (rs.next()) {
                    res = new MessageData(rs.getInt("mId"), rs.getString("subject"), rs.getString("message"), rs.getInt("uId"), rs.getString("file"), rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validFile"), rs.getBoolean("validUrl"));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return res;
            }
    }

    /**
     * Get all data for a specific User, by ID
     * 
     * @param uId The id of the User being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    UserData selectOneUser(int uId) {
        UserData res = null;
            try {
                mSelectOneUser.setInt(1, uId);
                ResultSet rs = mSelectOneUser.executeQuery();
                if (rs.next()) {
                    res = new UserData(rs.getInt("uId"), rs.getString("name"), rs.getString("email"), rs.getString("gender"), rs.getString("SO"), rs.getString("username"), rs.getString("note"), rs.getBoolean("valid"));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Get all data for a specific Comment, by ID
     * 
     * @param cId The id of the Comment being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    CommentData selectOneComment(int cId) {
        CommentData res = null;
            try {
                mSelectOneComment.setInt(1, cId);
                ResultSet rs = mSelectOneComment.executeQuery();
                if (rs.next()) {
                    res = new CommentData(rs.getInt("cId"), rs.getString("content"), rs.getInt("uId"), rs.getInt("mId"), rs.getString("file"), rs.getString("url"), rs.getBoolean("valid"), rs.getBoolean("validFile"), rs.getBoolean("validUrl"));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Get all data for a specific Upvote, by ID
     * 
     * @param uvId The id of the Upvote being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    UpvoteData selectOneUpvote(int uvId) {
        UpvoteData res = null;
            try {
                mSelectOneUpvote.setInt(1, uvId);
                ResultSet rs = mSelectOneUpvote.executeQuery();
                if (rs.next()) {
                    res = new UpvoteData(rs.getInt("uvId"), rs.getInt("mId"), rs.getInt("uId"));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Get all data for a specific Downvote, by ID
     * 
     * @param dvId The id of the Downvote being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DownvoteData selectOneDownvote(int dvId) {
        DownvoteData res = null;
            try {
                mSelectOneDownvote.setInt(1, dvId);
                ResultSet rs = mSelectOneDownvote.executeQuery();
                if (rs.next()) {
                    res = new DownvoteData(rs.getInt("dvId"), rs.getInt("mId"), rs.getInt("uId"));
                }
                rs.close();
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * Delete a row by ID
     * 
     * @param table Which table to query
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteRow(String table, int id) {
        int res = -1;
        try {
            int choice;
            choice = Integer.parseInt(table);
            switch (choice) {
                case 1: // messages table, table 1
                    mDeleteOneMessage.setInt(1, id);
                    res = mDeleteOneMessage.executeUpdate();
                    return res;
                case 2: // users table, table 2
                    mDeleteOneUser.setInt(1, id);
                    res = mDeleteOneUser.executeUpdate();
                    return res;
                case 3: // comments table, table 3
                    mDeleteOneComment.setInt(1, id);
                    res = mDeleteOneComment.executeUpdate();
                    return res;
                case 4: // upvotes table, table 4
                    mDeleteOneUpvote.setInt(1, id);
                    res = mDeleteOneUpvote.executeUpdate();
                    return res;
                case 5: // downvotes table, table 5
                    mDeleteOneDownvote.setInt(1, id);
                    res = mDeleteOneDownvote.executeUpdate();
                    return res;
        
                default: 
                    System.err.println("Invalid table\n");
                    return res;
        }
    } catch (SQLException e) {
            e.printStackTrace();
            return res;
        }
    }


    /**
     * Method to update a message in the database
     * 
     * @param mId Which Message to update
     * @param subject The new subject for the new Message
     * @param message The new message body for the Message
     * @param uId The new user id of the message (shouldn't change, but here for Admin purposes)
     * @param file The new file url of the message
     * @param url The new url of the message
     * 
     * @return The number of rows updated (1)
     */
    int updateMessage(int mId, String subject, String message, int uId, String file, String url) {
        int res = -1;
            try {
                mUpdateOneMessage.setInt(6, mId);
                mUpdateOneMessage.setString(1, subject);
                mUpdateOneMessage.setString(2, message);
                mUpdateOneMessage.setInt(3, uId);
                mUpdateOneMessage.setString(4, file);
                mUpdateOneMessage.setString(5, url);
                res = mUpdateOneMessage.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to validate a Message
     * @param mId Which Message to validate
     * @return The number of rows updated (1)
     */
    int updateOneValidMessage(int mId){
        int res = -1;
            try {
                mUpdateOneValidMessage.setInt(1, mId);
                res = mUpdateOneValidMessage.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to invalidate a Message
     * @param mId Which Message to invalidate
     * @return The number of rows updated (1)
     */
    int updateOneInvalidMessage(int mId){
        int res = -1;
            try {
                mUpdateOneInvalidMessage.setInt(1, mId);
                res = mUpdateOneInvalidMessage.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to validate a Message's file
     * @param mId Which Message to validate their file
     * @return The number of rows updated (1)
     */
    int updateOneValidMessageFile(int mId){
        int res = -1;
            try {
                mUpdateOneValidMessageFile.setInt(1, mId);
                res = mUpdateOneValidMessageFile.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to invalidate a Message's file
     * @param mId Which Message to invalidate their file
     * @return The number of rows updated (1)
     */
    int updateOneInvalidMessageFile(int mId){
        int res = -1;
            try {
                mUpdateOneInvalidMessageFile.setInt(1, mId);
                res = mUpdateOneInvalidMessageFile.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to validate a Message's url
     * @param mId Which Message to validate their url
     * @return The number of rows updated (1)
     */
    int updateOneValidMessageUrl(int mId){
        int res = -1;
            try {
                mUpdateOneValidMessageUrl.setInt(1, mId);
                res = mUpdateOneValidMessageUrl.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to invalidate a Message's url
     * @param mId Which Message to invalidate their url
     * @return The number of rows updated (1)
     */
    int updateOneInvalidMessageUrl(int mId){
        int res = -1;
            try {
                mUpdateOneInvalidMessageUrl.setInt(1, mId);
                res = mUpdateOneInvalidMessageUrl.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to update a User in the database
     * 
     * @param uId The id of the User to be updated 
     * @param name The new name of the User
     * @param email The new email of the User
     * @param gender The new gender of the User
     * @param SO The new sexual orientation of the User
     * @param username The new username of the User
     * @param note The new note about the User
     * 
     * @return The number of rows updated (1)
     */
    int updateUser(int uId, String name, String email, String gender, String SO, String username, String note) {
        int res = -1;
            try {
                mUpdateOneUser.setString(1, name);
                mUpdateOneUser.setString(2, email);
                mUpdateOneUser.setString(3, gender);
                mUpdateOneUser.setString(4, SO);
                mUpdateOneUser.setString(5, username);
                mUpdateOneUser.setString(6, note);
                mUpdateOneUser.setInt(7, uId);
                res = mUpdateOneUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to validate a User
     * @param mId Which User to validate
     * @return The number of rows updated (1)
     */
    int updateOneValidUser(int uId){
        int res = -1;
            try {
                mUpdateOneValidUser.setInt(1, uId);
                res = mUpdateOneValidUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to invalidate a User
     * @param mId Which User to invalidate
     * @return The number of rows updated (1)
     */
    int updateOneInvalidUser(int uId){
        int res = -1;
            try {
                mUpdateOneInvalidUser.setInt(1, uId);
                res = mUpdateOneInvalidUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /** 
     * Method to update a Comment in the Database
     *
     * @param cId The id of the Comment to be updated
     * @param content The new content of the Comment
     * @param uId The new user id of the Comment (shouldn't really change)
     * @param mId The new message id of the Comment (shouldn't really change)
     *
     * @return The number of rows updated (1)
     */
    int updateComment(int cId, String content, int uId, int mId, String file, String url) {
        int res = -1;
            try {
                mUpdateOneComment.setString(1, content);
                mUpdateOneComment.setInt(2, uId);
                mUpdateOneComment.setInt(3, mId);
                mUpdateOneComment.setString(4, file);
                mUpdateOneComment.setString(5, url);
                mUpdateOneComment.setInt(6, cId);
                res = mUpdateOneComment.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to validate a Comment
     * @param mId Which Comment to validate
     * @return The number of rows updated (1)
     */
    int updateOneValidComment(int cId){
        int res = -1;
            try {
                mUpdateOneValidComment.setInt(1, cId);
                res = mUpdateOneValidComment.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to invalidate a Comment
     * @param mId Which Comment to invalidate
     * @return The number of rows updated (1)
     */
    int updateOneInvalidComment(int cId){
        int res = -1;
            try {
                mUpdateOneInvalidComment.setInt(1, cId);
                res = mUpdateOneInvalidComment.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to validate a Comment's file
     * @param cId Which Comment to validate their file
     * @return The number of rows updated (1)
     */
    int updateOneValidCommentFile(int cId){
        int res = -1;
            try {
                mUpdateOneValidCommentFile.setInt(1, cId);
                res = mUpdateOneValidCommentFile.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to invalidate a Comment's file
     * @param cId Which Comment to invalidate their file
     * @return The number of rows updated (1)
     */
    int updateOneInvalidCommentFile(int cId){
        int res = -1;
            try {
                mUpdateOneInvalidCommentFile.setInt(1, cId);
                res = mUpdateOneInvalidCommentFile.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to validate a Comment's url
     * @param mId Which Comment to validate their url
     * @return The number of rows updated (1)
     */
    int updateOneValidCommentUrl(int cId){
        int res = -1;
            try {
                mUpdateOneValidCommentUrl.setInt(1, cId);
                res = mUpdateOneValidCommentUrl.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to invalidate a Comment's url
     * @param mId Which Comment to invalidate their url
     * @return The number of rows updated (1)
     */
    int updateOneInvalidCommentUrl(int cId){
        int res = -1;
            try {
                mUpdateOneInvalidCommentUrl.setInt(1, cId);
                res = mUpdateOneInvalidCommentUrl.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to update an Upvote in the Database
     *
     * @param uvId The id of the Upvote to be updated
     * @param mId The message id of the Upvote (shouldn't change)
     * @param uId The user id of the Upvote (shouldn't change)
     *
     * @return The number of rows updated (1)
     */
    int updateUpvote(int uvId, int mId, int uId) {
        int res = -1;
            try {
                mUpdateOneUpvote.setInt(3, uvId);
                mUpdateOneUpvote.setInt(1, mId);
                mUpdateOneUpvote.setInt(2, uId);
                res = mUpdateOneUpvote.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Method to update an Downvote in the Database
     *
     * @param dvId The id of the Downvote to be updated
     * @param mId The message id of the Downvote (shouldn't change)
     * @param uId The user id of the Downvote (shouldn't change)
     *
     * @return The number of rows updated (1)
     */
    int updateDownvote(int dvId, int mId, int uId) {
        int res = -1;
            try {
                mUpdateOneDownvote.setInt(3, dvId);
                mUpdateOneDownvote.setInt(1, mId);
                mUpdateOneDownvote.setInt(2, uId);
                res = mUpdateOneDownvote.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Create messages table. If it already exists, this will print an error and return false
     * 
     * @param table Which table to query
     * 
     * @return A boolean indicating whether the table was created succesfully
     */
    boolean createTable(String table) {
        try {
            int choice;
            choice = Integer.parseInt(table);
            switch (choice) {
                case 1: // messages table, table 1
                    mCreateMessagesTable.execute();
                    return true;
                case 2: // users table, table 2
                    mCreateUsersTable.execute();
                    return true;
                case 3: // comments table, table 3
                    mCreateCommentsTable.execute();
                    return true;
                case 4: // upvotes table, table 4
                    mCreateUpvotesTable.execute();
                    return true;
                case 5: // downvotes table, table 5
                    mCreateDownvotesTable.execute();
                    return true;
        
                default: 
                    System.err.println("Invalid table\n");
                    return false;
        }
    } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }

    /**
     * Remove table from the database.  If it does not exist, this will print
     * an error and return false.
     * 
     * @param table Which table to query
     * 
     * @return A boolean indicating whether the table was created succesfully
     */
    boolean dropTable(String table) {
        try {
            int choice;
            choice = Integer.parseInt(table);
            switch (choice) {
                case 1: // messages table, table 1
                    mDropMessagesTable.execute();
                    return true;
                case 2: // users table, table 2
                    mDropUsersTable.execute();
                    return true;
                case 3: // comments table, table 3
                    mDropCommentsTable.execute();
                    return true;
                case 4: // upvotes table, table 4
                    mDropUpvotesTable.execute();
                    return true;
                case 5: // downvotes table, table 5
                    mDropDownvotesTable.execute();
                    return true;
        
                default: 
                    System.err.println("Invalid table\n");
                    return false;
        }
    } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
}