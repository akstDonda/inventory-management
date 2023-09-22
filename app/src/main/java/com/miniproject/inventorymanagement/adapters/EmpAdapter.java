package com.miniproject.inventorymanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miniproject.inventorymanagement.R;
import com.miniproject.inventorymanagement.firebase.User;

import java.util.ArrayList;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.MyViewHolder> {

    Context context;
    ArrayList<employeelist> userArrayList;

    public EmpAdapter(Context context, ArrayList<employeelist> userArrayList) {
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

        employeelist emp = userArrayList.get(position);
         holder.UserEMail.setText(emp.UserEMail);



    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView UserEMail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            UserEMail=itemView.findViewById(R.id.email_emplist);

        }
    }
}
