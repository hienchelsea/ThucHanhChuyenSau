package com.example.cachua.photographerapp.View.Model;

public class FavoritesModel {
    String id;
    String photographerId;
    String userId;

    public FavoritesModel(String id, String photographerId, String userId) {
        this.id = id;
        this.photographerId = photographerId;
        this.userId = userId;
    }

    public FavoritesModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
