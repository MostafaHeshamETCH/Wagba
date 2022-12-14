package com.example.wagba.models;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantModel implements Serializable {

    private String name;
    private String imageUrl;
    private String rating;
    private String time;
    private ArrayList<MenuModel> menu;

    public ArrayList<MenuModel> getMenu() {
        return menu;
    }

    public RestaurantModel(){} // for firebase fetch

    public void setMenu(ArrayList<MenuModel> menu) {
        this.menu = menu;
    }

    public RestaurantModel(String name, String imageUrl, String rating, String time, ArrayList<MenuModel> menu) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.time = time;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
