package com.example.wagba.models;

public class CartModel {
    private String name;
    private String price;
    private int numberOfItems;

    public CartModel () {}

    public CartModel(String name, String price, int numberOfItems) {
        this.name = name;
        this.price = price;
        this.numberOfItems = numberOfItems;
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
