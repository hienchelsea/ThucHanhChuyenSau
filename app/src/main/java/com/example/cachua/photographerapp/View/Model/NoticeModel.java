package com.example.cachua.photographerapp.View.Model;

public class NoticeModel {
    String id;
    String collection;
    String documentId;
    String message;
    String type;
    String userId;

    public NoticeModel(String id, String collection, String documentId, String message, String type, String userId) {
        this.id = id;
        this.collection = collection;
        this.documentId = documentId;
        this.message = message;
        this.type = type;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
