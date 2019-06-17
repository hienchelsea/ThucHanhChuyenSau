package com.example.cachua.photographerapp.View.Model;

public class ReviewsModel {
    String id;
    String photographerId;
    double rating;
    String review;
    String userId;

    public ReviewsModel(String id, String photographerId, double rating, String review, String userId) {
        this.id = id;
        this.photographerId = photographerId;
        this.rating = rating;
        this.review = review;
        this.userId = userId;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
