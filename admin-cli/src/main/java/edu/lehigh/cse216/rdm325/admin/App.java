package edu.lehigh.cse216.rdm325.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 * App is our basic admin app.  For now, all it does is connect to the database
 * and then disconnect
 */
public class App {

    /**
     * Print the menu for our program
     */
    static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [T] Create table");
        System.out.println("  [D] Drop table");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [*] Query for all rows");
        System.out.println("  [-] Delete a row");
        System.out.println("  [+] Insert a new row");
        System.out.println("  [~] Update a row");
        System.out.println("  [V] Update a row's validity to true");
        System.out.println("  [I] Update a row's validity to false");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in) {
        // The valid actions:
        String actions = "TD1*-+VI~^q?";

        // We repeat until a valid single-character option is selected        
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String message
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided.  May be "".
     */
    static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided.  On error, it will be -1
     */
    static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * The main routine runs a loop that gets a request from the user and
     * processes it
     * 
     * @param argv Command-line options.  Ignored by this program.
     */
    public static void main(String[] argv) {
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");

        // Get a fully-configured connection to the database, or exit 
        // immediately
        Database db = Database.getDatabase(ip, port, user, pass);
        if (db == null)
            return;

        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Get the user's request, and execute it
            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'T') {
                String table = getString(in, "What table would you like to create? Messages = 1, Users = 2, Comments = 3, Upvotes = 4, Downvotes = 5");
                db.createTable(table);
            } else if (action == 'D') {
                String table = getString(in, "What table would you like to drop? Messages = 1, Users = 2, Comments = 3, Upvotes = 4, Downvotes = 5");
                db.dropTable(table);
            } else if (action == '1') {
                int table = getInt(in, "What table would you like to select a row from? Messages = 1, Users = 2, Comments = 3, Upvotes = 4, Downvotes = 5.");
                switch (table) {
                    case 1:
                        int mId = getInt(in, "Enter the row ID");
                        if (mId == -1)
                            continue;
                        MessageData resM = db.selectOneMessage(mId);
                        if (resM != null) {
                            System.out.println("  [" + resM.mId + "] Subject:" + resM.mSubject + " Message: "+ resM.mMessage + " User id: " + resM.muId + " File Url: " + resM.mFile + " Url: " + resM.mUrl + " Valid: " + resM.mValid + " ValidFile: " + resM.mValidFile + " ValidUrl: " + resM.mValidUrl);
                        }
                        break;
                    case 2:
                        int uId = getInt(in, "Enter the row ID");
                        if (uId == -1)
                            continue;
                        UserData resU = db.selectOneUser(uId);
                        if (resU != null) {
                            System.out.println("  [" + resU.uId + "] Name:" + resU.uName + " Email:" + resU.uEmail + " Gender:" + resU.uGender + " Sexual Orientation: " + resU.uSO + " Username: " + resU.uUsername + " Note: " + resU.uNote + " Valid: " + resU.uValid);
                        }
                        break;
                    case 3:
                        int cId = getInt(in, "Enter the row ID");
                        if (cId == -1)
                            continue;
                        CommentData resC = db.selectOneComment(cId);
                        if (resC != null) {
                            System.out.println("  [" + resC.cId + "] " + " Content: " + resC.cComment + " Message ID: " + resC.cmId + " User ID: " + resC.cuId + " File Url: " + resC.cFile + " Url: " + resC.cUrl + " Valid: " + resC.cValid + " ValidFile: " + resC.cValidFile + " ValidUrl: " + resC.cValidUrl);
                        }
                        break;
                    case 4: 
                        int uvId = getInt(in, "Enter the row ID");
                        if (uvId == -1)
                            continue;
                        UpvoteData resUV = db.selectOneUpvote(uvId);
                        if (resUV != null) {
                            System.out.println("  [" + resUV.muvId + "] " + " Message ID: " + resUV.muvmId + " User ID: " + resUV.muvuId);
                        }
                        break;
                    case 5:
                        int dvId = getInt(in, "Enter the row ID");
                        if (dvId == -1)
                            continue;
                        DownvoteData resDV = db.selectOneDownvote(dvId);
                        if (resDV != null) {
                            System.out.println("  [" + resDV.mdvId + "] " + " Message ID: " + resDV.mdvmId + " User ID: " + resDV.mdvuId);
                        }
                        break;
                }
                
            } else if (action == '*') {
                int table = getInt(in, "What table would you like? Messages = 1, Users = 2, Comments = 3, Upvotes = 4, Downvotes = 5");
                switch (table) {
                    case 1:
                        int optionM = getInt(in, "What would you like to see? All = 1, Valid only = 2, Invalid only = 3");
                        ArrayList<MessageData> resM = null;
                        switch(optionM){
                            case 1:
                                resM = db.selectAllMessages();
                                break;
                            case 2:
                                resM = db.selectAllValidMessages();
                                break;
                            case 3:
                                resM = db.selectAllInvalidMessages();
                                break;
                            default:
                                System.out.println("Invalid option.");
                                continue;
                        }
                        if (resM == null)
                            continue;
                        System.out.println("  Current Messages:");
                        System.out.println("  -------------------------");
                        for (MessageData rdM : resM) {
                            System.out.println("  [" + rdM.mId + "] Subject: " + rdM.mSubject + " Message: "+ rdM.mMessage + " User id: " + rdM.muId + " File Url: " + rdM.mFile + " Url: " + rdM.mUrl + " Valid: " + rdM.mValid + " ValidFile: " + rdM.mValidFile + " ValidUrl: " + rdM.mValidUrl);
                        }
                        break;
                    case 2:
                        int optionU = getInt(in, "What would you like to see? All = 1, Valid only = 2, Invalid only = 3");
                        ArrayList<UserData> resU = null;
                        switch(optionU){
                            case 1:
                                resU = db.selectAllUsers();
                                break;
                            case 2:
                                resU = db.selectAllValidUsers();
                                break;
                            case 3:
                                resU = db.selectAllInvalidUsers();
                                break;
                            default:
                                System.out.println("Invalid option.");
                                continue;
                        }
                        if (resU == null)
                            continue;
                        System.out.println("  Current Users:");
                        System.out.println("  -------------------------");
                        for (UserData rdU : resU) {
                            System.out.println("  [" + rdU.uId + "] Name: " + rdU.uName + " Email:" + rdU.uEmail + " Gender:" + rdU.uGender + " Sexual Orientation: " + rdU.uSO + " Username: " + rdU.uUsername + " Note: " + rdU.uNote + " Valid: " + rdU.uValid);
                        }
                        break;
                    case 3:
                        int optionC = getInt(in, "What would you like to see? All = 1, Valid only = 2, Invalid only = 3");
                        ArrayList<CommentData> resC = db.selectAllComments();
                        switch(optionC){
                            case 1:
                                resC = db.selectAllComments();
                                break;
                            case 2:
                                resC = db.selectAllValidComments();
                                break;
                            case 3:
                                resC = db.selectAllInvalidComments();
                                break;
                            default:
                                System.out.println("Invalid option.");
                                continue;
                        }
                        if (resC == null)
                            continue;
                        System.out.println("  Current Comments:");
                        System.out.println("  -------------------------");
                        for (CommentData rdC : resC) {
                            System.out.println("  [" + rdC.cId + "] Content: " + rdC.cComment + " User ID: "+ rdC.cuId + " Message ID: " + rdC.cmId + " File Url: " + rdC.cFile + " Url: " + rdC.cUrl + " Valid: " + rdC.cValid + " ValidFile: " + rdC.cValidFile + " ValidUrl: " + rdC.cValidUrl);
                        }
                        break;
                    case 4:
                        ArrayList<UpvoteData> resUV = db.selectAllUpvotes();
                        if (resUV == null)
                            continue;
                        System.out.println("  Current Upvotes");
                        System.out.println("  -------------------------");
                        for (UpvoteData rdUV : resUV) {
                            System.out.println("  [" + rdUV.muvId + "] " + " Message ID: " + rdUV.muvmId + " User ID: " + rdUV.muvuId);
                        }
                        break;
                    case 5:
                        ArrayList<DownvoteData> resDV = db.selectAllDownvotes();
                        if (resDV == null)
                            continue;
                        System.out.println("  Current Downvotes");
                        System.out.println("  -------------------------");
                        for (DownvoteData rdDV : resDV) {
                            System.out.println("  [" + rdDV.mdvId + "] " + " Message ID: " + rdDV.mdvmId + " User ID: " + rdDV.mdvuId);
                        }
                        break;
                }
            } else if (action == '-') {
                String table = getString(in, "What table would you like to delete a row from? Messages = 1, Users = 2, Comments = 3, Upvotes = 4, Downvotes = 5");
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                int res = db.deleteRow(table, id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows deleted");
            } else if (action == '+') {
                int res = 0;
                int table = getInt(in, "What table would you like to add a row to? Messages = 1, Users = 2, Comments = 3, Upvotes = 4, Downvotes = 5");
                switch (table) {
                    case 1: //messages table
                        String newSubject = getString(in, "Enter a subject");
                        String newMessage = getString(in, "Enter a message");
                        int userId = getInt(in, "Enter a user id");
                        String newMFile = getString(in, "Enter a file url");
                        String newMUrl = getString(in, "Enter a url");
                        res = db.insertMessage(newSubject, newMessage, userId, newMFile, newMUrl);
                        System.out.println(res + " rows added");
                        break;
                    case 2: //users table
                        String newName = getString(in, "Enter a name");
                        String newEmail = getString(in, "Enter an email");
                        String newGender = getString(in, "Enter a gender");
                        String newSO = getString(in, "Enter a sexual orientation");
                        String newUsername = getString(in, "Enter a username");
                        String newNote = getString(in, "Enter a note");
                        res = db.insertUser(newName, newEmail, newGender, newSO, newUsername, newNote);
                        System.out.println(res + " rows added");
                        break;
                    case 3: //comments table
                        String newComment = getString(in, "Enter a comment");
                        int cuId = getInt(in, "Enter a user id");
                        int cmuId = getInt(in, "Enter a message id");
                        String newCFile = getString(in, "Enter a file url");
                        String newCUrl = getString(in, "Enter a url");
                        res = db.insertComment(newComment, cuId, cmuId, newCFile, newCUrl);
                        System.out.println(res + " rows added");
                        break;
                    case 4: //upvotes table
                        int uvmId = getInt(in, "Enter a message id");
                        int uvuId = getInt(in, "Enter a user id");
                        res = db.insertUpvote(uvmId, uvuId);
                        System.out.println(res + " rows added");
                        break;
                    case 5: //downvotes table
                        int dvmId = getInt(in, "Enter a message id");
                        int dvuId = getInt(in, "Enter a user id");
                        res = db.insertDownvote(dvmId, dvuId);
                        System.out.println(res + " rows added");
                        break;
                
                    default: 
                        System.out.println("Invalid table.");
                    continue;
                }
            } else if (action == '~') {
                int res = 0;
                int table = getInt(in, "What table would you like to update? Messages = 1, Users = 2, Comments = 3, Upvotes = 4, Downvotes = 5");
                switch (table) {
                    case 1: //messages table
                        int mId = getInt(in, "Enter a message id");
                        String newSubject = getString(in, "Enter the new subject");
                        String newMessage = getString(in, "Enter the new message");
                        int muId = getInt(in, "Enter the user id");
                        String mFile = getString(in, "Enter the file url");
                        String mUrl = getString(in, "Enter the file url");
                        res = db.updateMessage(mId, newSubject, newMessage, muId, mFile, mUrl);
                        System.out.println(res + " row updated");
                        break;
                    case 2: //users table
                        int uId = getInt(in, "Enter a user id");
                        String newName = getString(in, "Enter a name");
                        String newEmail = getString(in, "Enter an email");
                        String newGender = getString(in, "Enter a gender");
                        String newSO = getString(in, "Enter a sexual orientation");
                        String newUsername = getString(in, "Enter a username");
                        String newNote = getString(in, "Enter a note");
                        res = db.updateUser(uId, newName, newEmail, newGender, newSO, newUsername, newNote);
                        System.out.println(res + " row updated");
                        break;
                    case 3: //comments table
                        int cId = getInt(in, "Enter a comment id");
                        String newComment = getString(in, "Enter the new comment");
                        int cuId = getInt(in, "Enter a user id");
                        int cmuId = getInt(in, "Enter a message id");
                        String cFile = getString(in, "Enter the file url");
                        String cUrl = getString(in, "Enter the file url");
                        res = db.updateComment(cId, newComment, cuId, cmuId, cFile, cUrl);
                        System.out.println(res + " row updated");
                        break;
                    case 4: //upvotes table
                        int uvId = getInt(in, "Enter an upvote id");
                        int uvmId = getInt(in, "Enter a message id");
                        int uvuId = getInt(in, "Enter a user id");
                        res = db.updateUpvote(uvId, uvmId, uvuId);
                        System.out.println(res + " row updated");
                        break;
                    case 5: //downvotes table
                        int dvId = getInt(in, "Enter a downvote id");
                        int dvmId = getInt(in, "Enter a message id");
                        int dvuId = getInt(in, "Enter a user id");
                        res = db.updateDownvote(dvId, dvmId, dvuId);
                        System.out.println(res + " row updated");
                        break;
                
                    default: 
                        System.out.println("Invalid table.");
                    continue;
                }
        } else if (action == 'V') {
                int res = 0;
                int table = getInt(in, "What table would you access? Messages = 1, Users = 2, Comments = 3");
                switch (table) {
                    case 1: //messages table
                        int mId = getInt(in, "Enter the message id of the message to be validated");
                        int optionM = getInt(in, "What would you like to validate? Message = 1, File = 2, Url = 3");
                        switch(optionM){
                            case 1:
                                res = db.updateOneValidMessage(mId);
                                System.out.println(res + " row updated");
                                break;
                            case 2:
                                res = db.updateOneValidMessageFile(mId);
                                System.out.println(res + " row updated");
                                break;
                            case 3:
                                res = db.updateOneValidMessageUrl(mId);
                                System.out.println(res + " row updated");
                                break;
                            default:
                                System.out.println("Invalid option");
                            continue;
                        }
                        break;
                    case 2: //users table
                        int uId = getInt(in, "Enter the user id of the user to be validated");
                        res = db.updateOneValidUser(uId);
                        System.out.println(res + " row updated");
                        break;
                    case 3: //comments table
                        int cId = getInt(in, "Enter the comment id of the comment to be validated");
                        int optionC = getInt(in, "What would you like to validate? Comment = 1, File = 2, Url = 3");
                        switch(optionC){
                            case 1:
                                res = db.updateOneValidComment(cId);
                                System.out.println(res + " row updated");
                                break;
                            case 2:
                                res = db.updateOneValidCommentFile(cId);
                                System.out.println(res + " row updated");
                                break;
                            case 3:
                                res = db.updateOneValidCommentUrl(cId);
                                System.out.println(res + " row updated");
                                break;
                            default:
                                System.out.println("Invalid option");
                            continue;
                        }
                        break;
                
                    default: 
                        System.out.println("Invalid table.");
                    continue;
                }
        } else if (action == 'I') {
                int res = 0;
                int table = getInt(in, "What table would you access? Messages = 1, Users = 2, Comments = 3");
                switch (table) {
                    case 1: //messages table
                        int mId = getInt(in, "Enter the message id of the message to be invalidated");
                        int optionM = getInt(in, "What would you like to invalidate? Message = 1, File = 2, Url = 3");
                        switch(optionM){
                            case 1:
                                res = db.updateOneInvalidMessage(mId);
                                System.out.println(res + " row updated");
                                break;
                            case 2:
                                res = db.updateOneInvalidMessageFile(mId);
                                System.out.println(res + " row updated");
                                break;
                            case 3:
                                res = db.updateOneInvalidMessageUrl(mId);
                                System.out.println(res + " row updated");
                                break;
                            default:
                                System.out.println("Invalid option");
                            continue;
                        }
                        break;
                    case 2: //users table
                        int uId = getInt(in, "Enter the user id of the user to be invalidated");
                        res = db.updateOneInvalidUser(uId);
                        System.out.println(res + " row updated");
                        break;
                    case 3: //comments table
                        int cId = getInt(in, "Enter the comment id of the comment to be invalidated");
                        int optionC = getInt(in, "What would you like to invalidate? Comment = 1, File = 2, Url = 3");
                        switch(optionC){
                            case 1:
                                res = db.updateOneInvalidComment(cId);
                                System.out.println(res + " row updated");
                                break;
                            case 2:
                                res = db.updateOneInvalidCommentFile(cId);
                                System.out.println(res + " row updated");
                                break;
                            case 3:
                                res = db.updateOneInvalidCommentUrl(cId);
                                System.out.println(res + " row updated");
                                break;
                            default:
                                System.out.println("Invalid option");
                            continue;
                        }
                        break;
                
                    default: 
                        System.out.println("Invalid table.");
                    continue;
                }
        }
        // Always remember to disconnect from the database when the program 
        // exits
        }
        db.disconnect();
    }
}