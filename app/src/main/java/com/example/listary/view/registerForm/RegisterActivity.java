package com.example.listary.view.registerForm;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listary.R;
import com.example.listary.controllers.RegisterController;
import com.example.listary.view.loginForm.LoginActivity;


public class RegisterActivity extends AppCompatActivity {

    private EditText edUserName,edEmail,edPassword;
    private Button btnRegister;
    private RegisterController registerController = new RegisterController();
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        setViewId();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerController.checkAllFields(edUserName, edEmail, edPassword)) {
                    view.startAnimation(buttonClick);
                    createNewUser();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    private void setViewId() {
        edUserName = findViewById(R.id.edUserName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }


    private void createNewUser() {
        if(registerController.createUserFirestore()){
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }else{
            Toast.makeText(RegisterActivity.this, "Email ja cadastrado", Toast.LENGTH_LONG).show();
        }
    }
}
