package com.example.smartchef.models;

import android.widget.TextView;

import java.io.Serializable;

public class Ratings implements Serializable {
    String userId;
    String rating;
    String reviewText;
    String timestamp;

    String userName;

    public Ratings() {
    }

    public Ratings(String userId, String rating, String reviewText, String timestamp, String userName) {
        this.userId = userId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.timestamp = timestamp;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
