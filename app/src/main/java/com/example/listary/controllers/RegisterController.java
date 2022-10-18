package com.example.listary.controllers;
import android.util.Patterns;

import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterController {

    private FirebaseAuth auth;
    private String userName, email, password;
    private FirebaseFirestore db;

    public boolean checkAllFields(EditText edUserName, EditText edEmail, EditText edPassword) {

        userName = edUserName.getText().toString();
        email = edEmail.getText().toString();
        password = edPassword.getText().toString();

        if (userName.length() == 0) {
            edUserName.setError("Esse campo é obrigatório");
            return false;
        }

        if (email.length() == 0) {
            edEmail.setError("Esse campo é obrigatório");
            return false;

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Informe um email válido");
            return false;
        }

        if (password.length() == 0) {
            edPassword.setError("Esse campo é obrigatório");
            return false;

        } else if (password.length() < 6) {
            edPassword.setError("A senha deve conter ao menos 6 caracteres");
            return false;
        }
        return true;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
