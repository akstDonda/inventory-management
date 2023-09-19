package com.miniproject.inventorymanagement.common;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    ImageButton lg_back_btn;
    TextInputLayout email, password;
    TextView reggt;
    EditText lemail,lpassword;
    FirebaseAuth mAuth;
    ProgressBar lprogresbar;
    FirebaseFirestore db;
    static int some = 1;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn=findViewById(R.id.btn_admin_login);
        mAuth=FirebaseAuth.getInstance();
        lprogresbar=findViewById(R.id.lProgressBar);
        lg_back_btn=findViewById(R.id.login_back_btn);

        email=findViewById(R.id.outlinedTextField11);
        password = findViewById(R.id.outlinedTextField12);

        reggt=findViewById(R.id.openreg);

        lemail = email.getEditText();
        lpassword = password.getEditText();

        db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("users");

        //back button so user go to select usertype activity
        lg_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,UserTypeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        reggt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,AdminRegistrationActivity.class);
                startActivity(intent);
            }
        });



        //user is already create so throw direct mainActivity
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Loading.class));
            finish();
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=lemail.getText().toString().trim();
                String password=lpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    lemail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    lpassword.setError("password is Required");
                    return;
                }
                if(password.length()<6){
                    lpassword.setError("character More Then 6 Required");
                    return;
                }
                lprogresbar.setVisibility(View.VISIBLE);

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
                            lprogresbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    public void CreateNewAccount(View view) {
        startActivity(new Intent(getApplicationContext(), AdminRegistrationActivity.class));
    }
}