package com.example.wagba.models;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {
    private String name, email;

    private ArrayList<CartModel> cart;
    private ArrayList<OrderModel> ordersHistory;

    public UserModel() {}

    public UserModel(String email, String name, ArrayList<CartModel> cart, ArrayList<OrderModel> ordersHistory) {
        this.email = email;
        this.name = name;
        this.cart = cart;
        this.ordersHistory = ordersHistory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CartModel> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartModel> cart) {
        this.cart = cart;
    }

    public ArrayList<OrderModel> getOrdersHistory() {
        return ordersHistory;
    }

    public void setOrdersHistory(ArrayList<OrderModel> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }
}
