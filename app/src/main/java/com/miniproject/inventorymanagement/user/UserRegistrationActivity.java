package com.miniproject.inventorymanagement.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.UserTypeActivity;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserRegistrationActivity extends AppCompatActivity {

    TextInputLayout useremail;
    TextInputLayout userpassword;
    TextInputLayout userstoreuid;

    EditText ue,upass,usui;
    Button btnadduser;
    ImageView btnbh;
    ProgressBar pb;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    DatabaseHandler Edbhandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        Edbhandler=DatabaseHandler.getInstance();

        btnbh=findViewById(R.id.back_btnn);
        useremail=findViewById(R.id.addempemail);
        userpassword=findViewById(R.id.addemppass);
        userstoreuid=findViewById(R.id.addempcompanyid);
        btnadduser=findViewById(R.id.btn_add_emp);
        pb=findViewById(R.id.erProgressBar);

        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        //convert into edittext
        ue = useremail.getEditText();
        upass = userpassword.getEditText();
        usui = userstoreuid.getEditText();


        //data authentication and insert
        btnadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // convert into string
                String Useremail = ue.getText().toString();
                String Userpwd = upass.getText().toString();
                String Usersui = usui.getText().toString();

                if (TextUtils.isEmpty(Useremail)){
                    ue.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(Userpwd)){
                    upass.setError("password is Required");
                    return;
                }
                if(Userpwd.length()<6){
                    upass.setError("character More Then 6 Required");
                    return;
                }
                pb.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(Useremail,Userpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Map<String,Object> emp = new HashMap<>();
                            emp.put("UserEMail",Useremail);
                            emp.put("UserPassword",Userpwd);
                            emp.put("StoreUID",Usersui);
                            emp.put("UserAuth","False");

                            db.collection("employeeDetails")
                                    .add(emp)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(UserRegistrationActivity.this, "SucessFully insert Employee data", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(UserRegistrationActivity.this, "Wait until your owner accept your Employee Request", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(UserRegistrationActivity.this, UserTypeActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UserRegistrationActivity.this, "unSucessFull", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                        }else{
                            Toast.makeText(UserRegistrationActivity.this, "Error !!!"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        // back login
        btnbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserRegistrationActivity.this, UserLogin.class);
                startActivity(i);
            }
        });



    }

}