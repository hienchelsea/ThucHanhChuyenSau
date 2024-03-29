package com.example.cachua.photographerapp.View.Model;

public class MessagesModel {
    int id;
    String message;
    String receiver;
    String sender;

    public MessagesModel(int id, String message, String receiver, String sender) {
        this.id = id;
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
