package com.example.wagba.models;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderModel implements Serializable {
    private String name;
    private String imageUrl;
    private String price;
    private String date;
    private String time;
    private ArrayList<CartModel> orderDetails;
    private String clientUid;
    private String clientName;
    private String uniYear;
    private String whichGate;
    private String deliveryTimes;
    private String status;

    public OrderModel(String name, String imageUrl, String price, String date, String time, ArrayList<CartModel> orderDetails, String clientUid, String clientName, String address, String whichGate, String deliveryTimes, String status) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.date = date;
        this.time = time;
        this.orderDetails = orderDetails;
        this.clientUid = clientUid;
        this.clientName = clientName;
        this.uniYear = address;
        this.whichGate = whichGate;
        this.deliveryTimes = deliveryTimes;
        this.status = status;
    }

    public ArrayList<CartModel> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<CartModel> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderModel() {}

    public String getClientUid() {
        return clientUid;
    }

    public void setClientUid(String clientUid) {
        this.clientUid = clientUid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUniYear() {
        return uniYear;
    }

    public void setUniYear(String uniYear) {
        this.uniYear = uniYear;
    }

    public String getWhichGate() {
        return whichGate;
    }

    public void setWhichGate(String whichGate) {
        this.whichGate = whichGate;
    }

    public String getDeliveryTimes() {
        return deliveryTimes;
    }

    public void setDeliveryTimes(String deliveryTimes) {
        this.deliveryTimes = deliveryTimes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
