package com.example.listary.view.historic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.listary.R;
import com.example.listary.adapters.HistoricViewAdapter;
import com.example.listary.model.ShoppingCartModel;
import com.example.listary.model.ShoppingList;
import com.example.listary.model.ShoppingListDocument;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HistoricView extends AppCompatActivity {

    private TextView tvHistoricListName;
    private RecyclerView rvHistoricViewList;
    private Button btConfirmView;
    private HistoricViewAdapter historicViewAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private CollectionReference docRef =
            db.collection("data")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("shoppingList");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_view_list);

        tvHistoricListName = findViewById(R.id.tvHistoricListName);
        rvHistoricViewList = findViewById(R.id.rvHistoricView);
        btConfirmView = findViewById(R.id.btConfirmView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,

                DividerItemDecoration.VERTICAL);

        rvHistoricViewList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvHistoricViewList.setHasFixedSize(true);
        rvHistoricViewList.addItemDecoration(dividerItemDecoration);

        String documentId = getIntent().getStringExtra("documentId");

        DocumentReference idRef =
                db.collection("data")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("shoppingList")
                        .document(documentId);

        idRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ShoppingList shoppingDocument = document.toObject(ShoppingListDocument.class).shoppingList;
                        tvHistoricListName.setText(shoppingDocument.getName());
                        historicViewAdapter = new HistoricViewAdapter(HistoricView.this, shoppingDocument.getProductList() );
                        rvHistoricViewList.setAdapter(historicViewAdapter);
                        historicViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        btConfirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
