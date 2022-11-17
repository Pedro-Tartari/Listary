package com.example.listary.view.historic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.listary.R;
import com.example.listary.adapters.HistoricViewAdapter;
import com.example.listary.model.ShoppingList;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HistoricView extends AppCompatActivity {

    private TextView tvHistoricListName;
    private RecyclerView rvHistoricViewList;
    private Button btConfirmView;
    private HistoricViewAdapter historicViewAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        rvHistoricViewList.setLayoutManager(new GridLayoutManager(this,2));
        rvHistoricViewList.setHasFixedSize(true);
        rvHistoricViewList.addItemDecoration(dividerItemDecoration);



        FirestoreRecyclerOptions<ShoppingList> options =
                new FirestoreRecyclerOptions.Builder<ShoppingList>()
                        .build();

        historicViewAdapter = new HistoricViewAdapter(options);
        rvHistoricViewList.setAdapter(historicViewAdapter);
        historicViewAdapter.notifyDataSetChanged();

        btConfirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        historicViewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        historicViewAdapter.stopListening();
    }
}
