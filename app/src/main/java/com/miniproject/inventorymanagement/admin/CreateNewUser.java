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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.inventorymanagement.R;

import java.util.Objects;

public class CreateNewUser extends AppCompatActivity {
    Button addUser;
    EditText username;
    EditText password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);

        // get fields
        addUser = findViewById(R.id.btn_add_user);
        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);
        mAuth = FirebaseAuth.getInstance();
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=username.getText().toString().trim();
                String pwd=password.getText().toString().trim();

                if (TextUtils.isEmpty(uname)){
                    username.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    password.setError("password is Required");
                    return;
                }
                if (password.length() < 8){
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