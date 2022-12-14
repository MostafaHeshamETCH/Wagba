package com.example.wagba.models;

import java.util.ArrayList;

public class OrderModel {
    private String name;
    private String imageUrl;
    private String price;
    private String date;
    private String time;
    private ArrayList<OrderDetailsModel> orderDetails;

    public ArrayList<OrderDetailsModel> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetailsModel> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderModel() {}

    public OrderModel(String name, String imageUrl, String price, String date, String time) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
}
