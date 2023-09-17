package com.miniproject.inventorymanagement.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.inventorymanagement.R;

import java.util.Objects;

public class CreateNewUser extends AppCompatActivity {
    Button addUser;
    TextInputLayout username;
    TextInputLayout password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_emp);

        // get fields
        addUser = findViewById(R.id.adduser);
        username = findViewById(R.id.adduseremail);
        password = findViewById(R.id.adduserpass);
        mAuth = FirebaseAuth.getInstance();
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getEditText().getText().toString();
                String pwd = password.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(uname)){
                    username.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    password.setError("password is Required");
                    return;
                }
                if (pwd.length() < 8){
                    password.setError("character More Then 6 Required");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(uname,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreateNewUser.this, "SucessFully Create User", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));

                        }else{
                            Toast.makeText(CreateNewUser.this, "Error !!!"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}