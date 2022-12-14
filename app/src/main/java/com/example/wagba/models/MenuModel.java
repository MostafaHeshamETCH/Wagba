package com.example.wagba.models;

import java.io.Serializable;

public class MenuModel implements Serializable {
    private String name;
    private String desc;
    private String imageUrl;
    private String price;

    public String getName() {
        return name;
    }

    public MenuModel(){} // for firebase fetch

    public MenuModel(String name, String desc, String imageUrl, String price) {
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
}
