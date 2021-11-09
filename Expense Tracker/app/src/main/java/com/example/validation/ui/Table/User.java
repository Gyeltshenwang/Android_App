//package com.example.validation.ui.User;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.validation.R;
//import com.example.validation.userModel;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//
//public class User extends FirestoreRecyclerAdapter<userModel,User.UserAdapter> {
//
//    public User(@NonNull FirestoreRecyclerOptions<userModel> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull UserAdapter holder, int position, @NonNull userModel model) {
//        holder.t1.setText(model.getUser());
//        holder.t1.setText(model.getEmail());
//
//    }
//
//    @NonNull
//    @Override
//    public UserAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details,parent,false);
//        return new UserAdapter(view);
//    }
//
//    public static class UserAdapter extends RecyclerView.ViewHolder {
//        TextView t1,t2;
//        public UserAdapter(@NonNull View itemView) {
//            super(itemView);
//             t1=itemView.findViewById(R.id.tvName);
//             t2=itemView.findViewById(R.id.tvEmail);
//        }
//    }
//}
