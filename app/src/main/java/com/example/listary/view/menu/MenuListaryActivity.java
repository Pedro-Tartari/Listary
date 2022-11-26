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

import com.example.listary.R;
import com.example.listary.controllers.MenuController;

import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.example.listary.view.loginForm.Login;
import com.example.listary.view.newList.NewListActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuListaryActivity extends AppCompatActivity implements View.OnClickListener {

    private String uID;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MenuController menuController = new MenuController();
    private AlertDialog alertDialog;

    private CardView cvNewList, cvProduct, cvHistoric, cvPantry;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle("Listary");
        alert.setMessage("Você tem certeza que deseja sair do aplicativo?");
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog = alert.create();
        alertDialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        uID = user.getUid();

        cvNewList= findViewById(R.id.cvNewList);
        cvHistoric= findViewById(R.id.cvHistoric);
        cvProduct= findViewById(R.id.cvProduct);
        cvPantry= findViewById(R.id.cvPantry);

        cvNewList.setOnClickListener(this);
        cvHistoric.setOnClickListener(this);
        cvProduct.setOnClickListener(this);
        cvPantry.setOnClickListener(this);


        menuController.verifyReservedId(uID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header, menu);

        this.setTitle(getResources().getString(R.string.menu_listary));

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuListaryActivity.this, Login.class));
                finish();
            default:
                return true;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.cvNewList: intent = new Intent(this, NewListActivity.class);
            startActivity(intent);
            break;

            case R.id.cvHistoric: intent = new Intent(this, HistoricActivity.class);
                startActivity(intent);
                break;

            case R.id.cvProduct: intent = new Intent(this, SearchProductActivity.class);
                startActivity(intent);
                break;

            case R.id.cvPantry: intent = new Intent(this, PantryActivity.class);
                startActivity(intent);
                break;
        }
    }
}