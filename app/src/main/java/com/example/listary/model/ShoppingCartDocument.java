package com.example.listary.model;

import com.google.firebase.firestore.PropertyName;

public class ShoppingCartDocument {

    private ShoppingList shoppingList;
    private double totalPrice;
    private int id;

    public ShoppingCartDocument() {
    }

    public ShoppingCartDocument(ShoppingList shoppingList, double totalPrice, int id) {
        this.shoppingList = shoppingList;
        this.totalPrice = totalPrice;
        this.id = id;
    }

    @PropertyName("shoppingList")
    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
