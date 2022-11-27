package com.example.listary.view.historic;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.listary.R;
import com.example.listary.adapters.HistoricViewAdapter;
import com.example.listary.controllers.HistoricController;
import com.example.listary.interfaces.Callback;

public class HistoricViewActivity extends AppCompatActivity {

    private TextView tvHistoricListName;
    private RecyclerView rvHistoricViewList;
    private Button btConfirmView;
    private HistoricViewAdapter historicViewAdapter;

    private HistoricController historicController = new HistoricController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_view_list);


        String documentId = getIntent().getStringExtra("documentId");
        getDataFromFirestore(documentId);

        tvHistoricListName = findViewById(R.id.tvHistoricListName);
        rvHistoricViewList = findViewById(R.id.rvHistoricView);
        btConfirmView = findViewById(R.id.btConfirmView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,

                DividerItemDecoration.VERTICAL);

        rvHistoricViewList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvHistoricViewList.setHasFixedSize(true);
        rvHistoricViewList.addItemDecoration(dividerItemDecoration);

        tvHistoricListName.setText(historicController.getListName());

        btConfirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDataFromFirestore(String documentId) {

        historicController.getDataFromDatabase(documentId, new Callback() {
            @Override
            public void onCallback(Object modelClass) {
                historicViewAdapter = (HistoricViewAdapter) modelClass;
                rvHistoricViewList.setAdapter(historicViewAdapter);
                historicViewAdapter.notifyDataSetChanged();
            }
        });

    }
}
