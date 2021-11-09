package com.example.validation.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.validation.Calculator;
//import com.example.validation.ExpenseMade;
import com.example.validation.R;
import com.example.validation.ui.ExpenseDetail.ExpenseDetail_Fragment;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AddExpenses extends Fragment{

    private Button btn;
    private TextView ettDate,mBalance;
   private DatePickerDialog.OnDateSetListener setListener;
   private Spinner spin_dropdown;
   private EditText mExpense,mInitialAmount;
   private CardView a1,a2,a3,a4;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private DocumentReference documentReference = fStore.document("Expense Amount/Expense Amount Details");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_expenses, container, false);
        spin_dropdown = view.findViewById(R.id.spin_dropdown);
        mExpense = view.findViewById(R.id.number2);
        mBalance = view.findViewById(R.id.mBalance);
        mInitialAmount = view.findViewById(R.id.mInitial);
        a1 = view.findViewById(R.id.cd1);
        a2 = view.findViewById(R.id.cd2);
        a3 = view.findViewById(R.id.cd3);
        a4 = view.findViewById(R.id.cd4);

        btn = view.findViewById(R.id.btn_save);
        //animation
        btn.setTranslationX(800);
        btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        Animation A = AnimationUtils.loadAnimation(getContext(),R.anim.faded);
        a1.setAnimation(A);

//        Animation anm = AnimationUtils.loadAnimation(getContext(),R.anim.faded);
//        btn.setAnimation(anm);

        Animation A2= AnimationUtils.loadAnimation(getContext(),R.anim.faded);
        a2.setAnimation(A2);

        Animation A3 = AnimationUtils.loadAnimation(getContext(),R.anim.faded);
        a3.setAnimation(A3);

        Animation A4 = AnimationUtils.loadAnimation(getContext(),R.anim.faded);
        a4.setAnimation(A4);
        //

        // balance, creating a object of a balance
        balanceAmount Ubalance = new balanceAmount();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              final   String amount = mInitialAmount.getText().toString();
             final   String expenses = mExpense.getText().toString();
              final   String category = spin_dropdown.getSelectedItem().toString();
            final    String date = ettDate.getText().toString();
                // Balance = String.valueOf(mBalance.getText().toString());


                final  Integer mamount = Integer.valueOf(amount);

                Integer Balance = Ubalance.userBalance(Integer.valueOf(amount),Integer.valueOf(expenses));
                mBalance.setText(String.valueOf(Balance));

                /*  testing*/

                if (TextUtils.isEmpty(amount)) {
                    mInitialAmount.setError("Field cannot be left empty");
                    return;
                }

                if (TextUtils.isEmpty(expenses)) {
                    mExpense.setError("Field cannot be left empty");
                    return;
                }
                final  Integer mexpense = Integer.valueOf(amount);

                if (!TextUtils.isEmpty(category)) {
                    ettDate.setError("Field cannot be left empty");
                    return;
                }

                if (!TextUtils.isEmpty(date)) {
                    mInitialAmount.setError("Field cannot be left empty");
                    return;
                }


                /*  testing*/



               String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                         CollectionReference collectionReference = (CollectionReference) FirebaseFirestore.getInstance()
//                                 //.whereEqualTo("uid",muser.getUid())
//                                 .collection("Expense Amount");
                FirebaseFirestore.getInstance()
                        .collection("Expense Amount")
                       .add(new dataBase(mamount,mexpense,category,date,Balance,uid));

                Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();

                ExpenseDetail_Fragment exp = new ExpenseDetail_Fragment();
                FragmentTransaction fra = getFragmentManager().beginTransaction();
                fra.replace(R.id.nav_host_fragment,exp,exp.getTag());
                fra.addToBackStack(null);
                fra.commit();
            }

        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.category,R.layout.support_simple_spinner_dropdown_item);
        spin_dropdown.setAdapter(adapter);

        ettDate = view.findViewById(R.id.Date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        ettDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String datepicker = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                ettDate.setText(datepicker);
            }
        };


        return view;


    }


}

