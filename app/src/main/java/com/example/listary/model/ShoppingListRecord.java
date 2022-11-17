package com.example.listary.model;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class ShoppingListRecord {

    public ArrayList<ShoppingList> shoppingListsRecord;

    public ShoppingListRecord() {
    }

    public ShoppingListRecord(ArrayList<ShoppingList> shoppingListsRecord) {
        this.shoppingListsRecord = shoppingListsRecord;
    }

    @PropertyName("shoppingList")
    public ArrayList<ShoppingList> getShoppingListsRecord() {
        return shoppingListsRecord;
    }

    @PropertyName("shoppingList")
    public void setShoppingListsRecord(ArrayList<ShoppingList> shoppingListsRecord) {
        this.shoppingListsRecord = shoppingListsRecord;
    }
}
