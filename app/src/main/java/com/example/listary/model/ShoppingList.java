package com.example.listary.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingList {

    private List<ProductItem> productList;
    private String name;
    private int id;
    private float totalPrice;
    private String date;

    public ShoppingList() {
    }

    public ShoppingList(String name, List productList, Float totalPrice, int id, String date) {
        this.name = name;
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.id = id;
        this.date = date;

    }

    public List<ProductItem> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductItem> productList) {
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
