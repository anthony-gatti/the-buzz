package edu.lehigh.cse216.rdm325.backend;

public class FileDTO {
    public String fData;
    public String mimeType;
    public String fName;

    /**
     * Constructor to set values of FileDTO
     * @param fData Data of the file
     * @param mimeType MimeType of the file
     * @param fName Name of the file
     */
    public FileDTO(String fData, String mimeType, String fName){
        this.fData = fData;
        this.mimeType = mimeType;
        this.fName = fName;
    }
    /**
     * Empty constructor to set all values to null
     */
    public FileDTO(){
        fData = null;
        mimeType = null;
        fName = null;
    }
    /**
     * Function to turn a fileDTO into a String
     * @return the string version of a fileDTO
     */
    public String makeString(){
        String returnValue = "fData:" + fData + " mimeType:" + mimeType + " fName:" + fName;
        return returnValue;
    }
    /**
     * Function to turn a string into a fileDTO
     * @param json The string value of a fileDTO
     * @return a FileDTO filled with values from the input string
     */
    public static FileDTO toDTO(String json){
        FileDTO returnValue = new FileDTO();
        String[] parts = json.split(" ");
        String[] dataParts = parts[0].split(":");
        String[] mimeParts = parts[1].split(":");
        String[] nameParts = parts[2].split(":");
        returnValue.fData = dataParts[1];
        returnValue.mimeType = mimeParts[1];
        returnValue.fName = nameParts[1];
        return returnValue;
    }
}