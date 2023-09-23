package com.miniproject.inventorymanagement.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.miniproject.inventorymanagement.R;

import java.util.ArrayList;

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
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        empArrayList.clear();
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult())
                            {
                                String empuid = queryDocumentSnapshot.getString("StoreUID");
                                String emp_auth = queryDocumentSnapshot.getString("UserAuth");
                                if(empuid.equals(User_id) && !emp_auth.equals("True"))
                                {
                                    String cat_id = queryDocumentSnapshot.getId().toString();
                                    employeelist employeelist = new employeelist(queryDocumentSnapshot.getString("UserEMail").toString(),cat_id);
                                    empArrayList.add(employeelist);
                                    empAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });

    }


}