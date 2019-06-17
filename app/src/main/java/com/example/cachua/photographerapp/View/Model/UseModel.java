package com.example.cachua.photographerapp.View.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UseModel {

    String id;
    String bio;
    String birth;
    String email;
    String location;
    String name;
    String phone;
    Double rating;
    String role;
    String avatar;


    public UseModel(String id, String bio, String birth, String email, String location, String name, String phone, Double rating, String role, String avatar) {
        this.id = id;
        this.bio = bio;
        this.birth = birth;
        this.email = email;
        this.location = location;
        this.name = name;
        this.phone = phone;
        this.rating = rating;
        this.role = role;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
