package com.example.listary.view.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.listary.R;
import com.example.listary.model.ReservedId;
import com.example.listary.view.Pantry.PantryActivity;
import com.example.listary.view.createProduct.SearchProductActivity;
import com.example.listary.view.loginForm.Login;
import com.example.listary.view.newList.NewListActivity;
import com.example.listary.view.historic.HistoricActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MenuListaryActivity extends AppCompatActivity {

    private String uID;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listary);
        uID = user.getUid();


        db.collection("data").document(uID).collection("reservedID")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() > 0) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Log.d("Resultado", "Room already exists, start the chat");
                                }
                            } else {
                                DocumentReference idReference = db.collection("data")
                                        .document(uID).collection("reservedID")
                                        .document("reservedProductId");

                                ReservedId reservedId = new ReservedId(0);
                                idReference.set(reservedId);
                            }
                        } else {
                            Log.d("FTAG", "Error getting documents: ", task.getException());
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
                startActivity(new Intent(MenuListaryActivity.this, Login.class));
            default:
                return true;
        }
    }

    public void openSearchProduct(View view) {
        Intent intent = new Intent(this, SearchProductActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void openNewList(View view) {
        Intent intent = new Intent(this, NewListActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void openHistoric(View view) {
        Intent intent = new Intent(this, HistoricActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void  openPantry(View view) {
        Intent intent = new Intent(this, PantryActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

    }





}