package com.example.validation.ui.TotalExpenseTable;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.validation.R;
//import com.github.mikephil.charting.charts.LineChart;
//import com.example.validation.ui.BlankFragment;
import com.example.validation.ui.home.dataBase;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class GraphFragmnet extends Fragment {

    private FloatingActionButton shear;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser muser = mAuth.getCurrentUser();
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private String uid = muser.getUid();
    private DocumentReference documentReference = fStore.document("Expense Amount/Expense Amount Details");
    private total_Expense adapter;
    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
       // btn = v.findViewById(R.id.show);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BlankFragment fragment = new BlankFragment();
//                FragmentTransaction fragmentmanager =getFragmentManager().beginTransaction();
//                fragmentmanager.replace(R.id.nav_host_fragment,fragment,fragment.getTag());
//                fragmentmanager.addToBackStack(null);
//                fragmentmanager.commit();
//            }
//        });
        RecyclerView recycler = v.findViewById(R.id.total_Rec);

       TextView mTotal = v.findViewById(R.id.total);
        TextView total = v.findViewById(R.id.total_expense);
        Query query = FirebaseFirestore.getInstance()
                .collection("Expense Amount")
                .whereEqualTo("uid", uid);
        FirestoreRecyclerOptions<dataBase> options = new FirestoreRecyclerOptions.Builder<dataBase>()
                .setQuery(query, dataBase.class)
                .build();
        adapter = new total_Expense(options);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        /* animation */
        recycler.setTranslationX(800);
        recycler.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        mTotal.setTranslationX(800);
        mTotal.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        Animation Total = AnimationUtils.loadAnimation(getContext(),R.anim.faded);

        total.setAnimation(Total);


        //
        FirebaseFirestore.getInstance().
                collection("Expense Amount")
                .whereEqualTo("uid", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    int TotalExpenses = 0;

                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.w("YourTag", "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            //String amount = mInitialAmount.getText().toString();
                            if (doc.exists()) {
                                dataBase message = doc.toObject(dataBase.class);
                                //final  int mexpense = Integer.valueOf(amount);
                                TotalExpenses += message.getExpenses();
                                String tot = String.valueOf(TotalExpenses);
                                total.setText(tot);
                                /* animation*/



                            }
                        }

                    }
                });




        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
      //  FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
      //  FirebaseAuth.getInstance().removeAuthStateListener(this);
    }
}