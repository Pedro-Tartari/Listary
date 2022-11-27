package com.example.listary.view.loginForm;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.listary.R;
import com.example.listary.controllers.LoginController;
import com.example.listary.view.menu.MenuListaryActivity;
import com.example.listary.view.registerForm.RegisterActivity;
import com.example.listary.view.resetPassword.ResetPasswordActivity;


public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{

    private TextView register, forgetPass;
    private EditText edEmailLogin,edPasswordLogin;
    private Button btnLoginUser;

    private LoginController loginController = new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();
        setViewId();
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    private void setViewId() {
        edEmailLogin = findViewById(R.id.edEmailLogin);
        edPasswordLogin = findViewById(R.id.edPasswordLogin);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        btnLoginUser = (Button) findViewById(R.id.btnLoginUser);
        btnLoginUser.setOnClickListener(this);

        forgetPass = (TextView) findViewById(R.id.forgetPass);
        forgetPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.btnLoginUser:

                if (loginController.checkAllFields(edEmailLogin,edPasswordLogin)) {
                    signIn();
                    finish();
                }
                break;
            case R.id.forgetPass:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                finish();
                break;
        }
    }

    private void signIn() {
            if(loginController.signInFirestore(loginController.getEmail(), loginController.getPassword())){
                startActivity(new Intent(LoginActivity.this, MenuListaryActivity.class));
                finish();
            }
            else{
                Toast.makeText(LoginActivity.this, "E-mail e/ou Senha Inválida !", Toast.LENGTH_LONG).show();
            }
    }
}


