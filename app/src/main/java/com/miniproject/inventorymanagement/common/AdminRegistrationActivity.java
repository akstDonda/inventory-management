package com.miniproject.inventorymanagement.common;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.WriteBatch;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.admin.Home;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.ArrayList;
import java.util.Objects;

public class AdminRegistrationActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText, companyEditText, displayNameEditText;
    TextInputLayout emailTextView, passwordTextView, companyTextInput, displayNameTextInput;
    Button registrationButton;
    TextView goToLoginButton;
    ProgressBar registrationProgressBar;
    FirebaseAuth mAuth;
    ImageButton backToUserTypeButton;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        dbHandler = DatabaseHandler.getInstance();

        companyTextInput = findViewById(R.id.edt_company_admin_reg);
        emailTextView = findViewById(R.id.edt_email_admin_reg);
        passwordTextView = findViewById(R.id.edt_password_admin_reg);
        displayNameTextInput = findViewById(R.id.display_name);
        goToLoginButton = findViewById(R.id.openlog);

        companyEditText = companyTextInput.getEditText();
        emailEditText = emailTextView.getEditText();
        passwordEditText = passwordTextView.getEditText();
        displayNameEditText = displayNameTextInput.getEditText();

        registrationButton = findViewById(R.id.btn_admin_signin);
        registrationProgressBar = findViewById(R.id.rProgressBar);
        mAuth = FirebaseAuth.getInstance();
        backToUserTypeButton = findViewById(R.id.reg_back_btn);

        //back button
        backToUserTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminRegistrationActivity.this, UserTypeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        //back to loginpage
        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminRegistrationActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        //user is already create so throw direct mainActivity
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Loading.class));
            finish();
        }
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String companyName = companyEditText.getText().toString().trim();
                String displayName = displayNameEditText.getText().toString().trim();
                Toast.makeText(AdminRegistrationActivity.this, email + password, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email can't be empty!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password can't be empty!");
                    return;
                } else if (password.length() < 8) {
                    passwordEditText.setError("Password of minimum 8 characters is required!");
                    return;
                }
                if (TextUtils.isEmpty(companyName)) {
                    companyEditText.setError("Company name is required too.");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
                    return;
                }
                if (TextUtils.isEmpty(displayName)) {
                    displayNameEditText.setError("Display Name is required.");
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    return;
                }
                registrationProgressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminRegistrationActivity.this, "SucessFully Create User", Toast.LENGTH_SHORT).show();
                            String uid = Objects.requireNonNull(dbHandler.getFirebaseAuth().getCurrentUser()).getUid();
                            Task<Void> task1 = dbHandler.createDocuments(uid);
                            task1.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.e(TAG, "Batch creation success!!");
//                                    dbHandler.getFirebaseFirestore().collection("users").document(uid).update("companyId", uid);
//                                    dbHandler.getFirebaseFirestore().collection("companies").document(uid).update("adminId", uid);
//                                    dbHandler.getFirebaseFirestore().collection("companies").document(uid).update("users", new ArrayList<>());
//                                    dbHandler.getFirebaseFirestore().collection("companies").document(uid).update("name", companyName);
                                    DocumentReference userRef = dbHandler.getFirebaseFirestore().collection("users").document(uid);
                                    DocumentReference companyRef = dbHandler.getFirebaseFirestore().collection("companies").document(uid);
                                    WriteBatch batch = dbHandler.getFirebaseFirestore().batch();
                                    batch.update(userRef, "companyId", uid);
                                    batch.update(userRef, "displayName", displayName);
                                    batch.update(companyRef, "adminId", uid);
                                    ArrayList<String> users = new ArrayList<String>();
                                    users.add(uid);
                                    batch.update(companyRef, "users", users);
                                    batch.update(companyRef, "name", companyName);
                                    batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            startActivity(new Intent(getApplicationContext(), Home.class));
                                        }
                                    });
                                }
                            });
//                            dbHandler.refreshUserData();
//                            dbHandler.refreshCompanyData();
//                            dbHandler.createDocuments(dbHandler.getFirebaseAuth().getCurrentUser().getUid());
//                            Log.d(TAG, dbHandler.getUser().getCompanyId());
//                            Log.d(TAG, "Updated User (local): " + dbHandler.getUser().getName() + "(" + dbHandler.getUser().getId() + ")");
                        } else {
                            Toast.makeText(AdminRegistrationActivity.this, "Error !!!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            registrationProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


    }

    public void GoForOldAccounnt(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}