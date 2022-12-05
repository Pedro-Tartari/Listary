package com.example.listary.view.menu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.example.listary.R;
import com.example.listary.controllers.MenuController;

import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.LoginActivity;
import com.example.listary.view.newList.NewListActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private MenuController menuController = new MenuController();
    private AlertDialog alertDialog;
    private CardView cvNewList,cvHistoric,cvProduct,cvPantry;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuController.verifyReservedId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header, menu);
        cvNewList= findViewById(R.id.cvNewList);
        cvHistoric= findViewById(R.id.cvHistoric);
        cvProduct= findViewById(R.id.cvProduct);
        cvPantry= findViewById(R.id.cvPantry);

        cvNewList.setOnClickListener(this);
        cvHistoric.setOnClickListener(this);
        cvProduct.setOnClickListener(this);
        cvPantry.setOnClickListener(this);

        this.setTitle(getResources().getString(R.string.menu_listary));

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                finish();
            default:
                return true;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openNewList(View view) {
        Intent intent = new Intent(this, NewListActivity.class);
        startActivity(intent);
        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        Intent intent;
        view.startAnimation(buttonClick);
        switch (view.getId()){

            case R.id.cvNewList: intent = new Intent(this, NewListActivity.class);
                view.startAnimation(buttonClick);
                startActivity(intent);
                finish();
                break;

            case R.id.cvHistoric: intent = new Intent(this, HistoricActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.cvProduct: intent = new Intent(this, SearchProductActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.cvPantry: intent = new Intent(this, PantryActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}