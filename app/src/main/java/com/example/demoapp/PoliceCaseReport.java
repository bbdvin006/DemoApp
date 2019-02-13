package com.example.demoapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
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
    private EditText otherInformation;
    private Button submitReport;
    private EditText noOfVictims;
    private FirebaseAuth mAuth;
    private DatabaseReference mref;
    private FirebaseDatabase database;
    private EditText date;
    private EditText time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_case_report);
        reportOfficerName = findViewById(R.id.reportOfficerName);
        policeStationList = findViewById(R.id.policeStationList);
        addressOfCrime = findViewById(R.id.addressOfCrime);
        crimeCommitted = findViewById(R.id.crimeCommitted);
        evidenceOfCrime = findViewById(R.id.evidenceOfCrime);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
//        yesArrested = findViewById(R.id.yesArrested);
//        notArrested = findViewById(R.id.notArrested);
//        notConfirmed = findViewById(R.id.notConfirmed);
        noOfVictims = findViewById(R.id.noOfVictims);
        otherInformation = findViewById(R.id.otherInformation);

        submitReport = findViewById(R.id.submitReport);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mref = database.getReference();

        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateform()) {
                    addDataToFirebase();
                }
            }
        });


    }



    private boolean validateform() {

        boolean valid = true;
        String nameOfOfficer = reportOfficerName.getText().toString();
        String crimeAddress = addressOfCrime.getText().toString();
        String committedCrime = crimeCommitted.getText().toString();
        String crimeEvidence = evidenceOfCrime.getText().toString();
        String cDate = date.getText().toString();
        String ctime = time.getText().toString();
        String victimsNo = noOfVictims.getText().toString();

        if(TextUtils.isEmpty(nameOfOfficer)){
            reportOfficerName.setError("Enter Officer Name!");
            return false;
        }
        else{
            valid = true;
            reportOfficerName.setError(null);
        }

        if(TextUtils.isEmpty(crimeAddress)){
            addressOfCrime.setError("Enter Address Of Crime scene!");
            return false;
        }
        else{
            valid = true;
            addressOfCrime.setError(null);
        }

        if(TextUtils.isEmpty(committedCrime)){
            crimeCommitted.setError("Enter the crime committed!");
            return false;
        }
        else{
            valid = true;
            crimeCommitted.setError(null);
        }
        if(TextUtils.isEmpty(crimeEvidence)){
            evidenceOfCrime.setError("Enter Officer Name!");
            return false;
        }
        else{
            valid = true;
            evidenceOfCrime.setError(null);
        }

        if(TextUtils.isEmpty(victimsNo)){
            noOfVictims.setError("Enter convicts number Here!");
            return false;
        }
        else{
            valid = true;
            noOfVictims.setError(null);
        }

        if(TextUtils.isEmpty(cDate)){
            date.setError("Enter Date of crime Here!");
            return false;
        }
        else{
            valid = true;
            date.setError(null);
        }
        if(TextUtils.isEmpty(ctime)){
            time.setError("Enter Time of crime Here!");
            return false;
        }
        else{
            valid = true;
            time.setError(null);
        }

        return valid;
    }

    private void addDataToFirebase() {

        String nameOfOfficer = reportOfficerName.getText().toString();
        String crimeAddress = addressOfCrime.getText().toString();
        String committedCrime = crimeCommitted.getText().toString();
        String crimeEvidence = evidenceOfCrime.getText().toString();
        String information = otherInformation.getText().toString();
        String victimsNo = noOfVictims.getText().toString();
        String cDate = date.getText().toString();
        String ctime = time.getText().toString();

        CaseReportInformation report = new CaseReportInformation();
        report.setJnameOfOfficer(nameOfOfficer);
        //report.setJpoliceStationName();
        report.setJaddressOfCrime(crimeAddress);
        report.setJevidence(crimeEvidence);
        report.setJcrimeCommited(committedCrime);
        //report.setjArrestDone();
        report.setJnoOfConvicts(victimsNo);
        report.setjOtherInformation(information);
        report.setJdate(cDate);
        report.setjTime(ctime);

        if(mAuth.getCurrentUser() != null){
            mref.child("User").child(mAuth.getCurrentUser().getUid())
                    .setValue(report, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if(databaseError == null){
                        Toast.makeText(PoliceCaseReport.this, "Report Is Saved Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(PoliceCaseReport.this, "Report Is Not Saved, Check Again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
