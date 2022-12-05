package com.example.listary.view.resetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listary.R;
import com.example.listary.controllers.PasswordController;
import com.example.listary.view.loginForm.LoginActivity;


public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edResetEmail;
    private Button btnResetPassword;
    private PasswordController passwordController = new PasswordController();

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().hide();
        setViewId();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                if (passwordController.CheckAllFields(edResetEmail)) {
                    Toast.makeText(ResetPasswordActivity.this, "Verifique seu email", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ResetPasswordActivity.this, "Algo deu errado, tente novamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setViewId() {
        edResetEmail = findViewById(R.id.edResetEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);
    }
}
