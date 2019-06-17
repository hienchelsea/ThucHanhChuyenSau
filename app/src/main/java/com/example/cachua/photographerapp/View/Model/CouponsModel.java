package com.example.cachua.photographerapp.View.Model;

import java.sql.Timestamp;

public class CouponsModel {
    String id;
    String code;
    String description;
    Timestamp endAt;
    String name;
    Timestamp startAt;
    Timestamp updatedAt;
    String userId;
    float value;


    public CouponsModel(String id, String code, String description, Timestamp endAt, String name, Timestamp startAt, Timestamp updatedAt, String userId, float value) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.endAt = endAt;
        this.name = name;
        this.startAt = startAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
