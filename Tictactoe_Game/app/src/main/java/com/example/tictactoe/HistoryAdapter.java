package com.example.tictactoe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

                                        public class HistoryAdapter extends FirestoreRecyclerAdapter<database, HistoryAdapter.myViewHolder> {

                                            public HistoryAdapter(@NonNull FirestoreRecyclerOptions<database> options) {
                                                super(options);
                                            }

                                            @Override
                                            protected void onBindViewHolder(@NonNull HistoryAdapter.myViewHolder holder, int position, @NonNull database model) {
                                                holder.t1.setText(model.getPlayerOneScore()+"");
                                                holder.t2.setText(model.getPlayerTwoScore()+"");
                                                holder.t3.setText(model.getStatus());

                                            }

                                            @NonNull
                                            @Override
                                            public HistoryAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item,parent,false);
                                                return new myViewHolder(v);
                                            }
                                            public  void deletItem(int position){
                                                getSnapshots().getSnapshot(position).getReference().delete();


                                            }
                                          public class myViewHolder extends RecyclerView.ViewHolder {
                                                private TextView t1,t2,t3;
                                              public myViewHolder(@NonNull View itemView) {
                                                  super(itemView);
                                                  t1 = itemView.findViewById(R.id.tv1);
                                                  t2 = itemView.findViewById(R.id.tv2);
                                                  t3 = itemView.findViewById(R.id.tv3);
                                              }
                                          }
                                        }
