package com.example.wagba.models;

public class OrderDetailsModel {
    private String name, price;
    private int numberOfItems;

    public OrderDetailsModel() {}

    public OrderDetailsModel(String name, String price, int numberOfItems) {
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
}
