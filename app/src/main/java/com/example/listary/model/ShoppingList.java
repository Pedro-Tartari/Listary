package com.example.listary.model;

import java.util.ArrayList;

public class ShoppingList {

    private ArrayList<Product> productList = new ArrayList<>();
    private String name;
    private int id;
    private float totalPrice;

    public ShoppingList() {
    }

    public ShoppingList(String name, Float totalPrice, int id) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.id = id;

    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
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
}
