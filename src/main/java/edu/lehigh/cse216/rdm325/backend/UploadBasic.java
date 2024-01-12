package edu.lehigh.cse216.rdm325.backend;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.*;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.AccessToken;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import java.io.*;
import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Date;
import java.util.Base64;
import java.util.stream.Stream;

// https://developers.google.com/drive/api/guides/manage-uploads

/* Class to demonstrate use of Drive insert file API */
public class UploadBasic {

  private static final List<String> SCOPES =
  Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
  private static final Gson gson = new Gson();

  /**
   * Upload new file.
   *
   * @param fName The name of the file
   * @param data The actual data of the file
   * @return Inserted file metadata if successful, {@code null} otherwise.
   * @throws IOException if service account credentials file not found.
   */
  public static String uploadBasic(String fName, String data) throws IOException {
    // Load pre-authorized user credentials from the environment.
    // TODO(developer) - See https://developers.google.com/identity for
    // guides on implementing OAuth2 for your application.

    

    
    String json = FileUtils.readFileToString(new java.io.File("credentials.json"), StandardCharsets.UTF_8);
    CredentialObject credentialObject= gson.fromJson(json, CredentialObject.class);
    String key = new String(Base64.getDecoder().decode(System.getenv("GOOGLE_PRIVATE_KEY")));
    credentialObject.private_key = "-----BEGIN PRIVATE KEY-----" + key + "-----END PRIVATE KEY-----\n";
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
        return "";
    }

    GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("tempCreds.json"))
      .createScoped(DriveScopes.DRIVE_FILE);
    HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
        credentials);
    try {
        tempFile.delete();
    }catch(Exception e){
        System.out.println(e);
    }
    // Build a new authorized API client service.
    Drive service = new Drive.Builder(new NetHttpTransport(),
        GsonFactory.getDefaultInstance(),
        requestInitializer)
        .setApplicationName("Drive samples")
        .build();
        
    // Upload file photo.jpg on drive.
    File fileMetadata = new File();
    fileMetadata.setName(fName);
    // File's content.
    java.io.File filePath = new java.io.File("./files/test.png");
    // Specify media type and file-path for file.
    byte[] decoded = Base64.getDecoder().decode(data);
    java.io.File outputFile = java.io.File.createTempFile("tempFile", "idc");
    //https://www.baeldung.com/java-write-byte-array-file
    try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
        outputStream.write(decoded);
    }
    //mime type is based on the file extension
    FileContent mediaContent = new FileContent(getMimeType(fName.split("\\.")[1]), outputFile); //needs escape characters to split by .
    try {
      File file = service.files().create(fileMetadata, mediaContent)
          .setFields("id")
          .execute();
      System.out.println("File ID: " + file.getId());
      return file.getId();
    } catch (IOException e) {
      // TODO(developer) - handle error appropriately
      System.err.println("Unable to upload file: " + e);
      throw e;
    }
  }

  /**
   * Function to take a file extension and return the google drive compatable mimetype
   * 
   * @param extension The extension of the file
   * @return The mimetype of the file
   */
  public static String getMimeType(String extension){
    //https://stackoverflow.com/questions/11894772/google-drive-mime-types-listing
    //Used chatgpt to autogenerate switch statement based on these values
    String mimeType;
    switch(extension.toLowerCase()){
      case "xls":
          mimeType = "application/vnd.ms-excel";
          break;
      case "xlsx":
          mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
          break;
      case "xml":
          mimeType = "text/xml";
          break;
      case "ods":
          mimeType = "application/vnd.oasis.opendocument.spreadsheet";
          break;
      case "csv":
          mimeType = "text/plain";
          break;
      case "tmpl":
          mimeType = "text/plain";
          break;
      case "pdf":
          mimeType = "application/pdf";
          break;
      case "php":
          mimeType = "application/x-httpd-php";
          break;
      case "jpg":
          mimeType = "image/jpeg";
          break;
      case "png":
          mimeType = "image/png";
          break;
      case "gif":
          mimeType = "image/gif";
          break;
      case "bmp":
          mimeType = "image/bmp";
          break;
      case "txt":
          mimeType = "text/plain";
          break;
      case "doc":
          mimeType = "application/msword";
          break;
      case "js":
          mimeType = "text/js";
          break;
      case "swf":
          mimeType = "application/x-shockwave-flash";
          break;
      case "mp3":
          mimeType = "audio/mpeg";
          break;
      case "zip":
          mimeType = "application/zip";
          break;
      case "rar":
          mimeType = "application/rar";
          break;
      case "tar":
          mimeType = "application/tar";
          break;
      case "arj":
          mimeType = "application/arj";
          break;
      case "cab":
          mimeType = "application/cab";
          break;
      case "html":
          mimeType = "text/html";
          break;
      case "htm":
          mimeType = "text/html";
          break;
      case "default":
          mimeType = "application/octet-stream";
          break;
      case "folder":
          mimeType = "application/vnd.google-apps.folder";
          break;
      default:
          mimeType = "unknown";
          break;
    }
    return mimeType;
  }
}