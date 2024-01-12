package edu.lehigh.cse216.rdm325.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// import java.util.Map;
import java.util.List;

/**
 * Unit test for Database.
 */

public class DatabaseTest extends TestCase {
    //Sample data to add to the database
    static String subject1 = "String subject";
    static String subject2 = "String subject2";
    static String message1 = "String message";
    static String message2 = "String message2";

    static String name1 = "John Smith";
    static String name2 = "Jesse Pinkman";
    static String email1 = "john@email.com";
    static String email2 = "chilliflake@email.com";
    static String gender1 = "male";
    static String gender2 = "male";
    static String SO1 = "straight";
    static String SO2 = "Jane";
    static String username1 = "Jonny";
    static String username2 = "crazy8";
    static String note1 = "test";
    static String note2 = "bitch";

    static String content1 = "Heisenberg";
    static String content2 = "Chicken Man";

    static String file1 = "FAED3765293C";
    static String file2 = "CADF92626817";
    static String url1 = "https://www.breakingbad.com";
    static String url2 = "https://www.fuckyou.org";


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatabaseTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        TestSuite suite= new TestSuite();
        //Need to occur in this order so that the operations function correctly
        suite.addTest(new DatabaseTest("testDBConnection"));
        suite.addTest(new DatabaseTest("testCreateTables"));
        suite.addTest(new DatabaseTest("testAddUsersTable"));
        suite.addTest(new DatabaseTest("testAddMessagesTable"));
        suite.addTest(new DatabaseTest("testAddCommentsTable"));
        suite.addTest(new DatabaseTest("testAddUpvotesTable"));
        suite.addTest(new DatabaseTest("testAddDownvotesTable"));
        suite.addTest(new DatabaseTest("testSelectAllMessages"));
        suite.addTest(new DatabaseTest("testSelectAllValidMessages"));
        suite.addTest(new DatabaseTest("testSelectAllInvalidMessages"));
        suite.addTest(new DatabaseTest("testSelectAllUsers"));
        suite.addTest(new DatabaseTest("testSelectAllValidUsers"));
        suite.addTest(new DatabaseTest("testSelectAllInvalidUsers"));
        suite.addTest(new DatabaseTest("testSelectAllComments"));
        suite.addTest(new DatabaseTest("testSelectAllValidComments"));
        suite.addTest(new DatabaseTest("testSelectAllInvalidComments"));
        suite.addTest(new DatabaseTest("testSelectAllUpvotes"));
        suite.addTest(new DatabaseTest("testSelectAllDownvotes"));
        suite.addTest(new DatabaseTest("testSelectOneMessage"));
        suite.addTest(new DatabaseTest("testSelectOneUser"));
        suite.addTest(new DatabaseTest("testSelectOneComment"));
        suite.addTest(new DatabaseTest("testSelectOneUpvote"));
        suite.addTest(new DatabaseTest("testSelectOneDownvote"));
        suite.addTest(new DatabaseTest("testModifyRowMessages"));
        suite.addTest(new DatabaseTest("testModifyRowUsers"));
        suite.addTest(new DatabaseTest("testModifyRowComments"));
        suite.addTest(new DatabaseTest("testModifyRowUpvotes"));
        suite.addTest(new DatabaseTest("testModifyRowDownvotes"));
        suite.addTest(new DatabaseTest("testInvalidateRowMessages"));
        suite.addTest(new DatabaseTest("testInvalidateRowUsers"));
        suite.addTest(new DatabaseTest("testInvalidateRowComments"));
        suite.addTest(new DatabaseTest("testInvalidateFileMessages"));
        suite.addTest(new DatabaseTest("testInvalidateFileComments"));
        suite.addTest(new DatabaseTest("testInvalidateUrlMessages"));
        suite.addTest(new DatabaseTest("testInvalidateUrlComments"));
        suite.addTest(new DatabaseTest("testValidateRowMessages"));
        suite.addTest(new DatabaseTest("testValidateRowUsers"));
        suite.addTest(new DatabaseTest("testValidateRowComments"));
        suite.addTest(new DatabaseTest("testValidateFileMessages"));
        suite.addTest(new DatabaseTest("testValidateFileComments"));
        suite.addTest(new DatabaseTest("testValidateUrlMessages"));
        suite.addTest(new DatabaseTest("testValidateUrlComments"));
        suite.addTest(new DatabaseTest("testDeleteRows"));
        suite.addTest(new DatabaseTest("testDropTables"));
        return suite;
    }
    
    /**
     * Test getting connection to Database
     */
    public void testDBConnection(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        assertTrue(db != null);
        db.disconnect();
    }
    /**
     * Test creating the tables
     */
    public void testCreateTables(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);
        /*
        db.dropTable("5");
        db.dropTable("4");
        db.dropTable("3"); 
        db.dropTable("1");
        db.dropTable("2");   
        */
        assertTrue(db.createTable("2"));
        assertTrue(db.createTable("1"));
        assertTrue(db.createTable("3"));
        assertTrue(db.createTable("4"));
        assertTrue(db.createTable("5"));
        db.disconnect();
    }
    /**
     * Test adding 2 messages to the table and checking return values
     */
    public void testAddMessagesTable(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;
        int uId = 1;

        Database db = Database.getDatabase(ip, port, user, pass);    
        assertEquals(1, db.insertMessage(subject1, message1, uId, file1, url1));
        assertEquals(1, db.insertMessage(subject2, message2, uId, file2, url2));
        db.disconnect();
    }

    /**
     * Test adding 2 users to the table and checking return values
     */
    public void testAddUsersTable(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        assertEquals(1, db.insertUser(name1, email1, gender1, SO1, username1, note1));
        assertEquals(1, db.insertUser(name2, email2, gender2, SO2, username2, note2));
        db.disconnect();
    }

    /**
     * Test adding 2 comments to the table and checking return values
     */
    public void testAddCommentsTable(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;
        int uid = 1;

        Database db = Database.getDatabase(ip, port, user, pass);    
        assertEquals(1, db.insertComment(content1, uid, 1, file1, url1));
        assertEquals(1, db.insertComment(content2, uid, 2, file2, url2));
        db.disconnect();
    }

    /**
     * Test adding an upvote to the table and checking return values
     */
    public void testAddUpvotesTable(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;
        int uid = 1;

        Database db = Database.getDatabase(ip, port, user, pass);    
        assertEquals(1, db.insertUpvote(1, uid));
        assertEquals(1, db.insertUpvote(2, uid));
        db.disconnect();
    }

    /**
     * Test adding an upvote to the table and checking return values
     */
    public void testAddDownvotesTable(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;
        int uid = 2;

        Database db = Database.getDatabase(ip, port, user, pass);    
        assertEquals(1, db.insertDownvote(1, uid));
        assertEquals(1, db.insertDownvote(2, uid));
        db.disconnect();
    }

    /**
     * Test getting all rows from Messages table and checking their values
     */
    public void testSelectAllMessages(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<MessageData> data = db.selectAllMessages();
        assertEquals(subject1, data.get(0).mSubject);
        assertEquals(subject2, data.get(1).mSubject);
        assertEquals(message1, data.get(0).mMessage);
        assertEquals(message2, data.get(1).mMessage);
        assertEquals(file1, data.get(0).mFile);
        assertEquals(file2, data.get(1).mFile);
        assertEquals(url1, data.get(0).mUrl);
        assertEquals(url2, data.get(1).mUrl);
        
        db.disconnect();
    }

    /**
     * Test getting all rows from valid Messages table and checking their values
     */
    public void testSelectAllValidMessages(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<MessageData> data = db.selectAllValidMessages();
        assertEquals(true, data.get(0).mValid);
        assertEquals(true, data.get(1).mValid);
        
        db.disconnect();
    }

    /**
     * Test getting all rows from invalid Messages table and checking their values
     */
    public void testSelectAllInvalidMessages(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<MessageData> data = db.selectAllInvalidMessages();
        assertEquals(0, data.size());
        
        db.disconnect();
    }

    /**
     * Test getting all rows from Users table and checking their values
     */
    public void testSelectAllUsers(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<UserData> data = db.selectAllUsers();
        assertEquals(name1, data.get(0).uName);
        assertEquals(name2, data.get(1).uName);
        assertEquals(email1, data.get(0).uEmail);
        assertEquals(email2, data.get(1).uEmail);
        assertEquals(username1, data.get(0).uUsername);
        assertEquals(username2, data.get(1).uUsername);
        
        db.disconnect();
    }

    /**
     * Test getting all rows from valid Users table and checking their values
     */
    public void testSelectAllValidUsers(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<UserData> data = db.selectAllValidUsers();
        assertEquals(true, data.get(0).uValid);
        assertEquals(true, data.get(1).uValid);
        
        db.disconnect();
    }

    /**
     * Test getting all rows from invalid Users table and checking their values
     */
    public void testSelectAllInvalidUsers(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<UserData> data = db.selectAllInvalidUsers();
        assertEquals(0, data.size());
        
        db.disconnect();
    }

    /**
     * Test getting all rows from Comments table and checking their values
     */
    public void testSelectAllComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<CommentData> data = db.selectAllComments();
        assertEquals(content1, data.get(0).cComment);
        assertEquals(content2, data.get(1).cComment);
        assertEquals(file1, data.get(0).cFile);
        assertEquals(file2, data.get(1).cFile);
        assertEquals(url1, data.get(0).cUrl);
        assertEquals(url2, data.get(1).cUrl);
        
        db.disconnect();
    }

    /**
     * Test getting all rows from Comments table and checking their values
     */
    public void testSelectAllValidComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<CommentData> data = db.selectAllValidComments();
        assertEquals(true, data.get(0).cValid);
        assertEquals(true, data.get(1).cValid);
        
        db.disconnect();
    }

    /**
     * Test getting all rows from Comments table and checking their values
     */
    public void testSelectAllInvalidComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<CommentData> data = db.selectAllInvalidComments();
        assertEquals(0, data.size());
        
        db.disconnect();
    }

    /**
     * Test getting all rows from Upvotes table and checking their values
     */
    public void testSelectAllUpvotes(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<UpvoteData> data = db.selectAllUpvotes();
        assertEquals(1, data.get(0).muvmId);
        assertEquals(2, data.get(1).muvmId);
        assertEquals(1, data.get(0).muvuId);
        assertEquals(1, data.get(1).muvuId);
        
        db.disconnect();
    }

    /**
     * Test getting all rows from Downvotes table and checking their values
     */
    public void testSelectAllDownvotes(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        List<DownvoteData> data = db.selectAllDownvotes();
        assertEquals(1, data.get(0).mdvmId);
        assertEquals(2, data.get(1).mdvmId);
        assertEquals(2, data.get(0).mdvuId);
        assertEquals(2, data.get(1).mdvuId);
        
        db.disconnect();
    }

    /**
     * Test getting each message from the table individually and check their message and subject
     */
    public void testSelectOneMessage(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertEquals(subject1, db.selectOneMessage(1).mSubject);
        assertEquals(subject2, db.selectOneMessage(2).mSubject);
        assertEquals(message1, db.selectOneMessage(1).mMessage);
        assertEquals(message2, db.selectOneMessage(2).mMessage);
        
        db.disconnect();
    }

    /**
     * Test getting each user from the table individually and check their message and subject
     */
    public void testSelectOneUser(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertEquals(name1, db.selectOneUser(1).uName);
        assertEquals(name2, db.selectOneUser(2).uName);
        assertEquals(email1, db.selectOneUser(1).uEmail);
        assertEquals(email2, db.selectOneUser(2).uEmail);
        assertEquals(username1, db.selectOneUser(1).uUsername);
        assertEquals(username2, db.selectOneUser(2).uUsername);
        
        db.disconnect();
    }

    /**
     * Test getting each comment from the table individually and check their message and subject
     */
    public void testSelectOneComment(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertEquals(content1, db.selectOneComment(1).cComment);
        assertEquals(content2, db.selectOneComment(2).cComment);
        assertEquals(file1, db.selectOneComment(1).cFile);
        assertEquals(file2, db.selectOneComment(2).cFile);
        assertEquals(url1, db.selectOneComment(1).cUrl);
        assertEquals(url2, db.selectOneComment(2).cUrl);
        
        db.disconnect();
    }

    /**
     * Test getting each upvote from the table individually and check their message and subject
     */
    public void testSelectOneUpvote(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertEquals(1, db.selectOneUpvote(1).muvmId);
        assertEquals(2, db.selectOneUpvote(2).muvmId);
        assertEquals(1, db.selectOneUpvote(1).muvuId);
        assertEquals(1, db.selectOneUpvote(2).muvuId);
        
        db.disconnect();
    }

    /**
     * Test getting each downvote from the table individually and check their message and subject
     */
    public void testSelectOneDownvote(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertEquals(1, db.selectOneDownvote(1).mdvmId);
        assertEquals(2, db.selectOneDownvote(2).mdvmId);
        assertEquals(2, db.selectOneDownvote(1).mdvuId);
        assertEquals(2, db.selectOneDownvote(2).mdvuId);
        
        db.disconnect();
    }

    /**
     * Test modifying both messages and check the return values and that their new values persist
     */
    public void testModifyRowMessages(){
       String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertFalse(-1 == db.updateMessage(1, subject2, message2, 1, file2, url2));
        assertEquals(subject2, db.selectOneMessage(1).mSubject);
        assertEquals(message2, db.selectOneMessage(1).mMessage);
        assertEquals(file2, db.selectOneMessage(1).mFile);
        assertEquals(url2, db.selectOneMessage(1).mUrl);
        
        db.disconnect();
    }

    /**
     * Test modifying both users and check the return values and that their new values persist
     */
    public void testModifyRowUsers(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertFalse(-1 == db.updateUser(1, name2, email2, gender2, SO2, username2, note2));
        assertEquals(name2, db.selectOneUser(1).uName);
        assertEquals(email2, db.selectOneUser(1).uEmail);
        assertEquals(gender2, db.selectOneUser(1).uGender);
        assertEquals(SO2, db.selectOneUser(1).uSO);
        assertEquals(username2, db.selectOneUser(1).uUsername);
        assertEquals(note2, db.selectOneUser(1).uNote);
        
        db.disconnect();
    }

    /**
     * Test modifying both comments and check the return values and that their new values persist
     */
    public void testModifyRowComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertFalse(-1 == db.updateComment(1, content2, 1, 1, file2, url2));
        assertEquals(content2, db.selectOneComment(1).cComment);
        assertEquals(file2, db.selectOneComment(1).cFile);
        assertEquals(url2, db.selectOneComment(1).cUrl);
        
        db.disconnect();
    }

    /**
     * Test modifying both upvotes and check the return values and that their new values persist
     */
    public void testModifyRowUpvotes(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertFalse(-1 == db.updateUpvote(1, 1, 1));
        assertEquals(1, db.selectOneUpvote(1).muvmId);
        assertEquals(1, db.selectOneUpvote(1).muvuId);
        
        db.disconnect();
    }

    /**
     * Test modifying both upvotes and check the return values and that their new values persist
     */
    public void testModifyRowDownvotes(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertFalse(-1 == db.updateDownvote(1, 1, 1));
        assertEquals(1, db.selectOneDownvote(1).mdvmId);
        assertEquals(1, db.selectOneDownvote(1).mdvuId);
        
        db.disconnect();
    }

    /**
     * Test validating a message and check the return values and that their new values persist
     */
    public void testValidateRowMessages(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneValidMessage(1));
        assertEquals(true, db.selectOneMessage(1).mValid);

        db.disconnect();
    }

    /**
     * Test invalidating a message and check the return values and that their new values persist
     */
    public void testInvalidateRowMessages(){
       String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneInvalidMessage(1));
        assertEquals(false, db.selectOneMessage(1).mValid);

        db.disconnect();
    }

    /**
     * Test validating a user and check the return values and that their new values persist
     */
    public void testValidateRowUsers(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneValidUser(1));
        assertEquals(true, db.selectOneUser(1).uValid);

        db.disconnect();
    }

    /**
     * Test invalidating a user and check the return values and that their new values persist
     */
    public void testInvalidateRowUsers(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneInvalidUser(1));
        assertEquals(false, db.selectOneUser(1).uValid);

        db.disconnect();
    }

    /**
     * Test validating a comment and check the return values and that their new values persist
     */
    public void testValidateRowComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneValidComment(1));
        assertEquals(true, db.selectOneComment(1).cValid);

        db.disconnect();
    }

    /**
     * Test invalidating a comment and check the return values and that their new values persist
     */
    public void testInvalidateRowComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneInvalidComment(1));
        assertEquals(false, db.selectOneComment(1).cValid);

        db.disconnect();
    }

    /**
     * Test validating a message file and check the return values and that their new values persist
     */
    public void testValidateFileMessages(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneValidMessageFile(1));
        assertEquals(true, db.selectOneMessage(1).mValidFile);

        db.disconnect();
    }

    /**
     * Test invalidating a message file and check the return values and that their new values persist
     */
    public void testInvalidateFileMessages(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneInvalidMessageFile(1));
        assertEquals(false, db.selectOneMessage(1).mValidFile);

        db.disconnect();
    }

    /**
     * Test validating a message url and check the return values and that their new values persist
     */
    public void testValidateUrlMessages(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneValidMessageUrl(1));
        assertEquals(true, db.selectOneMessage(1).mValidUrl);

        db.disconnect();
    }

    /**
     * Test invalidating a message url and check the return values and that their new values persist
     */
    public void testInvalidateUrlMessages(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneInvalidMessageUrl(1));
        assertEquals(false, db.selectOneMessage(1).mValidUrl);

        db.disconnect();
    }

    /**
     * Test validating a comment file and check the return values and that their new values persist
     */
    public void testValidateFileComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneValidCommentFile(1));
        assertEquals(true, db.selectOneComment(1).cValidFile);

        db.disconnect();
    }

    /**
     * Test invalidating a comment file and check the return values and that their new values persist
     */
    public void testInvalidateFileComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneInvalidCommentFile(1));
        assertEquals(false, db.selectOneComment(1).cValidFile);

        db.disconnect();
    }

    /**
     * Test validating a comment url and check the return values and that their new values persist
     */
    public void testValidateUrlComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneValidCommentUrl(1));
        assertEquals(true, db.selectOneComment(1).cValidUrl);

        db.disconnect();
    }

    /**
     * Test invalidating a comment url and check the return values and that their new values persist
     */
    public void testInvalidateUrlComments(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);

        assertFalse(-1 == db.updateOneInvalidCommentUrl(1));
        assertEquals(false, db.selectOneComment(1).cValidUrl);

        db.disconnect();
    }

    /**
     * Test deleting all rows from tables and check the return values
     */
    public void testDeleteRows(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);    
        
        assertFalse(-1 == db.deleteRow("5", 1));
        assertFalse(-1 == db.deleteRow("5", 2));
        assertFalse(-1 == db.deleteRow("4", 1));
        assertFalse(-1 == db.deleteRow("4", 2));
        assertFalse(-1 == db.deleteRow("3", 1));
        assertFalse(-1 == db.deleteRow("3", 2));
        assertFalse(-1 == db.deleteRow("1", 1));
        assertFalse(-1 == db.deleteRow("1", 2));
        assertFalse(-1 == db.deleteRow("2", 1));
        assertFalse(-1 == db.deleteRow("2", 2));
        db.disconnect();
    }
    /**
     * Test dropping all tables
     */
    public void testDropTables(){
        String ip = Env.ip;
        String port = Env.port;
        String user = Env.user;
        String pass = Env.pass;

        Database db = Database.getDatabase(ip, port, user, pass);
        assertTrue(db.dropTable("5"));
        assertTrue(db.dropTable("4"));  
        assertTrue(db.dropTable("3"));  
        assertTrue(db.dropTable("1"));
        assertTrue(db.dropTable("2"));
        db.disconnect();
    }
} 