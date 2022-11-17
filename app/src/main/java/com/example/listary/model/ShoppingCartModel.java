package com.example.listary.model;

public class ShoppingCartModel {

    private ShoppingList shoppingList;
    private double totalPrice;
    private int id;

    public ShoppingCartModel() {
    }

    public ShoppingCartModel(ShoppingList shoppingList, double totalPrice, int id) {
        this.shoppingList = shoppingList;
        this.totalPrice = totalPrice;
        this.id = id;
    }

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
