package com.example.demoapp;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.demoapp.adapter.ConvictAdapter;
import com.example.demoapp.models.CaseReport;
import com.example.demoapp.models.Convict;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.otaliastudios.printer.DocumentView;
import com.otaliastudios.printer.PdfPrinter;
import com.otaliastudios.printer.PrintCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import pub.devrel.easypermissions.EasyPermissions;

public class DetailCaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private DocumentView documentView;
    private PdfPrinter mPrinter;
    private String caseId;
    private FirebaseFirestore db;
    private RecyclerView convictRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        documentView = findViewById(R.id.documentView);
        convictRecycler = findViewById(R.id.convictRecycler);

        caseId = getIntent().getStringExtra("caseId");

        convictRecycler.setLayoutManager(new LinearLayoutManager(this));
        final List<Convict> convicts = new ArrayList<>();

        FirebaseFirestoreSettings firestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(firestoreSettings);

        final TextView textId = findViewById(R.id.textId);
        final TextView textStation = findViewById(R.id.textStation);
        final TextView textOfficer = findViewById(R.id.textOfficer);
        final TextView textCaseDetails = findViewById(R.id.textCaseDetails);
        final TextView textArrestDone = findViewById(R.id.textArrestDone);
        final TextView textNumConvicts = findViewById(R.id.textNumConvicts);
        final TextView textEvidence = findViewById(R.id.textEvidence);
        final TextView textDate = findViewById(R.id.textDate);
        final TextView textTime = findViewById(R.id.textTime);
        final TextView textOtherDetails = findViewById(R.id.textOtherDetails);



        db.collection("cases").document(caseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    CaseReport report = task.getResult().toObject(CaseReport.class);
                    assert report != null;
                    textId.setText(caseId);
                    textArrestDone.setText(report.ArrestDone);
                    textNumConvicts.setText(String.valueOf(report.noOfConvicts));
                    textEvidence.setText(report.evidenceDetails);
                    textDate.setText(report.date);
                    textTime.setText(report.time);
                    textStation.setText(report.station);
                    textOfficer.setText(report.officer);
                    textCaseDetails.setText(report.caseDetails);
                    textOtherDetails.setText(report.otherInformation);
                } else {

                }
            }
        });


        db.collection("convicts").whereEqualTo("caseid", caseId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e == null) {

                    convicts.clear();

                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Convict convict = document.toObject(Convict.class);
                        convicts.add(convict);
                    }
                    ConvictAdapter adapter = new ConvictAdapter(DetailCaseActivity.this, R.layout.view_convict_card, convicts);
                    convictRecycler.setAdapter(adapter);
                } else {
                    Toast.makeText(DetailCaseActivity.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
                    EasyPermissions.requestPermissions(DetailCaseActivity.this, "Grant Read and Write", 482, perms);
                } else {
                    printReport();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        printReport();
    }

    private void printReport() {
        PrintCallback mPrintCallback = new PrintCallback() {
            @Override
            public void onPrint(String id, File file) {
                Toast.makeText(DetailCaseActivity.this, "Printing", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrintFailed(String id, Throwable error) {
                Toast.makeText(DetailCaseActivity.this, "Printing Failed", Toast.LENGTH_SHORT).show();
            }
        };
        mPrinter = new PdfPrinter(documentView, mPrintCallback);
        mPrinter.setPrintPageBackground(true);

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        mPrinter.print("id", directory, "document_" + ((int) System.currentTimeMillis()) + ".pdf");
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "cannot print without permission", Toast.LENGTH_SHORT).show();
    }
}
