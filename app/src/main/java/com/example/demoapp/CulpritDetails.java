package com.example.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CulpritDetails extends AppCompatActivity {

    private EditText culpritName;
    private EditText ageOfCulprit;
    private EditText genderDetail;
    private EditText mobileNum;
    private EditText addressDetail;
    private EditText otherInfo;
    private FirebaseAuth mAuth;
    private DatabaseReference mref;
    private String culpName;
    private String culpAge;
    private String culpGender;
    private String culpMobile;
    private String culpAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culprit_details);

        culpritName = findViewById(R.id.culpritName);
        ageOfCulprit = findViewById(R.id.ageOfCulp);
        genderDetail = findViewById(R.id.genderDetail);
        mobileNum = findViewById(R.id.mobileNum);
        addressDetail = findViewById(R.id.addressDetail);
        otherInfo = findViewById(R.id.otherInfo);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mref = database.getReference();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validDetail()){
                    culpritInfo();
                }
            }
        });
    }

    private void culpritInfo() {

    }

    private boolean validDetail() {
        boolean valid = true;
        culpName = culpritName.getText().toString();
        culpAge = ageOfCulprit.getText().toString();
        culpGender = genderDetail.getText().toString();
        culpMobile = mobileNum.getText().toString();
        culpAddress = addressDetail.getText().toString();

        if(TextUtils.isEmpty(culpName)){
            culpritName.setError("Enter Culprit Name!");
            return false;
        }
        else{
            valid = true;
            culpritName.setError(null);
        }
        if(TextUtils.isEmpty(culpAge)){
            ageOfCulprit.setError("Enter Culprit Age!");
            return false;
        }
        else{
            valid = true;
            ageOfCulprit.setError(null);
        }
        if(TextUtils.isEmpty(culpGender)){
            genderDetail.setError("Enter Gender Detail here!");
            return false;
        }
        else{
            valid = true;
            genderDetail.setError(null);
        }
        if(TextUtils.isEmpty(culpMobile)){
            mobileNum.setError("Enter Valid Mobile Number Here!");
            return false;
        }
        else{
            valid = true;
            mobileNum.setError(null);
        }

        if(TextUtils.isEmpty(culpAddress)){
            addressDetail.setError("Enter Address!");
            return false;
        }
        else{
            valid = true;
            addressDetail.setError(null);
        }

        return valid;
    }
}
