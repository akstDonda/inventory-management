package com.miniproject.inventorymanagement.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.adapters.EmpAdapter;
import com.miniproject.inventorymanagement.firebase.User;

import java.util.ArrayList;
import java.util.Objects;

public class CreateNewUser extends AppCompatActivity {

    TextView textviewuid;
    RecyclerView recyclerView;
    ArrayList<employeelist> empArrayList;
    EmpAdapter empAdapter;
    FirebaseFirestore db;
    FirebaseAuth mauth;
    FirebaseUser cuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_emp);

        textviewuid=findViewById(R.id.tvuid);


        recyclerView=findViewById(R.id.emprecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        empArrayList=new ArrayList<employeelist>();
        empAdapter=new EmpAdapter(CreateNewUser.this,empArrayList);

        recyclerView.setAdapter(empAdapter);

         //EventRequestListener();

        mauth = FirebaseAuth.getInstance();
        cuser = mauth.getCurrentUser();
        String User_id = cuser.getUid().toString();

        //print current admin uid
        textviewuid.setText(User_id);


        //print requests of employees
        db.collection("employeeDetails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        empArrayList.clear();
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult())
                            {
                                String empuid = queryDocumentSnapshot.getString("StoreUID");
                                if(empuid.equals(User_id))
                                {
                                    employeelist employeelist = new employeelist(queryDocumentSnapshot.getString("UserEMail").toString());
                                    empArrayList.add(employeelist);
                                    empAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });

    }


}