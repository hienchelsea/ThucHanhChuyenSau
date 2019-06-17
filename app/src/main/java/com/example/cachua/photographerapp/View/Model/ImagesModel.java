package com.example.cachua.photographerapp.View.Model;

import java.util.ArrayList;

public class ImagesModel {
    String id;
    String albumId;
    String caption;
    String path;
    ArrayList<String> tag;
    String userId;

    public ImagesModel(String id, String albumId, String caption, String path, ArrayList<String> tag, String userId) {
        this.id = id;
        this.albumId = albumId;
        this.caption = caption;
        this.path = path;
        this.tag = tag;
        this.userId = userId;
    }

    public ImagesModel(String albumId, String caption, String path, ArrayList<String> tag, String userId) {
        this.albumId = albumId;
        this.caption = caption;
        this.path = path;
        this.tag = tag;
        this.userId = userId;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
