package com.example.wagba.models;

public class OrderDetailsModel {
    private String name, price, imageUrl;
    private int numberOfItems;

    public OrderDetailsModel() {}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public OrderDetailsModel(String name, String price, int numberOfItems, String imageUrl) {
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
}
