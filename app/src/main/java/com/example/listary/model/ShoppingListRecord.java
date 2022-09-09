package com.example.listary.model;

import java.util.ArrayList;

public class ShoppingListRecord {

    private ArrayList<ShoppingList> shoppingListsRecord = new ArrayList<>();

    public ShoppingListRecord() {
    }

    public ShoppingListRecord(ArrayList<ShoppingList> shoppingListsRecord) {
        this.shoppingListsRecord = shoppingListsRecord;
    }

    public ArrayList<ShoppingList> getShoppingListsRecord() {
        return shoppingListsRecord;
    }

    public void setShoppingListsRecord(ArrayList<ShoppingList> shoppingListsRecord) {
        this.shoppingListsRecord = shoppingListsRecord;
    }
}
