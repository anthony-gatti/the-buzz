package edu.lehigh.cse216.rdm325.backend;


// Import the Spark package, so that we can make use of the "get" function to 
// create an HTTP GET route
import spark.Spark;
import spark.Request;
import spark.Response;
import static spark.Spark.halt;

import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

// Import Google's JSON library
import com.google.gson.*;

// Generate Session Tokens
import edu.lehigh.cse216.rdm325.backend.CreateAuth;

// Upload file
import edu.lehigh.cse216.rdm325.backend.UploadBasic;
import edu.lehigh.cse216.rdm325.backend.GetFiles;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

// Google Oauth
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

//Caching 
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.lang.InterruptedException;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;


/**
 * For now, our app creates an HTTP server that can only get and add data.
 */
public class App {

    /**
     * DB port
     */
    private static final String DEFAULT_PORT_DB = "5432";
     /**
     * Backend port
     */
    private static final int DEFAULT_PORT_SPARK = 4567;
    /**
     * CLIENT_ID of web
     */
    private static final String CLIENT_ID1 = System.getenv("CLIENT_ID1");
    /**
     * CLIENT_ID of mobile
     */
    private static final String CLIENT_ID2 = System.getenv("CLIENT_ID2");
    /**
     * CLIENT_Secret of app
     */
    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");
    // session token is the key, userId it the value
    private static final HashMap<String, Integer> SESSION_TOKENS = new HashMap<>();

    private static MemcachedClient mc;
    // gson provides us with a way to turn JSON into objects, and objects
    // into JSON.
    //
    // NB: it must be final, so that it can be accessed from our lambdas
    //
    // NB: Gson is thread-safe.  See 
    // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
    //gson = new Gson();
    private static final Gson gson = new Gson();

    private static Database database;

    /**
    * Get a fully-configured connection to the database, or exit immediately
    * Uses the Postgres configuration from environment variables
    * 
    * NB: now when we shutdown the server, we no longer lose all data
    * 
    * @return null on failure, otherwise configured database object
    */
    private static Database getDatabaseConnection(){
        if( System.getenv("DATABASE_URL") != null ){
            return Database.getDatabase(System.getenv("DATABASE_URL"), DEFAULT_PORT_DB);
        }

        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        return Database.getDatabase(ip, port, "", user, pass);
    }

