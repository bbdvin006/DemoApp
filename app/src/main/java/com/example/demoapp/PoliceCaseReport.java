package com.example.demoapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PoliceCaseReport extends AppCompatActivity {

    private EditText reportOfficerName;
    private Spinner policeStationList;
    private EditText addressOfCrime;
    private EditText crimeCommitted;
    private EditText evidenceOfCrime;
    private CheckBox yesArrested;
    private CheckBox notArrested;
    private CheckBox notConfirmed;
    private Spinner victimlist;
    private EditText otherInformation;
    private Button submitReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_case_report);
        reportOfficerName = findViewById(R.id.reportOfficerName);
        policeStationList = findViewById(R.id.policeStationList);
        addressOfCrime = findViewById(R.id.addressOfCrime);
        crimeCommitted = findViewById(R.id.crimeCommitted);
        evidenceOfCrime = findViewById(R.id.evidenceOfCrime);
        yesArrested = findViewById(R.id.yesArrested);
        notArrested = findViewById(R.id.notArrested);
        notConfirmed = findViewById(R.id.notConfirmed);
        victimlist = findViewById(R.id.victimList);
        otherInformation = findViewById(R.id.otherInformation);

        submitReport = findViewById(R.id.submitReport);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mref = database.getReference();

        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataToFirebase();
            }
        });


    }

    private void addDataToFirebase() {
        String nameOfOfficer = reportOfficerName.getText().toString();
        String crimeAddress = addressOfCrime.getText().toString();
        String committedCrime = crimeCommitted.getText().toString();
        String crimeEvidence = evidenceOfCrime.getText().toString();
        String information = otherInformation.getText().toString();


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
