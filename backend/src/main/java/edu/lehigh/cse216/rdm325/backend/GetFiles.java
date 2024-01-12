package edu.lehigh.cse216.rdm325.backend;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileWriter;
import java.util.Base64;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;

import edu.lehigh.cse216.rdm325.backend.FileDTO;

//https://developers.google.com/drive/api/guides/manage-downloads

/* Class to demonstrate use-case of drive's download file. */
public class GetFiles {

  private static final Gson gson = new Gson();

  /**
   * Download a file
   *
   * @param realFileId file ID of any workspace document format file.
   * @return FileDTO holding name, data, and mimetype
   */
  public static FileDTO downloadFile(String realFileId) {
    if(realFileId == "" || realFileId == null || realFileId == "\n"){
        return null;
    }
        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
          guides on implementing OAuth2 for your application.*/
    GoogleCredentials credentials;
    //Get credentials file
    String json;
    try{
      json = FileUtils.readFileToString(new java.io.File("credentials.json"), StandardCharsets.UTF_8);
    } catch(Exception e){
      System.out.println(e);
      return null;
    }
    CredentialObject credentialObject= gson.fromJson(json, CredentialObject.class);
    String key = new String(Base64.getDecoder().decode(System.getenv("GOOGLE_PRIVATE_KEY")));
    credentialObject.private_key = "-----BEGIN PRIVATE KEY-----" + key + "-----END PRIVATE KEY-----\n";
    //System.out.println(credentialObject.private_key);
    credentialObject.private_key_id = System.getenv("GOOGLE_PRIVATE_KEY_ID");
    java.io.File tempFile = new java.io.File("tempCreds.json");
    try{
        //create and write to a new file
        tempFile.createNewFile();
        FileWriter writer = new FileWriter("tempCreds.json");
        String json2 = gson.toJson(credentialObject);
        //Necessary to deal with escape characters
        json2 = json2.replaceAll("\\\\n", "\\\n");
        writer.write(json2);
        writer.close();
    }catch(Exception e){
        System.err.println(e);
        return null;
    }

    try{
         credentials = GoogleCredentials.fromStream(new FileInputStream("tempCreds.json"))
            .createScoped(DriveScopes.DRIVE_FILE);
    } catch(Exception e){
        System.err.println("Unable to get credentials: " + e.toString());
        return null;
    }    
    //delete temp file
    try {
      tempFile.delete();
    }catch(Exception e){
        System.out.println(e);
    }

    HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
        credentials);

    // Build a new authorized API client service.
    Drive service = new Drive.Builder(new NetHttpTransport(),
        GsonFactory.getDefaultInstance(),
        requestInitializer)
        .setApplicationName("Drive samples")
        .build();

    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      FileDTO dto = new FileDTO();
      service.files().get(realFileId)
          .executeMediaAndDownloadTo(outputStream);
        File fileMetadata = service.files().get(realFileId).execute();
        // https://stackoverflow.com/questions/2418485/how-do-i-convert-a-byte-array-to-base64-in-java
        dto.fData = new String (Base64.getEncoder().encode(outputStream.toByteArray()));
        dto.mimeType = fileMetadata.getMimeType();
        dto.fName = fileMetadata.getName();
      return dto;
    } catch (GoogleJsonResponseException e) {
      // TODO(developer) - handle error appropriately
      System.err.println("Unable to get file from Google: " + e.getDetails());
      return null;
    } catch(Exception e){
        System.err.println("Unable to get file: " + e.toString());
        System.err.println(realFileId);
        return null;
    }
  }
  /**
   * Delete a file from the drive
   *
   * @param realFileId file ID of any workspace document format file.
   * @return a String with either Success or an error
   */
  public static String deleteFile(String realFileId){
    if(realFileId == "" || realFileId == null || realFileId == "\n"){
      return null;
    }
        /* Load pre-authorized user credentials from the environment.
          TODO(developer) - See https://developers.google.com/identity for
          guides on implementing OAuth2 for your application.*/
    GoogleCredentials credentials;
    //Get credentials file
    String json;
    try{
      json = FileUtils.readFileToString(new java.io.File("credentials.json"), StandardCharsets.UTF_8);
    } catch(Exception e){
      System.out.println(e);
      return null;
    }
    CredentialObject credentialObject= gson.fromJson(json, CredentialObject.class);
    String key = new String(Base64.getDecoder().decode(System.getenv("GOOGLE_PRIVATE_KEY")));
    credentialObject.private_key = "-----BEGIN PRIVATE KEY-----" + key + "-----END PRIVATE KEY-----\n";
    //System.out.println(credentialObject.private_key);
    credentialObject.private_key_id = System.getenv("GOOGLE_PRIVATE_KEY_ID");
    java.io.File tempFile = new java.io.File("tempCreds.json");
    try{
        tempFile.createNewFile();
        FileWriter writer = new FileWriter("tempCreds.json");
        String json2 = gson.toJson(credentialObject);
        json2 = json2.replaceAll("\\\\n", "\\\n");
        writer.write(json2);
        writer.close();
    }catch(Exception e){
        System.err.println(e);
        return null;
    }

    try{
        credentials = GoogleCredentials.fromStream(new FileInputStream("tempCreds.json"))
            .createScoped(DriveScopes.DRIVE_FILE);
    } catch(Exception e){
        System.err.println("Unable to get credentials: " + e.toString());
        return null;
    }    
    //delete temp file
    try {
      tempFile.delete();
    }catch(Exception e){
        System.out.println(e);
    }

    HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
        credentials);

    // Build a new authorized API client service.
    Drive service = new Drive.Builder(new NetHttpTransport(),
        GsonFactory.getDefaultInstance(),
        requestInitializer)
        .setApplicationName("Drive samples")
        .build();
      //actually delete file
      try{
        service.files().delete(realFileId).execute();
      }catch(Exception e){
        System.out.println(e);
        return e.toString();
      }
      return "Success";
      
    }

    
}