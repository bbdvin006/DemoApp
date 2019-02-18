package com.example.demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.adapter.CaseAdapter;
import com.example.demoapp.models.CaseReport;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class DetailStationActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textStation = findViewById(R.id.textStation);
        TextView textAddress = findViewById(R.id.textAddress);
        TextView textContact = findViewById(R.id.textContact);

        if (getIntent() == null) {
            Toast.makeText(this, "error loading data", Toast.LENGTH_SHORT).show();
        }
        String stationName = getIntent().getStringExtra("station_name");
        String stationAddr = getIntent().getStringExtra("station_address");
        String stationContact = getIntent().getStringExtra("station_contact");
        textAddress.setText(stationAddr);
        textStation.setText(stationName);
        textContact.setText(stationContact);
        final RecyclerView caseRecycler = findViewById(R.id.caseRecyler);
        caseRecycler.setLayoutManager(new LinearLayoutManager(this));
        final List<CaseReport> cases = new ArrayList<>();

        FirebaseFirestoreSettings firestoreSettings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true).build();

        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(firestoreSettings);

        db.collection("cases").whereEqualTo("station", stationName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e == null) {
                    cases.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        CaseReport report = document.toObject(CaseReport.class);
                        report.setCaseId(document.getId());
                        cases.add(report);

                    }
                    CaseAdapter adapter = new CaseAdapter(DetailStationActivity.this, R.layout.view_case_card, cases);
                    caseRecycler.setAdapter(adapter);
                } else {
                    Toast.makeText(DetailStationActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
