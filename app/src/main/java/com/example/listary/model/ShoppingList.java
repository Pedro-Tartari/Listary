package com.example.listary.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {

    private List<Product> productList;
    private String name;
    private int id;
    private float totalPrice;

    public ShoppingList() {
    }

    public ShoppingList(String name, List productList, Float totalPrice, int id) {
        this.name = name;
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.id = id;

    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
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
