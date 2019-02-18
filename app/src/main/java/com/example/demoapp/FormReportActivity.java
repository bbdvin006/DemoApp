package com.example.demoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.demoapp.data.StationDummyData;
import com.example.demoapp.models.CaseReport;
import com.example.demoapp.models.PoliceStation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormReportActivity extends AppCompatActivity {

    public static final String TAG = FormReportActivity.class.getName();
    private EditText reportOfficerName;
    private Spinner policeStationList;
    private EditText addressOfCrime;
    private EditText crimeCommitted;
    private EditText evidenceOfCrime;
    private EditText otherInformation;
    private Spinner noOfVictims;
    private FirebaseAuth mAuth;
    private Button date;
    private Button time;
    private RadioGroup arrestStatus;
    private FirebaseFirestore db;
    private ProgressBar uploadBar;
    private int numVictims = 0;
    private String caseid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_case_report);
        reportOfficerName = findViewById(R.id.reportOfficerName);
        policeStationList = findViewById(R.id.policeStationList);
        addressOfCrime = findViewById(R.id.addressOfCrime);
        crimeCommitted = findViewById(R.id.crimeCommitted);
        evidenceOfCrime = findViewById(R.id.evidenceOfCrime);
        date = findViewById(R.id.btnDate);
        time = findViewById(R.id.btnTime);
        arrestStatus = findViewById(R.id.arrestStatus);
        noOfVictims = findViewById(R.id.victimList);
        otherInformation = findViewById(R.id.otherInformation);
        Button submitReport = findViewById(R.id.submitReport);

        //spinner setup
        ArrayAdapter<PoliceStation> stationAdapter = new ArrayAdapter<PoliceStation>(this, android.R.layout.simple_list_item_1, StationDummyData.load());
        stationAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        policeStationList.setAdapter(stationAdapter);
        policeStationList.setSelection(0);

        ArrayAdapter<Integer> victimAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new Integer[]{1, 2, 3, 4, 5, 6, 7, 7, 8, 9, 10});
        victimAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        noOfVictims.setAdapter(victimAdapter);
        noOfVictims.setSelection(2);

        uploadBar = findViewById(R.id.uploadBar);
        mAuth = FirebaseAuth.getInstance();
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadBar.setVisibility(View.VISIBLE);
                if (validateform()) {
                    addDataToFirebase();
                } else {
                    updateUI(false);
                }
            }
        });

        FirebaseFirestoreSettings firestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).setTimestampsInSnapshotsEnabled(true).build();
        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(firestoreSettings);


        // DatePicker
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dtFragment = new DatePickerFragment();
                dtFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        //timePicker
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment tmFragment = new TimePickerFragment();
                tmFragment.show(getSupportFragmentManager(), "timePicker");

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

        if (TextUtils.isEmpty(nameOfOfficer)) {
            reportOfficerName.setError("Enter Officer Name!");
            valid = false;
        } else {
            reportOfficerName.setError(null);
        }

        if (TextUtils.isEmpty(crimeAddress)) {
            addressOfCrime.setError("Enter Address where case happened!");
            valid = false;
        } else {
            addressOfCrime.setError(null);
        }

        if (TextUtils.isEmpty(committedCrime)) {
            crimeCommitted.setError("Enter the case details!");
            valid = false;
        } else {
            crimeCommitted.setError(null);
        }
        if (TextUtils.isEmpty(crimeEvidence)) {
            evidenceOfCrime.setError("Enter Officer Name!");
            valid = false;
        } else {
            evidenceOfCrime.setError(null);
        }


        if (TextUtils.isEmpty(cDate) || ctime.toLowerCase().contains("date here")) {
            date.setError("Enter Date of crime Here!");
            valid = false;
        } else {
            date.setError(null);
        }
        if (TextUtils.isEmpty(ctime) || ctime.toLowerCase().contains("date here")) {
            time.setError("Enter Time of crime Here!");
            valid = false;
        } else {
            time.setError(null);
        }

        return valid;
    }

    private void addDataToFirebase() {
        RadioButton arrestStatusValue = findViewById(arrestStatus.getCheckedRadioButtonId());
        PoliceStation station = (PoliceStation) policeStationList.getSelectedItem();
        String officer = reportOfficerName.getText().toString();
        String crimeAddress = addressOfCrime.getText().toString();
        String committedCrime = crimeCommitted.getText().toString();
        String crimeEvidence = evidenceOfCrime.getText().toString();
        String information = otherInformation.getText().toString();
        numVictims = (int) noOfVictims.getSelectedItem();
        String cDate = date.getText().toString();
        String ctime = time.getText().toString();
        String hasArrestMade = arrestStatusValue.getText().toString();
        CaseReport report = new CaseReport(
                officer,
                station.name,
                crimeAddress,
                committedCrime,
                crimeEvidence,
                hasArrestMade,
                information,
                cDate,
                ctime,
                numVictims
        );


        if (mAuth.getCurrentUser() != null) {
            // Add a new document with a generated ID
            db.collection("cases")
                    .add(report)
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
                            Toast.makeText(FormReportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            updateUI(false);
                        }
                    });
        }
    }

    private void updateUI(boolean state) {
        uploadBar.setVisibility(View.GONE);
        if (state) {
            Toast.makeText(this, "data saved on server", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FormConvictActivity.class);
            intent.putExtra("convicts", numVictims);
            intent.putExtra("caseid", caseid);
            startActivity(intent);
        } else {
            Toast.makeText(this, "some error", Toast.LENGTH_SHORT).show();
        }
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private Calendar cal;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


            cal = Calendar.getInstance();

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            Button btnDate = (Button) getActivity().findViewById(R.id.btnDate);
            cal.set(year, month, day);
            Date cDate = cal.getTime();

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
            String sdate = dateFormat.format(cDate);
            btnDate.setText(sdate);

        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        private Calendar cal;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            
            return new TimePickerDialog(getActivity(), this, hour, minute,false);
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hr, int min) {
            String am_pm = " AM";

            if (hr >= 12) {
                am_pm = " PM";
                if (hr >=13 && hr < 24) {
                    hr -= 12;
                }
                else {
                    hr = 12;
                }
            } else if (hr == 0) {
                hr = 12;
            }

            Button btnTime = (Button) getActivity()
                    .findViewById(R.id.btnTime);
            btnTime.setText(hr + ":" + min + am_pm);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