    /**
    * Get an integer environment variable if it exists, and otherwise return the
    * default value.
    * 
    * @param envar The name of the environment variable to get.
    * @param defaultVal The integer value to use as the default if envar isn't found
    * 
    * @return The best answer we could come up with for a value for envar
    */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }
    /**
     * Get the assoicated UID value with a sessionKey from the cache
     * 
     * @param   mc The cache client
     * @param   sessionKey The key we check to see if is in the cache
     * @return  The uid associated with the sessionKey or null if there is none
     */
    static Integer getUID(MemcachedClient mc, String sessionKey){
        Integer uid = null;
        try{   
            uid = mc.get(sessionKey);
        }catch (TimeoutException te) {
            System.err.println("Timeout during set or get: " +
                            te.getMessage());
        } catch (InterruptedException ie) {
            System.err.println("Interrupt during set or get: " +
                            ie.getMessage());
        } catch (MemcachedException me) {
            System.err.println("Memcached error during get or set: " +
                            me.getMessage());
        } catch (Exception e){
            return null;
        }
        
        return uid;
    }
    /**
     * GET route that returns all message subjects and Ids.  All we do is get 
     * the data, embed it in a StructuredResponse, turn it into JSON, and 
     * return it.  If there's no data, we return "[]", so there's no need 
     * for error handling.
     */
    public static String handleGetMessagesRequest(Request request, Response response) {
        // Ensure status 200 OK, with a MIME type of JSON

        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if (uId == null) {
            response.status(401); // Unauthorized
            response.type("application/json");
            return (gson.toJson(new StructuredResponse("error", "unauthorized app access", null)));
        }      

        response.status(200);
        response.type("application/json");
        ArrayList<DataRowMessage> res = database.selectAllMessages(uId);
        ArrayList<DataRowMessage> realRes = new ArrayList<DataRowMessage>();
        for (DataRowMessage row : res){
            if(row.valid == false){
                continue;
            }
            if(row.validFile == true && row.mFile != null){
                String json = null;
                try{
                    json = mc.get(row.mFile.fData);
                }catch (TimeoutException te) {
                    System.err.println("Timeout during set or get: " + te.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } catch (InterruptedException ie) {
                    System.err.println("Interrupt during set or get: " + ie.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } catch (MemcachedException me) {
                    System.err.println("Memcached error during get or set: " + me.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                }
                
                if(json != null){
                    String id = row.mFile.fData;
                    row.mFile = FileDTO.toDTO(json);
                    try{
                        mc.set(id, 3500, row.mFile.makeString());
                    }catch (TimeoutException te) {
                        System.err.println("Timeout during set or get: " + te.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (InterruptedException ie) {
                        System.err.println("Interrupt during set or get: " + ie.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (MemcachedException me) {
                        System.err.println("Memcached error during get or set: " + me.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    }
                }
                else {
                    String id = row.mFile.fData;
                    row.mFile = GetFiles.downloadFile(row.mFile.fData);
                    try{
                        mc.set(id, 3500, row.mFile.makeString());
                    }catch (TimeoutException te) {
                        System.err.println("Timeout during set or get: " + te.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (InterruptedException ie) {
                        System.err.println("Interrupt during set or get: " + ie.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (MemcachedException me) {
                        System.err.println("Memcached error during get or set: " + me.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    }                    
                }
            }
            if(!row.mURL.equals("") && row.validURL == false){
                row.mURL = "";
            }
            realRes.add(row);
        }
        return (gson.toJson(new StructuredResponse("ok", null, realRes)));
    }
    /**
     * GET route that returns all comments for a given post.  All we do is get 
     * the data, embed it in a StructuredResponse, turn it into JSON, and 
     * return it.  If there's no data, we return "[]", so there's no need 
     * for error handling.
     */
    public static String handleGetCommentsRequest(Request request, Response response) {
        // Ensure status 200 OK, with a MIME type of JSON

        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if (uId == null) {
            response.status(401); // Unauthorized
            response.type("application/json");
            return (gson.toJson(new StructuredResponse("error", "unauthorized app access", null)));
        }
        int idx = Integer.parseInt(request.params("mId"));
        response.status(200);
        response.type("application/json");
        ArrayList<DataRowComment> res = database.selectAllComments(idx);
        ArrayList<DataRowComment> realRes = new ArrayList<>();
        for (DataRowComment row : res){
            if(row.valid == false){
                continue;
            }
            if(row.cFile != null){
                String json = null;
                try{
                    json = mc.get(row.cFile.fData);
                }catch (TimeoutException te) {
                    System.err.println("Timeout during set or get: " + te.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } catch (InterruptedException ie) {
                    System.err.println("Interrupt during set or get: " + ie.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } catch (MemcachedException me) {
                    System.err.println("Memcached error during get or set: " + me.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                }
                
                if(json != null){
                    String id = row.cFile.fData;
                    row.cFile = FileDTO.toDTO(json);
                    try{
                        mc.set(id, 3500, row.cFile.makeString());
                    }catch (TimeoutException te) {
                        System.err.println("Timeout during set or get: " + te.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (InterruptedException ie) {
                        System.err.println("Interrupt during set or get: " + ie.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (MemcachedException me) {
                        System.err.println("Memcached error during get or set: " + me.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    }
                }
                else {
                    String id = row.cFile.fData;
                    row.cFile = GetFiles.downloadFile(row.cFile.fData);
                    try{
                        mc.set(id, 3500, row.cFile.makeString());
                    }catch (TimeoutException te) {
                        System.err.println("Timeout during set or get: " + te.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (InterruptedException ie) {
                        System.err.println("Interrupt during set or get: " + ie.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (MemcachedException me) {
                        System.err.println("Memcached error during get or set: " + me.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    }                    
                }
            }
            realRes.add(row);
        }
        return (gson.toJson(new StructuredResponse("ok", null, realRes)));
    }
    /**
     * GET route that returns the count of all comments for a given post.  All we do is get 
     * the data, embed it in a StructuredResponse, turn it into JSON, and 
     * return it.  If there's no data, we return "[]", so there's no need 
     * for error handling.
     */
    public static String handleCommentsCountRequest(Request request, Response response){
        // ensure status 200 OK, with a MIME type of JSON

        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);
        
        if(uId == null){
            response.status(401); // Unauthorized
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        int idx = Integer.parseInt(request.params("mId"));
        response.status(200);
        response.type("application/json");
        return gson.toJson(new StructuredResponse("ok", null, database.getCountComments(idx)));
    }
    /**
     * GET route that returns everything for a single row in the DataRowMessage.
     * The ":id" suffix in the first parameter to get() becomes 
     * request.params("id"), so that we can get the requested row ID.  If 
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error.  Otherwise, we have an integer, and the only possible 
     * error is that it doesn't correspond to a row with data.
     */
    public static String handleGetMessageRequest(Request request, Response response) {
        // ensure status 200 OK, with a MIME type of JSON

        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        int idx = Integer.parseInt(request.params("id"));
        response.status(200);
        response.type("application/json");
        DataRowMessage row = database.selectOneMessage(idx, uId);
        if (row == null) {
            return gson.toJson(new StructuredResponse("error", idx + " not found", null));
        } else {
            if(row.valid == false){
                return gson.toJson(new StructuredResponse("error", "invalid message", null));
            }
            if(row.validFile == true && row.mFile != null){
                String json = null;
                try{
                    json = mc.get(row.mFile.fData);
                }catch (TimeoutException te) {
                    System.err.println("Timeout during set or get: " + te.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } catch (InterruptedException ie) {
                    System.err.println("Interrupt during set or get: " + ie.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } catch (MemcachedException me) {
                    System.err.println("Memcached error during get or set: " + me.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                }
                
                if(json != null){
                    String id = row.mFile.fData;
                    row.mFile = FileDTO.toDTO(json);
                    try{
                        mc.set(id, 3500, row.mFile.makeString());
                    }catch (TimeoutException te) {
                        System.err.println("Timeout during set or get: " + te.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (InterruptedException ie) {
                        System.err.println("Interrupt during set or get: " + ie.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (MemcachedException me) {
                        System.err.println("Memcached error during get or set: " + me.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    }
                }
                else {
                    String id = row.mFile.fData;
                    row.mFile = GetFiles.downloadFile(row.mFile.fData);
                    try{
                        mc.set(id, 3500, row.mFile.makeString());
                    }catch (TimeoutException te) {
                        System.err.println("Timeout during set or get: " + te.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (InterruptedException ie) {
                        System.err.println("Interrupt during set or get: " + ie.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (MemcachedException me) {
                        System.err.println("Memcached error during get or set: " + me.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    }                    
                }
            }
            if(!row.mURL.equals("") && row.validURL == false){
                row.mURL = "";
            }
            return gson.toJson(new StructuredResponse("ok", null, row));
        }
    }
    /**
     * GET route that returns everything for a single row in the DataRowComment.
     * The ":id" suffix in the first parameter to get() becomes 
     * request.params("id"), so that we can get the requested row ID.  If 
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error.  Otherwise, we have an integer, and the only possible 
     * error is that it doesn't correspond to a row with data.
     */
    public static String handleGetCommentRequest(Request request, Response response){
        // ensure status 200 OK, with a MIME type of JSON

        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        int idx = Integer.parseInt(request.params("id"));
        response.status(200);
        response.type("application/json");
        DataRowComment row = database.selectOneComment(idx);
        if (row == null) {
            return gson.toJson(new StructuredResponse("error", idx + " not found", null));
        } else {
            if(row.valid == false){
                return gson.toJson(new StructuredResponse("error", "invalid comment", null));
            }
            if(row.cFile != null){
                String json = null;
                try{
                    json = mc.get(row.cFile.fData);
                }catch (TimeoutException te) {
                    System.err.println("Timeout during set or get: " + te.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } catch (InterruptedException ie) {
                    System.err.println("Interrupt during set or get: " + ie.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                } catch (MemcachedException me) {
                    System.err.println("Memcached error during get or set: " + me.getMessage());
                    //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                }
                
                if(json != null){
                    String id = row.cFile.fData;
                    row.cFile = FileDTO.toDTO(json);
                    try{
                        mc.set(id, 3500, row.cFile.makeString());
                    }catch (TimeoutException te) {
                        System.err.println("Timeout during set or get: " + te.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (InterruptedException ie) {
                        System.err.println("Interrupt during set or get: " + ie.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (MemcachedException me) {
                        System.err.println("Memcached error during get or set: " + me.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    }
                }
                else {
                    String id = row.cFile.fData;
                    row.cFile = GetFiles.downloadFile(row.cFile.fData);
                    try{
                        mc.set(id, 3500, row.cFile.makeString());
                    }catch (TimeoutException te) {
                        System.err.println("Timeout during set or get: " + te.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (InterruptedException ie) {
                        System.err.println("Interrupt during set or get: " + ie.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    } catch (MemcachedException me) {
                        System.err.println("Memcached error during get or set: " + me.getMessage());
                        //return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
                    }                    
                }
            }
            return gson.toJson(new StructuredResponse("ok", null, row));
        }
    }
    /**
     * GET route that returns everything for a single row in the DataRowUser.
     * The ":id" suffix in the first parameter to get() becomes 
     * request.params("id"), so that we can get the requested row ID.  If 
     * ":id" isn't a number, Spark will reply with a status 500 Internal
     * Server Error.  Otherwise, we have an integer, and the only possible 
     * error is that it doesn't correspond to a row with data.
     */
    public static String handleGetUserRequest(Request request, Response response){
        // ensure status 200 OK, with a MIME type of JSON

        // Authenticate
        int idx = Integer.parseInt(request.params("id"));
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        response.status(200);
        response.type("application/json");
        DataRowUser data = database.selectOneUser(idx);
        if (data == null) {
            return gson.toJson(new StructuredResponse("error", idx + " not found", null));
        } else {
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /**
     * GET route that returns everything for a single row in the DataRowUpvote.
     * The ":mid" suffix in the first parameter to get() becomes 
     * request.params("mid"), so that we can get the requested row ID.  If 
     * ":mid" isn't a number, Spark will reply with a status 500 Internal
     * Server Error.  Otherwise, we have an integer, and the only possible 
     * error is that it doesn't correspond to a row with data.
     */
    public static String handleGetUpvoteRequest(Request request, Response response){
        // ensure status 200 OK, with a MIME type of JSON

        // Authenticate
        int idx = Integer.parseInt(request.params("mid"));
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        } else if(database.selectOneUpvote(idx, uId) == null){
            return gson.toJson(new StructuredResponse("error", "this user hasn't upvoted this message", null));
        }
        response.status(200);
        response.type("application/json");
        Integer data = database.selectOneUpvote(idx, uId);
        if (data == null || data == -1) {
            return gson.toJson(new StructuredResponse("error", "upvote doesn't exist", null));
        } else {
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /**
     * GET route that returns everything for a single row in the DataRowDownvote.
     * The ":mid" suffix in the first parameter to get() becomes 
     * request.params("mid"), so that we can get the requested row ID.  If 
     * ":mid" isn't a number, Spark will reply with a status 500 Internal
     * Server Error.  Otherwise, we have an integer, and the only possible 
     * error is that it doesn't correspond to a row with data.
     */
    public static String handleGetDownvoteRequest(Request request, Response response){
        // ensure status 200 OK, with a MIME type of JSON

        // Authenticate
        int idx = Integer.parseInt(request.params("mid"));
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        } else if(database.selectOneDownvote(idx, uId) == null){
            return gson.toJson(new StructuredResponse("error", "this user hasn't downvoted this message", null));
        }
        response.status(200);
        response.type("application/json");
        Integer data = database.selectOneDownvote(idx, uId);
        if (data ==  null || data == -1) {
            return gson.toJson(new StructuredResponse("error", "downvotevote doesn't exist", null));
        } else {
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /**
     * POST route for adding a new element to the DataRowMessage.  This will read
     * JSON from the body of the request, turn it into a SimpleRequestMessage
     * object, extract the subject and message, insert them, and return the 
     * ID of the newly created row.
     */
    public static String handlePostMessageRequest(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // NB: if gson.Json fails, Spark will reply with status 500 Internal 
        // Server Error
        SimpleRequestMessage req = gson.fromJson(request.body(), SimpleRequestMessage.class);
        // ensure status 200 OK, with a MIME type of JSON
        // NB: even on error, we return 200, but with a JSON object that
        //     describes the error.
        response.status(200);
        response.type("application/json");
        // NB: createEntry checks for null subject and message
        String file = req.mFile;
        if(file != null){
            try{
                file = UploadBasic.uploadBasic(req.fName, req.mFile);
            } catch (Exception e){
                System.out.println(e.toString());
                return gson.toJson(new StructuredResponse("error", "error performing insertion of file", null));
            } 
        }
        else{
            file = "";
        }
        String url = req.mURL;
        if(url == null){
            url = "";
        }
        int newId = database.insertRowMessage(req.mSubject, req.mMessage, uId, file, url);
        if (newId == -1) {
            return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
        } else {
            return gson.toJson(new StructuredResponse("ok", "message insertion successfully.", null));
        }
    }
    /**
     * POST route for adding a new element to the DataRowComment.  This will read
     * JSON from the body of the request, turn it into a SimpleRequestComment 
     * object, extract the subject and message, insert them, and return the 
     * ID of the newly created row.
     */
    public static String handlePostCommentRequest(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // NB: if gson.Json fails, Spark will reply with status 500 Internal 
        // Server Error
        SimpleRequestComment req = gson.fromJson(request.body(), SimpleRequestComment.class);
        // ensure status 200 OK, with a MIME type of JSON
        // NB: even on error, we return 200, but with a JSON object that
        //     describes the error.
        int idx = Integer.parseInt(request.params("mId"));
        response.status(200);
        response.type("application/json");
        String file = req.cFile;
        if(file != null){
            try{
                file = UploadBasic.uploadBasic(req.fName, req.cFile);
            } catch (Exception e){
                System.out.println(e.toString());
                return gson.toJson(new StructuredResponse("error", "error performing insertion of file", null));
            } 
        }
        else{
            file = "";
        }
        String url = req.cURL;
        if(url == null){
            url = "";
        }
        // NB: createEntry checks for null subject and message
        int newId = database.insertRowComment(req.cContent, uId, idx, file, url);
        if (newId == -1) {
            return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
        } else {
            return gson.toJson(new StructuredResponse("ok", "inserted comment successfully.", null));
        }
    }
    /**
     * POST route for adding a new element to the upvote table.  This will not read
     * JSON from the body of the request, instead it creates based in the uid and mid of the
     * request and returning if it was a success or not.
     */
    public static String handlePostUpvoteRequest(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // NB: if gson.Json fails, Spark will reply with status 500 Internal 
        // Server Error
        // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
        // ensure status 200 OK, with a MIME type of JSON
        // NB: even on error, we return 200, but with a JSON object that
        //     describes the error.
        int idx = Integer.parseInt(request.params("mId"));
        response.status(200);
        response.type("application/json");
        // NB: createEntry checks for null subject and message
        Integer newId = -1, delId = -1;
        if(database.selectOneUpvote(idx,uId) == null || database.selectOneUpvote(idx,uId) == -1){
            newId = database.insertRowUpvote(idx, uId);
            if((delId = database.selectOneDownvote(idx,uId)) != null || delId != -1){
                delId = database.deleteRowDownvote(delId);
            }
        } else {
            return gson.toJson(new StructuredResponse("error", "error performing Upvote", null));
        }
        // delete downvote for this user if it exists
        if (newId == null || newId == -1) {
            return gson.toJson(new StructuredResponse("error", "error performing Upvote", null));
        } else {
            return gson.toJson(new StructuredResponse("ok", "Upvote insertion successful", null));
        }
    }
    /**
     * POST route for adding a new element to the downvote table.  This will not read
     * JSON from the body of the request, instead it creates based in the uid and mid of the
     * request and returning if it was a success or not.
     */
    public static String handlePostDownvoteRequest(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // NB: if gson.Json fails, Spark will reply with status 500 Internal 
        // Server Error
        // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
        // ensure status 200 OK, with a MIME type of JSON
        // NB: even on error, we return 200, but with a JSON object that
        //     describes the error.
        int idx = Integer.parseInt(request.params("mId"));
        response.status(200);
        response.type("application/json");
        // NB: createEntry checks for null subject and message
        Integer newId = -1, delId = -1;
        if(database.selectOneDownvote(idx,uId) == null || database.selectOneDownvote(idx,uId) == -1){
            newId = database.insertRowDownvote(idx, uId);
            if((delId = database.selectOneUpvote(idx,uId)) != null || delId != -1){
                delId = database.deleteRowUpvote(delId);
            }
        } else {
            return gson.toJson(new StructuredResponse("error", "error performing Downvote", null));
        }
        // delete downvote for this user if it exists
        if (newId == null || newId == -1) {
            return gson.toJson(new StructuredResponse("error", "error performing Downvote", null));
        } else {
            return gson.toJson(new StructuredResponse("ok", "insertion successful", null));
        }
    }
    /**
     * PUT route for updating a row in the DataRowComment.
     * Edits the message content of the comment itself
     * Only the comment creator and the admin can edit their comment
     */ 
    public static String handleCommentContentEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID or can't parse the JSON, Spark will send
        // a status 500
        int idx = Integer.parseInt(request.params("cId"));
        SimpleRequestComment req = gson.fromJson(request.body(), SimpleRequestComment.class);
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        if(database.selectOneComment(idx).uId != uId && uId != 1){
            return gson.toJson(new StructuredResponse("error", "user " + uId + " can't edit comment " + idx, null));
        }
        int result = database.updateOneComment(idx, req.cContent);
        if (result < 1 ) {
            return gson.toJson(new StructuredResponse("error", "unable to update content" + idx, null));
        } else {
            DataRowComment data = database.selectOneComment(idx);
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /**
     * PUT route for updating a row in the DataRowComment.
     * Edits the file content of the comment.
     * Only the comment creator and the admin can edit their comment
     */ 
    public static String handleCommentFileEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        int idx = Integer.parseInt(request.params("cId"));
        System.out.println(idx);
        String data = database.selectOneFile(idx);
        if (data == null) {
            return gson.toJson(new StructuredResponse("error", idx + " not found", null));
        } 

        if(!GetFiles.deleteFile(data).equals("Success")){
            return gson.toJson(new StructuredResponse("error", "Error deleting file", null));
        }
        /** GONNA HAVE TO CHANGE THIS */
        SimpleRequestComment req = gson.fromJson(request.body(), SimpleRequestComment.class);
        // ensure status 200 OK, with a MIME type of JSON
        // NB: even on error, we return 200, but with a JSON object that
        //     describes the error.
        response.status(200);
        response.type("application/json");
        String file = req.cFile;
        if(file != null){
            try{
                file = UploadBasic.uploadBasic(req.fName, req.cFile);
            } catch (Exception e){
                System.out.println(e.toString());
                return gson.toJson(new StructuredResponse("error", "error performing insertion of file", null));
            } 
        }
        return gson.toJson(new StructuredResponse("ok", null, database.updateOneCommentFile(idx, file)));
        
    }
    /**
     * PUT route for updating a row in the DataRowComment.
     * Edits the link/url content of the comment.
     * Only the comment creator and the admin can edit their comment
     */ 
    public static String handleCommentLinkEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID or can't parse the JSON, Spark will send
        // a status 500
        int idx = Integer.parseInt(request.params("cId"));
        SimpleRequestComment req = gson.fromJson(request.body(), SimpleRequestComment.class);
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        if(database.selectOneComment(idx).uId != uId && uId != 1){
            return gson.toJson(new StructuredResponse("error", "user " + uId + " can't edit comment " + idx, null));
        }
        int result = database.updateOneCommentURL(idx, req.cURL);
        if (result < 1 ) {
            return gson.toJson(new StructuredResponse("error", "unable to update url" + idx, null));
        } else {
            DataRowComment data = database.selectOneComment(idx);
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /** 
     * PUT route for updating a name in a DataRowUser.  This is almost 
     * exactly the same as POST but with it updates an existing element.
     * Only the comment creator and the admin can edit their comment
    */
    public static String handleNameEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID or can't parse the JSON, Spark will send
        // a status 500
        int idx = Integer.parseInt(request.params("id"));
        SimpleRequestUser req = gson.fromJson(request.body(), SimpleRequestUser.class);
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        if(idx != uId && uId != 1){
            return gson.toJson(new StructuredResponse("error", "user " + uId + " can't edit user " + idx, null));
        }
        int result = database.updateOneName(idx, req.uName);
        if (result < 1 ) {
            return gson.toJson(new StructuredResponse("error", "unable to update name" + idx, null));
        } else {
            DataRowUser data = database.selectOneUser(idx);
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /** 
     * PUT route for updating a email in a DataRowUser.  This is almost 
     * exactly the same as POST but with it updates an existing element.
     * Only the comment creator and the admin can edit their comment
    */
    public static String handleUserEmailEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID or can't parse the JSON, Spark will send
        // a status 500
        int idx = Integer.parseInt(request.params("id"));
        SimpleRequestUser req = gson.fromJson(request.body(), SimpleRequestUser.class);
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        if(idx != uId && uId != 1){
            return gson.toJson(new StructuredResponse("error", "user " + uId + " can't edit user " + idx, null));
        }
        int result = database.updateOneEmail(idx, req.uEmail);
        if (result < 1 ) {
            return gson.toJson(new StructuredResponse("error", "unable to update email" + idx, null));
        } else {
            DataRowUser data = database.selectOneUser(idx);
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /** 
     * PUT route for updating a gender in a DataRowUser.  This is almost 
     * exactly the same as POST but with it updates an existing element.
     * Only the comment creator and the admin can edit their comment
    */
    public static String handleUserGenderEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID or can't parse the JSON, Spark will send
        // a status 500
        int idx = Integer.parseInt(request.params("id"));
        SimpleRequestUser req = gson.fromJson(request.body(), SimpleRequestUser.class);
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        if(idx != uId && uId != 1){
            return gson.toJson(new StructuredResponse("error", "user " + uId + " can't edit user " + idx, null));
        }
        int result = database.updateOneGender(idx, req.uGender);
        if (result < 1 ) {
            return gson.toJson(new StructuredResponse("error", "unable to update gender" + idx, null));
        } else {
            DataRowUser data = database.selectOneUser(idx);
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /** 
     * PUT route for updating a so in a DataRowUser.  This is almost 
     * exactly the same as POST but with it updates an existing element.
     * Only the comment creator and the admin can edit their comment
    */
    public static String handleUserSoEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID or can't parse the JSON, Spark will send
        // a status 500
        int idx = Integer.parseInt(request.params("id"));
        SimpleRequestUser req = gson.fromJson(request.body(), SimpleRequestUser.class);
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        if(idx != uId && uId != 1){
            return gson.toJson(new StructuredResponse("error", "user " + uId + " can't edit user " + idx, null));
        }
        int result = database.updateOneSO(idx, req.uSO);
        if (result < 1 ) {
            return gson.toJson(new StructuredResponse("error", "unable to update so" + idx, null));
        } else {
            DataRowUser data = database.selectOneUser(idx);
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /** 
     * PUT route for updating a username in a DataRowUser.  This is almost 
     * exactly the same as POST but with it updates an existing element.
     * Only the comment creator and the admin can edit their comment
    */
    public static String handleUsernameEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID or can't parse the JSON, Spark will send
        // a status 500
        int idx = Integer.parseInt(request.params("id"));
        SimpleRequestUser req = gson.fromJson(request.body(), SimpleRequestUser.class);
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        if(idx != uId && uId != 1){
            return gson.toJson(new StructuredResponse("error", "user " + uId + " can't edit user " + idx, null));
        }
        int result = database.updateOneUsername(idx, req.uUsername);
        if (result < 1 ) {
            return gson.toJson(new StructuredResponse("error", "unable to update username" + idx, null));
        } else {
            DataRowUser data = database.selectOneUser(idx);
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /** 
     * PUT route for updating a note in a DataRowUser.  This is almost 
     * exactly the same as POST but with it updates an existing element.
     * Only the comment creator and the admin can edit their comment
    */
    public static String handleUserNoteEdit(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID or can't parse the JSON, Spark will send
        // a status 500
        int idx = Integer.parseInt(request.params("id"));
        SimpleRequestUser req = gson.fromJson(request.body(), SimpleRequestUser.class);
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        if(idx != uId && uId != 1){
            return gson.toJson(new StructuredResponse("error", "user " + uId + " can't edit user " + idx, null));
        }
        int result = database.updateOneNote(idx, req.uNote);
        if (result < 1 ) {
            return gson.toJson(new StructuredResponse("error", "unable to update note" + idx, null));
        } else {
            DataRowUser data = database.selectOneUser(idx);
            return gson.toJson(new StructuredResponse("ok", null, data));
        }
    }
    /**
     * POST route for adding a new element to the DataRowUser.  This will read
     * JSON from the body of the request, turn it into a SimpleRequestUser 
     * object, extract the subject and message, insert them, and return the 
     * ID of the newly created row. It will also insert them into the cache.
     */
    public static String handleUserPost(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null || uId != 1){
            response.status(401);
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        GoogleIdTokenVerifier verifier = null;
        try{
            verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory()).setAudience(Arrays.asList(CLIENT_ID1, CLIENT_ID2)).setIssuer("https://accounts.google.com").build();
        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return gson.toJson(new StructuredResponse("error", "Google verification exception thrown:", e.getLocalizedMessage()));
        }
        Payload payload = null;
        try{
            GoogleIdToken idToken = verifier.verify(request.headers("Id-Token-String"));
            if (idToken != null)
                payload = idToken.getPayload();
            else
                return gson.toJson(new StructuredResponse("error", "invalid token provided", null));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return gson.toJson(new StructuredResponse("error", "oauth exception thrown:", e.getLocalizedMessage()));
        }
        /*
        GoogleAuthorizationCodeFlow AuthFlow = new GoogleAuthorizationCodeFlow.Builder(
            new NetHttpTransport(), GsonFactory.getDefaultInstance(),
            CLIENT_ID1, CLIENT_SECRET,
            Collections.singleton(Scopes.openid)).setAccessType("offline").build();
        */
        // NB: if gson.Json fails, Spark will reply with status 500 Internal 
        // Server Error
        // ensure status 200 OK, with a MIME type of JSON
        // NB: even on error, we return 200, but with a JSON object that
        //     describes the error.
        
        response.status(200);
        response.type("application/json");
        // NB: createEntry checks for null subject and message
        String username = ((String)payload.get("email")).substring(0, (int)((String)payload.get("email")).indexOf("@"));
        Integer newId = null;
        if(database.selectOneUserId((String)payload.getEmail()) == null)
           newId = database.insertRowUser((String)payload.get("name"), (String)payload.get("email"), "", "", username, "");
        newId = database.selectOneUserId((String)payload.getEmail());
        if (newId == null) {
            return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
        } else {
            String sessionToken = CreateAuth.createSessionToken((String)payload.getEmail(), username);
            try{
                int userid = mc.get(sessionToken);
                DataRowUser user = database.selectOneUser(userid);
                if(user != null && user.valid == false){
                    return gson.toJson(new StructuredResponse("error", "unauthorized user", null));
                }
            }catch (TimeoutException te) {
                System.err.println("Timeout during set or get: " + te.getMessage());
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } catch (InterruptedException ie) {
                System.err.println("Interrupt during set or get: " + ie.getMessage());
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } catch (MemcachedException me) {
                System.err.println("Memcached error during get or set: " + me.getMessage());
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } catch (NullPointerException e){
                System.out.println("Not there yet");
            }
            try{
                mc.set(sessionToken, 3600, newId);
            }catch (TimeoutException te) {
                System.err.println("Timeout during set or get: " + te.getMessage());
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } catch (InterruptedException ie) {
                System.err.println("Interrupt during set or get: " + ie.getMessage());
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } catch (MemcachedException me) {
                System.err.println("Memcached error during get or set: " + me.getMessage());
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            }
            
            // System.out.println(sessionToken+":"+newId);
            return gson.toJson(new StructuredResponseUser("ok", "insertion successful", sessionToken, newId));
        }
    }
    /**
     * DELETE route for removing a row from the upvotes table
     */
    public static String handleUpvoteDelete(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID, Spark will send a status 500
        int idx = Integer.parseInt(request.params("mId"));
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        // NB: we won't concern ourselves too much with the quality of the 
        //     message sent on a successful delete
        Integer newId = -1;
        if((newId = database.selectOneUpvote(idx,uId)) != null){
            newId = database.deleteRowUpvote(newId);
        }
        if (newId == null) {
            return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
        } else {
            return gson.toJson(new StructuredResponse("ok", null, null));
        }
    }
    /**
     * DELETE route for removing a row from the downvotes table
     */
    public static String handleDownvoteDelete(Request request, Response response){
        // Authenticate
        final String sessionKey = request.headers("Session-Key");
        final Integer uId = getUID(mc, sessionKey);

        if(uId == null){
            response.status(401);
            response.type("application/json");
            return gson.toJson(new StructuredResponse("error", "unauthorized app access", null));
        }
        // If we can't get an ID, Spark will send a status 500
        int idx = Integer.parseInt(request.params("mId"));
        // ensure status 200 OK, with a MIME type of JSON
        response.status(200);
        response.type("application/json");
        // NB: we won't concern ourselves too much with the quality of the 
        //     message sent on a successful delete
        Integer newId = -1;
        if((newId = database.selectOneDownvote(idx,uId)) != null){
            newId = database.deleteRowDownvote(newId);
        }
        if (newId == null) {
            return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
        } else {
            return gson.toJson(new StructuredResponse("ok", null, null));
        }
    }




    /**
     * 
     * Runs the  backend
     * 
     * @param args main() args
     */
    public static void main(String[] args) {
        

        /**
         * CACHING
         */
        List<InetSocketAddress> servers =
        AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS").replace(",", " "));
        AuthInfo authInfo =
        AuthInfo.plain(System.getenv("MEMCACHIER_USERNAME"),
                        System.getenv("MEMCACHIER_PASSWORD"));

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);

        // Configure SASL auth for each server
        for(InetSocketAddress server : servers) {
        builder.addAuthInfo(server, authInfo);
        }

        // Use binary protocol
        builder.setCommandFactory(new BinaryCommandFactory());
        // Connection timeout in milliseconds (default: )
        builder.setConnectTimeout(1000);
        // Reconnect to servers (default: true)
        builder.setEnableHealSession(true);
        // Delay until reconnect attempt in milliseconds (default: 2000)
        builder.setHealSessionInterval(2000);
        
        try {
            mc = builder.build();
            try {
                //mc.set("foo", 0, "bar");
                String val = mc.get("foo");
                System.out.println(val);
            } catch (TimeoutException te) {
                System.err.println("Timeout during set or get: " +
                                te.getMessage());
            } catch (InterruptedException ie) {
                System.err.println("Interrupt during set or get: " +
                                ie.getMessage());
            } catch (MemcachedException me) {
                System.err.println("Memcached error during get or set: " +
                                me.getMessage());
            }
        } catch (IOException ioe) {
            System.err.println("Couldn't create a connection to MemCachier: " +
                            ioe.getMessage());
        }
    
        //Create admin token
        String admin = CreateAuth.createAdminToken("I am the very model of a modern Major-General "+
        "I've information vegetable, animal, and mineral " +
        "I know the kings of England, and I quote the fights historical ");
        try{
            mc.set(admin, 0, 1);
        } catch (TimeoutException te) {
            System.err.println("Timeout during set or get: " +
                            te.getMessage());
        } catch (InterruptedException ie) {
            System.err.println("Interrupt during set or get: " +
                            ie.getMessage());
        } catch (MemcachedException me) {
            System.err.println("Memcached error during get or set: " +
                            me.getMessage());
        }
        

        

        // DataRow holds all of the data that has been provided via HTTP 
        // requests
        //
        // NB: every time we shut down the server, we will lose all data, and 
        //     every time we start the server, we'll have an empty DataRow,
        //     with IDs starting over from 0.
        database = getDatabaseConnection();

        // Set the port on which to listen for requests from the environment
        Spark.port(getIntFromEnv("PORT", DEFAULT_PORT_SPARK));

        // Set up the location for serving static files.  If the STATIC_LOCATION
        // environment variable is set, we will serve from it.  Otherwise, serve
        // from "/web"
        String static_location_override = System.getenv("STATIC_LOCATION");
        if (static_location_override == null) {
            Spark.staticFileLocation("/web");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        if ("True".equalsIgnoreCase(System.getenv("CORS_ENABLED"))) {
            final String acceptCrossOriginRequestsFrom = "*";
            final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
            final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
            enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
        }

        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {
            res.redirect("/index.html");
            return "";
        });

        //Routes
        //Gets
        Spark.get("api/v3.0/messages", App::handleGetMessagesRequest);
        Spark.get("api/v3.0/messages/comments/:mId", App::handleGetCommentsRequest);
        Spark.get("api/v3.0/messages/comments/count/:mId", App::handleCommentsCountRequest);
        Spark.get("api/v3.0/messages/:id", App::handleGetMessageRequest);
        Spark.get("api/v3.0/comments/:id", App::handleGetCommentRequest);
        Spark.get("api/v3.0/users/:id", App::handleGetUserRequest);
        Spark.get("api/v3.0/messages/upvote/:mid", App::handleGetUpvoteRequest);
        Spark.get("api/v3.0/messages/downvote/:mid", App::handleGetDownvoteRequest);
        //Posts
        Spark.post("api/v3.0/messages", App::handlePostMessageRequest);
        Spark.post("api/v3.0/comments/:mId", App::handlePostCommentRequest);
        Spark.post("api/v3.0/messages/upvote/:mId", App::handlePostUpvoteRequest);
        Spark.post("api/v3.0/messages/downvote/:mId", App::handlePostDownvoteRequest);
            //New User
        Spark.post("api/v3.0/users", App::handleUserPost);
        //Puts
            //Comments
        Spark.put("api/v3.0/comments/content/:cId", App::handleCommentContentEdit);
        Spark.put("api/v3.0/comments/file/:cId", App::handleCommentFileEdit);
        Spark.put("api/v3.0/comments/url/:cId", App::handleCommentLinkEdit);
            //User Page
        Spark.put("api/v3.0/users/name/:id", App::handleNameEdit);
        Spark.put("api/v3.0/users/email/:id", App::handleUserEmailEdit);
        Spark.put("api/v3.0/users/gender/:id", App::handleUserGenderEdit);
        Spark.put("api/v3.0/users/so/:id", App::handleUserSoEdit);
        Spark.put("api/v3.0/users/username/:id", App::handleUsernameEdit);
        Spark.put("api/v3.0/users/note/:id", App::handleUserNoteEdit);
        //Delete
        Spark.delete("api/v3.0/messages/upvote/:mId", App::handleUpvoteDelete);
        Spark.delete("api/v3.0/messages/downvote/:mId", App::handleDownvoteDelete);

    }

/**
 * Set up CORS headers for the OPTIONS verb, and for every response that the
 * server sends.  This only needs to be called once.
 * 
 * @param origin The server that is allowed to send requests to this server
 * @param methods The allowed HTTP verbs from the above origin
 * @param headers The headers that can be sent with a request from the above
 *                origin
 */
private static void enableCORS(String origin, String methods, String headers) {
        // Create an OPTIONS route that reports the allowed CORS headers and methods
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        // 'before' is a decorator, which will run before any 
        // get/post/put/delete.  In our case, it will put three extra CORS
        // headers into the response
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
}