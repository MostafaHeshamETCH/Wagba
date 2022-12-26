package com.example.wagba.models;

import java.io.Serializable;

public class CartModel implements Serializable {
    private String name;
    private String price;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;
    private int numberOfItems;

    public CartModel () {}

    public CartModel(String name, String price, int numberOfItems, String imageUrl) {
        this.name = name;
        this.price = price;
        this.numberOfItems = numberOfItems;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
