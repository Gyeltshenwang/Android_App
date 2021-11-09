package com.example.validation.ui.Table;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.validation.R;
import com.example.validation.userModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class user_Fragment extends Fragment {
    private static final String TAG = "user_Fragment";
    private FirebaseUser muser = FirebaseAuth.getInstance().getCurrentUser();
    private  String uid = muser.getUid();
    private  TextView tvName,tvEmail;

   // User adapter;

    private CircleImageView ProfileImage;
    private final static int PICK_IMAGE = 1;
    //private Uri mainImageURI = null;
    Uri imageUri;
private FloatingActionButton shear;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);



     tvName= root.findViewById(R.id.tv_Name);
     tvEmail= root.findViewById(R.id.tv_Email);

        FirebaseFirestore.getInstance()
                .collection("user")

                .whereEqualTo("uid",uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> user = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : user) {

                                userModel c = d.toObject(userModel.class);
                                String nam = c.getUser();
                                String eml = c.getEmail();
                                tvName.setText(nam);
                                tvEmail.setText(eml);


                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data

            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference store = db.collection("user");
        Query query = store;
        FirestoreRecyclerOptions<userModel> options = new FirestoreRecyclerOptions.Builder<userModel>()
                .setQuery(query,userModel.class)
                .build();


        ProfileImage = root.findViewById(R.id.Profile_Image);
//        Name =root.findViewById(R.id.name);
//        Email=root.findViewById(R.id.email);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select picture"),PICK_IMAGE);
            }
        });



///

        return root;


    }
    @Override
    public void onStart() {
        super.onStart();

       // adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
      //  adapter.stopListening();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                ProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}