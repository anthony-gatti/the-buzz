package edu.lehigh.cse216.rdm325.backend;

import java.util.Base64;

/**
 * Class for creating authorizations
 */
public class CreateAuth {
    //private static final SecureRandom secureRandom = new SecureRandom();
    /**
     * the base64Encoder used to encode session tokens
     */
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();    

    /**
     * The function used to create Session tokens
     * @param email the email of the user
     * @param username the username of the user
     * @return an encoded token
     */
    public static String createSessionToken(String email, String username){
        String key = email + ":" + username;
        return base64Encoder.encodeToString(key.getBytes());
    }
    /**
     * The function used to create the Admin Session token
     * @param phrase the phrase to be encoded
     * @return an encoded token
     */
    public static String createAdminToken(String phrase){
        return base64Encoder.encodeToString(phrase.getBytes());
    }
    
    
}