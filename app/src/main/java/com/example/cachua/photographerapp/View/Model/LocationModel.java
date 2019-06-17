package com.example.cachua.photographerapp.View.Model;

public class LocationModel {
    int id;
    String description;
    String name;
    String tag;

    public LocationModel(int id, String description, String name, String tag) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
