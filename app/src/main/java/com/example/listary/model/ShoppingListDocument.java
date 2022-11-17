package com.example.listary.model;

import com.google.firebase.firestore.PropertyName;

public class ShoppingListDocument {

    public ShoppingList shoppingList;

    public ShoppingListDocument() {
    }

    public ShoppingListDocument(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    @PropertyName("shoppingList")
    public ShoppingList getShoppingList() {
        return shoppingList;
    }



    @PropertyName("serviceOrder")
    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}
