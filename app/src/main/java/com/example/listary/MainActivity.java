package com.example.listary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.listary.model.Firestore;
import com.example.listary.view.loginForm.LoginActivity;
import com.example.listary.view.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {

    private Firestore user = new Firestore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (user.getUser() != null){
            Toast.makeText(MainActivity.this, "Sucesso !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }else{
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }
    }
}