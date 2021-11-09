package com.example.validation.ui.TotalExpenseTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.validation.R;
import com.example.validation.ui.home.dataBase;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class total_Expense extends FirestoreRecyclerAdapter<dataBase,total_Expense.expenseHolder> {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore =FirebaseFirestore.getInstance();
    private DocumentReference documentReference;

    public  total_Expense(
            @NonNull FirestoreRecyclerOptions<dataBase> options){
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull expenseHolder holder, int position, @NonNull dataBase model) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = mAuth.getCurrentUser();
        String uid = muser.getUid();

        FirebaseFirestore.getInstance().collection("Expense Amount").whereEqualTo("uid",uid);

        holder.t5.setText(model.getExpenses()+ "");
        holder.t2.setText(model.getCategory());
        holder.t3.setText(model.getDate());


    }

    @NonNull
    @Override
    public expenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_item_expenses,parent,false);
        return new expenseHolder(view);
    }

    public class expenseHolder extends RecyclerView.ViewHolder {
        TextView t2,t3,t5;
        public expenseHolder(@NonNull View itemView) {
            super(itemView);
            t2 = itemView.findViewById(R.id.category);
            t3 = itemView.findViewById(R.id.date);
            t5 = itemView.findViewById(R.id.Expense);
        }
    }
}
