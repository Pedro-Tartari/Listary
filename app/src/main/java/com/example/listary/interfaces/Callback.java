package com.example.listary.interfaces;

import com.example.listary.model.ProductItem;

public interface Callback<T>{

    void onCallback(T modelClass);
}
