package com.miniproject.inventorymanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.MyViewHolder> {

    Context context;
    ArrayList<Employee> userArrayList;

    public EmpAdapter(Context context, ArrayList<Employee> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public EmpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.emplist,parent,false);

        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull EmpAdapter.MyViewHolder holder, int position) {

        Employee emp = userArrayList.get(position);
        String cat_id = userArrayList.get(position).cat_id;
        String userId = userArrayList.get(position).userId;
         holder.UserEMail.setText(emp.UserEMail);
         holder.btn_accept.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DatabaseHandler dbHandler = DatabaseHandler.getInstance();
                 dbHandler.getCompany().addUser(userId);
                 Map<String, Object> hashMap = new HashMap<>();
                 hashMap.put("companyId", dbHandler.getUser().getCompanyId());
                 dbHandler.getFirebaseFirestore().collection("users").document(userId).set(hashMap).addOnSuccessListener(
                         new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {
                                 FirebaseFirestore db = FirebaseFirestore.getInstance();
                                 Map<String,Object> map = new HashMap<>();
                                 map.put("UserAuth","True");
                                 db.collection("employeeDetails").document(cat_id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void unused) {
                                         Toast.makeText(context, "Emplyee Request Accepted", Toast.LENGTH_SHORT).show();
                                         Integer index = userArrayList.indexOf(emp);
                                         userArrayList.remove(emp);
                                         notifyItemRemoved(index);
                                     }
                                 });
                             }
                         }
                 );

             }
         });

        holder.btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("employeeDetails").document(cat_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Employee Request Declined", Toast.LENGTH_SHORT).show();
                        Integer index = userArrayList.indexOf(emp);
                        userArrayList.remove(emp);
                        notifyItemRemoved(index);
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView UserEMail;
        Button btn_accept,btn_decline;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            UserEMail=itemView.findViewById(R.id.email_emplist);
            btn_accept=itemView.findViewById(R.id.btn_a);
            btn_decline=itemView.findViewById(R.id.btn_d);

        }
    }
}
