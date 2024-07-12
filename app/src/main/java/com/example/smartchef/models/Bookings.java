package com.example.smartchef.models;

public class Bookings {

    public String bookingId;
    public String customerId;
    public String chefId;
    public String customerName;
    public String chefName;
    public String description;
    public String date;
    public String time;
    public String status;
    public String customerEmail;
    public String chefEmail;

    public Bookings() {
    }

    public Bookings(String bookingId, String customerId, String chefId, String customerName, String chefName, String description, String date, String time, String status, String customerEmail, String chefEmail) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.chefId = chefId;
        this.customerName = customerName;
        this.chefName = chefName;
        this.description = description;
        this.date = date;
        this.time = time;
        this.status = status;
        this.customerEmail = customerEmail;
        this.chefEmail = chefEmail;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getChefEmail() {
        return chefEmail;
    }

    public void setChefEmail(String chefEmail) {
        this.chefEmail = chefEmail;
    }
}
