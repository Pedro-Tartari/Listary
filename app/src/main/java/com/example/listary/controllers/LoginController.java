package com.example.listary.controllers;

import android.util.Patterns;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginController {

    private FirebaseAuth auth;
    private String email,password;

    private boolean isAllFieldsChecked = false;

    public boolean checkAllFields(EditText edEmailLogin, EditText edPasswordLogin) {
        email = edEmailLogin.getText().toString();
        password = edPasswordLogin.getText().toString();

        if (email.length() == 0) {
            edEmailLogin.setError("Esse campo é obrigatório");
            return false;

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmailLogin.setError("Informe um email válido");
            return false;
        }

        if (password.length() == 0) {
            edPasswordLogin.setError("Esse campo é obrigatório");
            return false;

        } else if (password.length() < 6) {
            edPasswordLogin.setError("A senha deve conter ao menos 6 caracteres");
            return false;
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
