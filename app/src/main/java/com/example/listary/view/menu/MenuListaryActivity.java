package com.example.listary.view.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.listary.R;
import com.example.listary.controllers.MenuController;

import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.loginForm.Login;
import com.example.listary.view.newList.NewListActivity;
import com.example.listary.view.historic.HistoricActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuListaryActivity extends AppCompatActivity {

    private String uID;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MenuController menuController = new MenuController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listary);
        uID = user.getUid();

        menuController.verifyReservedId(uID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.novaLista:
                intent = new Intent(this, NewListActivity.class);
                startActivity(intent);
                return true;

            case R.id.consultarProduto:
                intent = new Intent(this, SearchProductActivity.class);
                startActivity(intent);
                return true;

            case R.id.historic:
                intent = new Intent(this, HistoricActivity.class);
                startActivity(intent);
                return true;

            case R.id.despensa:
                intent = new Intent(this, PantryActivity.class);
                startActivity(intent);
                return true;

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuListaryActivity.this, Login.class));
            default:
                return true;
        }
    }

    public void openSearchProduct(View view) {
        Intent intent = new Intent(this, SearchProductActivity.class);
        startActivity(intent);

    }

    public void openNewList(View view) {
        Intent intent = new Intent(this, NewListActivity.class);
        startActivity(intent);

    }

    public void openHistoric(View view) {
        Intent intent = new Intent(this, HistoricActivity.class);
        startActivity(intent);

    }

    public void  openPantry(View view) {
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent);

    }
}