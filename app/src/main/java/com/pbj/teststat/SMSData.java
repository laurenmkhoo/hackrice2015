package com.pbj.teststat;

/**
 * This class represents SMS.
 */
public class SMSData {
    public static final String INBOX = "inbox";
    public static final String OUTBOX = "outbox";

    // Number from witch the sms was send
    private String number;
    // SMS text body
    private String body;
    private String id;
    private String time;
    private String folderName;
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getName(){return name;}

    public void setName(String contact){this.name = contact;}


}