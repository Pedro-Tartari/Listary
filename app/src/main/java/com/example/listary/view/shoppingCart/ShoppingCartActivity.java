package com.example.listary.view.shoppingCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.listary.R;
import com.example.listary.adapters.CartAdapter;
import com.example.listary.controllers.ShoppingCartController;
import com.example.listary.interfaces.Callback;
import com.example.listary.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CartAdapter cartAdapter;
    private List<ShoppingList> acProductList = new ArrayList<>();

    private ShoppingCartController shoppingCartController = new ShoppingCartController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        this.setTitle(getResources().getString(R.string.carrinho));

        getDataFromFirestore();
        buildRecyclerView();
    }

    private void getDataFromFirestore() {
        shoppingCartController.getDataFromDatabase(new Callback() {
            @Override
            public void onCallback(Object modelClass) {
                acProductList.add((ShoppingList) modelClass);
            }
        });
    }

    private void buildRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cartAdapter = new CartAdapter(acProductList, this);
        mRecyclerView.setAdapter(cartAdapter);
    }
}