package com.miniproject.inventorymanagement.admin;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import com.miniproject.inventorymanagement.adapters.EmpAdapter;
import com.miniproject.inventorymanagement.adapters.Employee;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.ArrayList;

public class ApproveUser extends AppCompatActivity {

    TextView textviewuid;
    RecyclerView recyclerView;
    ArrayList<Employee> empArrayList;
    EmpAdapter empAdapter;
    FirebaseFirestore db;
    FirebaseAuth mauth;
    FirebaseUser cuser;

    String storeId;
    String emp_auth;

    String User_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_emp);
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();

        textviewuid=findViewById(R.id.tvuid);


        recyclerView=findViewById(R.id.emprecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        empArrayList=new ArrayList<Employee>();
        empAdapter=new EmpAdapter(ApproveUser.this,empArrayList);

        recyclerView.setAdapter(empAdapter);

         //EventRequestListener();

        mauth = FirebaseAuth.getInstance();
        cuser = mauth.getCurrentUser();
        User_id = cuser.getUid().toString();
        String companyId = DatabaseHandler.getInstance().getUser().getCompanyId();

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
                                storeId = queryDocumentSnapshot.getString("StoreUID");
                                emp_auth =queryDocumentSnapshot.getString("UserAuth");
                                String userId = queryDocumentSnapshot.getString("userId");
                                if(emp_auth != null && storeId != null && userId != null && emp_auth.equals("False") && storeId.equals(companyId))
                                {
                                    DatabaseHandler.getInstance().getCompany().addUser(userId);
                                    String cat_id = queryDocumentSnapshot.getId().toString();
                                    Employee employeelist = new Employee(queryDocumentSnapshot.getString("UserEMail").toString(),cat_id, userId);
                                    empArrayList.add(employeelist);
                                    empAdapter.notifyDataSetChanged();
                                    DatabaseHandler.getInstance().getFirebaseFirestore().collection("users").document(userId).update("companyId",storeId);
                                }
                                else {
                                    if (storeId != null)
                                        Log.e(TAG, storeId);
                                    if (emp_auth != null)
                                        Log.e(TAG, emp_auth);
                                    if (userId != null)
                                        Log.e(TAG, userId);
                                }
                            }
                        }
                    }
                });

    }


}