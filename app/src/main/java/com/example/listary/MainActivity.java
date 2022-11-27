package com.example.listary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.example.listary.view.loginForm.LoginActivity;
import com.example.listary.view.menu.MenuListaryActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Toast.makeText(MainActivity.this, "Sucesso !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MenuListaryActivity.class));
            finish();
        }else{
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }
    }
}