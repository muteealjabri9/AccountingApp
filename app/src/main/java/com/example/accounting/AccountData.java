package com.example.accounting;

import java.io.Serializable;

public class AccountData implements Serializable {
    private int id;
    private int userId;
    private String details;
    private double price;

    public AccountData(int id, int userId, String details, double price) {
        this.id = id;
        this.userId = userId;
        this.details = details;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AccountData(int userId, String details, double price) {
        this.userId = userId;
        this.details = details;
        this.price = price;
    }
}
