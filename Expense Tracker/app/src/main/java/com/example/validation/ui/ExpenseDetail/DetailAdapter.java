package com.example.validation.ui.ExpenseDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.validation.R;
import com.example.validation.ui.home.dataBase;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DetailAdapter extends FirestoreRecyclerAdapter<dataBase,DetailAdapter.DetailHolder> {

private FirebaseAuth mAuth;
private FirebaseFirestore fStore =FirebaseFirestore.getInstance();
private DocumentReference documentReference;


    public DetailAdapter(@NonNull FirestoreRecyclerOptions<dataBase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DetailHolder holder, int position, @NonNull dataBase model) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
     final   String uid = muser.getUid();




        FirebaseFirestore.getInstance().collection("Expense Amount").whereEqualTo("uid",uid);

        holder.t1.setText(model.getExpenses()+ "");
      holder.t2.setText(model.getAmount() + "");
      holder.t3.setText(model.getCategory());
      holder.t4.setText(model.getDate());
      holder.t5.setText(model.getBalance() + " ");
        FloatingActionButton shear = null;



    }

    @NonNull
    @Override
    public DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details,parent,false);
       return new DetailHolder(view);

    }
    public  void deletItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();


    }

    class DetailHolder extends  RecyclerView.ViewHolder{
        TextView t1,t2,t3,t4,t5;
        FloatingActionButton shear;
        public DetailHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.tvExpenses);
        t2 = itemView.findViewById(R.id.tvAmount);
        t3 = itemView.findViewById(R.id.tvCategory);
        t4 = itemView.findViewById(R.id.tvDate);
        t5 = itemView.findViewById(R.id.tvBalance);
        shear= itemView.findViewById(R.id.shear);





        }

    }



}
