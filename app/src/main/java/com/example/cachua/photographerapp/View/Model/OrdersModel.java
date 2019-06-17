package com.example.cachua.photographerapp.View.Model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class OrdersModel {
    String id;
    String address;
    Map coupon;
    Timestamp endAt;
    String locationId;
    String note;
    Map option;
    String photographerId;
    Timestamp startAt;
    String status;
    Double total;
    String userId;

    public OrdersModel(String id, String address, Map coupon, Timestamp endAt, String locationId, String note, Map option, String photographerId, Timestamp startAt, String status, Double total, String userId) {
        this.id = id;
        this.address = address;
        this.coupon = coupon;
        this.endAt = endAt;
        this.locationId = locationId;
        this.note = note;
        this.option = option;
        this.photographerId = photographerId;
        this.startAt = startAt;
        this.status = status;
        this.total = total;
        this.userId = userId;
    }

    public OrdersModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map getCoupon() {
        return coupon;
    }

    public void setCoupon(Map coupon) {
        this.coupon = coupon;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Map getOption() {
        return option;
    }

    public void setOption(Map option) {
        this.option = option;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
