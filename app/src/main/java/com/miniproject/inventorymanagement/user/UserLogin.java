package com.miniproject.inventorymanagement.user;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.common.UserTypeActivity;

public class UserLogin extends AppCompatActivity {

    TextInputLayout employeeemail,employeepassword;
    ProgressBar pb;
    EditText empe,empp;
    ImageButton elg_back_btn;
    TextView elg_reggt;
    Button btnemplogin;
    FirebaseAuth mauth;
    FirebaseFirestore db;
    public int c =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        elg_back_btn=findViewById(R.id.elogin_back_btn);
        elg_reggt=findViewById(R.id.eopenreg);
        pb=findViewById(R.id.empProgressBar);

        employeeemail=findViewById(R.id.empemail);
        employeepassword=findViewById(R.id.emppass);
        btnemplogin=findViewById(R.id.btn_emp_login);
        empe=employeeemail.getEditText();
        empp=employeepassword.getEditText();

        mauth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();


        btnemplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String em=empe.getText().toString();
                String pw=empp.getText().toString();

                if (TextUtils.isEmpty(em)){
                    empe.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(pw)){
                    empp.setError("password is Required");
                    return;
                }
                if(pw.length()<6){
                    empp.setError("character More Then 6 Required");
                    return;
                }
                pb.setVisibility(View.VISIBLE);

                //not working
                db.collection("employeeDetails")
                        .whereEqualTo("UserEMail",em)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                        String chem=queryDocumentSnapshot.getString("UserEMail");
                                        String chps=queryDocumentSnapshot.getString("UserPassword");
                                        String chat=queryDocumentSnapshot.getString("UserAuth");
                                        String chuid=queryDocumentSnapshot.getString("StoreUID");
                                        if (chem.equals(em) && chps.equals(pw) && chat.equals("True") && !chuid.equals(null)){
                                            Intent i=new Intent(UserLogin.this,UserHome.class);
                                            startActivity(i);
                                        }
                                        else{
                                            c=1;
                                        }
                                    }
                                    if (c==1){
                                        Toast.makeText(UserLogin.this, "unauthorized user", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserLogin.this, "Data not fetch from database", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        // back to login and go to registration of employee
        elg_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserLogin.this, UserTypeActivity.class);
                startActivity(i);
            }
        });
        elg_reggt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserLogin.this, UserRegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}