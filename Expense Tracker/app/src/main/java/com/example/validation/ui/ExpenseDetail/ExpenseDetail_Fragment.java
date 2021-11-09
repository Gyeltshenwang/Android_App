package com.example.validation.ui.ExpenseDetail;

import android.graphics.Canvas;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.validation.Login;
import com.example.validation.R;
import com.example.validation.ui.home.dataBase;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.content.ContentValues.TAG;

public class ExpenseDetail_Fragment extends Fragment implements FirebaseAuth.AuthStateListener {

  private   FirebaseAuth  mAuth = FirebaseAuth.getInstance();
       private FirebaseUser muser = mAuth.getCurrentUser();
   private  FirebaseFirestore fStore =FirebaseFirestore.getInstance();
  private  String uid = muser.getUid();
  private  DocumentReference documentReference = fStore.document("Expense Amount/Expense Amount Details");


    private  DetailAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_expense, container, false);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Query query = FirebaseFirestore.getInstance()
                .collection("Expense Amount")
                .whereEqualTo("uid",uid);
        FirestoreRecyclerOptions<dataBase> options = new FirestoreRecyclerOptions.Builder<dataBase>()
                .setQuery(query,dataBase.class)
                .build();


        adapter = new DetailAdapter(options);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //animation of recyclerview;

       /* Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.faded);
        recyclerView.setAnimation(animation);***/

        recyclerView.setTranslationX(800);
        recyclerView.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        recyclerView.animate().translationX(0).alpha(2).setDuration(800).setStartDelay(200).start();



        // delete item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.deletItem(viewHolder.getLayoutPosition());

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.black))
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();
                //recyclerView.get

            }



        }).attachToRecyclerView(recyclerView);




        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        FirebaseAuth.getInstance().removeAuthStateListener(this);

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
     if (firebaseAuth.getCurrentUser()==null){

     }
    }
}