package com.miniproject.inventorymanagement.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;
import com.miniproject.inventorymanagement.user.UserRegistrationActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    ImageButton backButton;
    TextView goToRegistration;

    TextInputLayout emailTextInput, passwordTextInput;
    EditText emailEditText, passwordEditText;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ProgressBar progressBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton =findViewById(R.id.btn_admin_login);
        progressBar =findViewById(R.id.lProgressBar);
        backButton =findViewById(R.id.login_back_btn);
        goToRegistration =findViewById(R.id.openreg);

        emailTextInput =findViewById(R.id.outlinedTextField11);
        passwordTextInput = findViewById(R.id.outlinedTextField12);
        emailEditText = emailTextInput.getEditText();
        passwordEditText = passwordTextInput.getEditText();

        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        CollectionReference users = db.collection("users");

        //back button so user go to select usertype activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,UserTypeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        goToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);

                Intent intent;
                if ( isAdmin )
                    intent = new Intent(LoginActivity.this, AdminRegistrationActivity.class);
                else
                    intent = new Intent(LoginActivity.this, UserRegistrationActivity.class);

                startActivity(intent);
                finish();
            }
        });



        //user is already create so throw direct mainActivity
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Loading.class));
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= emailEditText.getText().toString().trim();
                String password= passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    emailEditText.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passwordEditText.setError("password is Required");
                    return;
                }
                if(password.length()<6){
                    passwordEditText.setError("character More Then 6 Required");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                DatabaseHandler dbHandler = DatabaseHandler.getInstance();
                Task<AuthResult> task = dbHandler.signInWithEmailAndPassword(email, password);

                task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, dbHandler.getCompany().getName() + "(name of company)", Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            System.out.println(currentUser);
//                            Toast.makeText(AdminLogin.this, currentUser.getUid(), Toast.LENGTH_LONG).show();
                            String userID = "Some";

                            DocumentReference docRef = db.collection("users").document(currentUser.getUid());
//                            Toast.makeText(AdminLogin.this, "SucessFully Create User", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Loading.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Error !!!"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}