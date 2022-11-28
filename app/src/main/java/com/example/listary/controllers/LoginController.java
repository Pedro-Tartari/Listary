package com.example.listary.controllers;


import android.util.Patterns;
import android.widget.EditText;

import com.example.listary.model.Firestore;


public class LoginController {

    private Firestore connection = new Firestore();
    private String email,password;
    private boolean result;

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
}
