//package com.example.validation.ui;
//
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.validation.R;
//import com.example.validation.ui.home.dataBase;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.github.mikephil.charting.utils.Utils;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import java.util.ArrayList;
//
//
//public class BlankFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public BlankFragment() {
//        // Required empty public constructor
//    }
//
//    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    private FirebaseUser muser = mAuth.getCurrentUser();
//    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
//    private String uid = muser.getUid();
//    LineDataSet lineDataSet = new LineDataSet(null, null);
//    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
//    LineData lineData;
//    LineChart lineChart;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_blank, container, false);
//        // Utils.initialize(getResources());
//        lineChart = (LineChart) v.findViewById(R.id.lineChart);
//        lineDataSet.setLineWidth(4);
//        FirebaseFirestore.getInstance().
//                collection("Expense Amount")
//                .whereEqualTo("uid", uid)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    // int TotalExpenses = 0;
//
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
//                                        @Nullable FirebaseFirestoreException e) {
//
//                        if (e != null) {
//                            Log.w("YourTag", "Listen failed.", e);
//                            return;
//                        }
//
//                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//
//                            if (doc.exists()) {
//                                Utils.init(getContext());
//                                ArrayList<Entry> Data = new ArrayList<Entry>();
//                                dataBase mydata = doc.toObject(dataBase.class);
//                                Data.add(new Entry(mydata.getExpenses(), mydata.getAmount()));
////Utils.init(getContext());
//
//                                showChart(Data);
//                            } else {
////                                 Log.e("MPChartLib-Utils"
////                                        , "Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
//                                lineChart.clear();
//                                lineChart.invalidate();
//                            }
//
//                        }
//
//                    }
//
//                    private void showChart(ArrayList<Entry> data) {
//                        //  Utils.convertDpToPixel(6f);
//                        lineDataSet.setValues(data);
//                        lineDataSet.setLabel("dataset 1");
//                        iLineDataSets.clear();
//                        iLineDataSets.add(lineDataSet);
//                        lineData = new LineData(iLineDataSets);
//                        lineChart.clear();
//                        lineChart.setData(lineData);
//                         lineChart.invalidate();
//
//                    }
//
//                });
//
//        return v;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseFirestore.getInstance().
//                collection("Expense Amount")
//                .whereEqualTo("uid", uid).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//
//                    if (doc.exists()) {
//                        Utils.init(getContext());
//                        ArrayList<Entry> Data = new ArrayList<Entry>();
//                        dataBase mydata = doc.toObject(dataBase.class);
//                        Data.add(new Entry(mydata.getExpenses(), mydata.getAmount()));
////Utils.init(getContext());
//
//                        //showChart(Data);
//                    }
//
//                }
//
//
//            }
//
//
//        });
//    }
//}