package com.example.listary.controllers;

import android.util.Patterns;
import android.widget.EditText;

import com.example.listary.model.Firestore;


public class PasswordController {

    private Firestore firestore = new Firestore();
    private String email;

    public boolean CheckAllFields(EditText edResetEmail) {
        email = edResetEmail.getText().toString();

        if (email.length() == 0) {
            edResetEmail.setError("Esse campo é obrigatório");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edResetEmail.setError("Informe um email válido");
            return false;
        }

        firestore.getAuth().sendPasswordResetEmail(email);
        return true;

    }
}
