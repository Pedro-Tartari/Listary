package com.example.listary.view.Pantry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listary.R;
import com.example.listary.model.Pastry;
import com.example.listary.view.loginForm.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PantryActivity extends AppCompatActivity {

    private Button btnSavePantry;
    private EditText edPantry;
    private String uid;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pentry);
        uid = user.getUid();

        edPantry = findViewById(R.id.edPantry);

        btnSavePantry = findViewById(R.id.btnSavePentry);
        show();
        btnSavePantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
    }

    private void show() {
        DocumentReference docRef = db.collection("data").document(uid).collection("pantry").document("FQpG5QWFiJ4xStsiDING");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String banco = document.getString("description");
                        edPantry.setText(banco);
                        edPantry.setSelection(edPantry.getText().length());
                    } else {
                        Log.d("n", "No such document");
                    }
                } else {
                    Log.d("erro", "get failed with ", task.getException());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater formMenu = getMenuInflater();
        formMenu.inflate(R.menu.activity_header, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.novaLista:
                Toast.makeText(this, "Voce clicou em Novo lista", Toast.LENGTH_LONG).show();
                return true;


            case R.id.consultarListas:
                Toast.makeText(this, "Voce clicou em Consultar Listas", Toast.LENGTH_LONG).show();
                return true;

            case R.id.despensa:
                Toast.makeText(this, "Voce clicou em Despensa", Toast.LENGTH_LONG).show();
                return true;

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, Login.class));
            default:
                return true;
        }
    }

    private void post() {
        String description = edPantry.getText().toString();
        Pastry pantry = new Pastry(
            description
    );

        if (description.isEmpty()){
            Toast.makeText(this, "Campo não pode estar vazio", Toast.LENGTH_LONG).show();
        } else{
            DocumentReference documentReference = db.collection("data").document(uid).collection("pantry").document("FQpG5QWFiJ4xStsiDING");
            documentReference.set(pantry)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(PantryActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PantryActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}


