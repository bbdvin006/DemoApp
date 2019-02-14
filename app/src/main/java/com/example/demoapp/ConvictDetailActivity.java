package com.example.demoapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.demoapp.models.Convict;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.formmaster.FormBuilder;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormElementSwitch;
import me.riddhimanadib.formmaster.model.FormElementTextMultiLine;
import me.riddhimanadib.formmaster.model.FormElementTextNumber;
import me.riddhimanadib.formmaster.model.FormElementTextSingleLine;
import me.riddhimanadib.formmaster.model.FormHeader;

public class ConvictDetailActivity extends AppCompatActivity {

    private static final String TAG = ConvictDetailActivity.class.getName();
    private RecyclerView recyclerView;
    private FormBuilder mFormBuilder;
    private String caseid;
    private ProgressBar uploadBar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convict_detail);

        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, "error counting convicts", Toast.LENGTH_SHORT).show();
        }
        assert intent != null;
        final int convictsSize = intent.getIntExtra("convicts", 0);
        caseid = intent.getStringExtra("caseid" );

        Button btnSave = findViewById(R.id.btnSave);
        recyclerView = findViewById(R.id.formRecycler);
        uploadBar = findViewById(R.id.uploadBar);
        mFormBuilder = new FormBuilder(this, recyclerView);

        List<BaseFormElement> formItems = new ArrayList<>();

        for (int i = 1; i <= convictsSize; i++) {


            formItems.add(FormHeader.createInstance("Convict Info"));

            formItems.add(FormElementTextSingleLine.createInstance().setTitle("Convict Name").setHint("enter full name").setRequired(true).setType(FormElementTextSingleLine.TYPE_EDITTEXT_TEXT_SINGLELINE).setTag(i * 10 + 1));
            formItems.add(FormElementTextMultiLine.createInstance().setTitle("Convict Address").setHint("enter address").setRequired(true).setTag(i * 10 + 2));
            formItems.add(FormElementTextMultiLine.createInstance().setTitle("Detail").setHint("enter convict details").setRequired(true).setTag(i * 10 + 3));
            formItems.add(FormElementTextNumber.createInstance().setTitle("Age").setHint("enter convict age").setRequired(true).setTag(i * 10 + 4));
            formItems.add(FormElementSwitch.createInstance().setTitle("Gender").setHint("enter convict gender").setSwitchTexts("male", "female").setRequired(true).setTag(i * 10 + 5));
            formItems.add(FormElementSwitch.createInstance().setTitle("State").setHint("is dead or alive").setSwitchTexts("dead", "alive").setRequired(true).setTag(i * 10 + 6));

        }
        mFormBuilder.addFormElements(formItems);

        FirebaseFirestoreSettings firestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).setTimestampsInSnapshotsEnabled(true).build();
        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(firestoreSettings);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBar.setVisibility(View.VISIBLE);
                if (mFormBuilder.isValidForm()) {
                    Toast.makeText(ConvictDetailActivity.this, "validated", Toast.LENGTH_SHORT).show();
                }else{
                    updateUI(false);
                }
                List<Convict> convictList = new ArrayList<>();
                for (int i = 1; i <= convictsSize; i++) {

                    String name = mFormBuilder.getFormElement(i * 10 + 1).getValue();
                    String address = mFormBuilder.getFormElement(i * 10 + 2).getValue();
                    String details = mFormBuilder.getFormElement(i * 10 + 3).getValue();
                    String gender = mFormBuilder.getFormElement(i * 10 + 4).getValue();
                    String state = mFormBuilder.getFormElement(i * 10 + 5).getValue();
                    convictList.add(new Convict(caseid,name,address,details,gender,state));

                }

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // Add a new document with a generated ID
                    db.collection("convicts")
                            .add(convictList)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    caseid = documentReference.getId();
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + caseid);
                                    updateUI(true);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                    Toast.makeText(ConvictDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    updateUI(false);
                                }
                            });
                }
            }

        });
    }
    private void updateUI(boolean state) {
        uploadBar.setVisibility(View.GONE);
        if (state) {
            Toast.makeText(this, "data saved on server", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,HomeActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "some error", Toast.LENGTH_SHORT).show();
        }
    }
}
