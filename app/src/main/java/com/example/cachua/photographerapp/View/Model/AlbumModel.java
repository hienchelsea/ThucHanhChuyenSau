package com.example.cachua.photographerapp.View.Model;

public class AlbumModel {
    String id;
    String description;
    String locationId;
    String name;
    String tag;
    String userId;

    public AlbumModel(String id, String description, String locationId, String name, String tag, String userId) {
        this.id = id;
        this.description = description;
        this.locationId = locationId;
        this.name = name;
        this.tag = tag;
        this.userId = userId;
    }

    public AlbumModel(String description, String locationId, String name, String tag, String userId) {
        this.description = description;
        this.locationId = locationId;
        this.name = name;
        this.tag = tag;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
