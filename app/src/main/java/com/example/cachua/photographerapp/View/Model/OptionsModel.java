package com.example.cachua.photographerapp.View.Model;

public class OptionsModel {
    String id;
    String description;
    String name;
    float price_per_day;
    float price_per_hour;
    int prints;
    int shots;
    String type;
    String userId;


//    String accessories;
//    String name;
//    String note;
//    String owner;
//    int priceHour;
//    int priceDay;
//    int prints;
//    int shots;
//    String type;


    public OptionsModel(String id, String description, String name, float price_per_day, float price_per_hour, int prints, int shots, String type, String userId) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price_per_day = price_per_day;
        this.price_per_hour = price_per_hour;
        this.prints = prints;
        this.shots = shots;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(float price_per_day) {
        this.price_per_day = price_per_day;
    }

    public float getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(float price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    public int getPrints() {
        return prints;
    }

    public void setPrints(int prints) {
        this.prints = prints;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
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
