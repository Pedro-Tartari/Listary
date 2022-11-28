package com.example.listary.view.loginForm;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.listary.R;
import com.example.listary.controllers.LoginController;
import com.example.listary.view.menu.MenuActivity;
import com.example.listary.view.registerForm.RegisterActivity;
import com.example.listary.view.resetPassword.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{

    private TextView register, forgetPass;
    private EditText edEmailLogin,edPasswordLogin;
    private Button btnLoginUser;
    private FirebaseAuth mAuth;

    private LoginController loginController = new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
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
                    mAuth.signInWithEmailAndPassword(edEmailLogin.getText().toString(), edPasswordLogin.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "E-Mail ou Senha Incorretos", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                break;
            case R.id.forgetPass:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                finish();
                break;
        }
    }
}


