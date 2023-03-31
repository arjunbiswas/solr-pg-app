package com.example.demo.messages;

import java.sql.Timestamp;

public class ResponseMessage {
    private String message;

    private String fileName;
    private String downloadUri;
    private long size;
    private Timestamp fileReceivedTimeStamp;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Timestamp getFileReceivedTimeStamp() {
        return fileReceivedTimeStamp;
    }

    public void setFileReceivedTimeStamp(Timestamp fileReceivedTimeStamp) {
        this.fileReceivedTimeStamp = fileReceivedTimeStamp;
    }
}