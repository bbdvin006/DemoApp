package com.example.demoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.formmaster.FormBuilder;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormElementTextEmail;
import me.riddhimanadib.formmaster.model.FormElementTextPassword;

public class LoginActivity extends AppCompatActivity {

    private Button sumbitButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private FormBuilder formBuilder;
    private List<BaseFormElement> formElements;
    private TextView textError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sumbitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        RecyclerView loginRecycler = findViewById(R.id.loginRecycler);
        formBuilder = new FormBuilder(this, loginRecycler);

        formElements = new ArrayList<>();
        formElements.add(FormElementTextEmail.createInstance().setTitle("Email").setHint("ravi@gmail.com").setRequired(true).setTag(101));
        formElements.add(FormElementTextPassword.createInstance().setType(BaseFormElement.TYPE_EDITTEXT_PASSWORD).setTitle("Password").setHint("secret password").setRequired(true).setTag(102));

        formBuilder.addFormElements(formElements);
        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                functionLogin();
            }
        });
        textError = findViewById(R.id.textError);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void functionLogin() {
        if (formBuilder.isValidForm()) {
            String email = formBuilder.getFormElement(101).getValue();
            String pwd = formBuilder.getFormElement(102).getValue();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pwd.length() < 8) {
                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters!", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        user = mAuth.getCurrentUser();
                        textError.setText("Validated");
                        textError.setTextColor(Color.GREEN);
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        textError.setText(task.getException().getMessage());
                        textError.setTextColor(Color.RED);
                        updateUI(null);
                    }

                }
            });
        }


    }

    private void updateUI(FirebaseUser user) {
        progressBar.setVisibility(View.GONE);
        if (user != null) {
            startActivity(new Intent(this, FormReportActivity.class));
            finish();
        }
    }
}
