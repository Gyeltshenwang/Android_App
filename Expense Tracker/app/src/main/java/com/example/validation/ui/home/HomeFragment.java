package com.example.validation.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.validation.R;
import com.example.validation.userModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.graphics.ImageFormat.JPEG;

public class HomeFragment extends Fragment {

    private static final String TAG = "user_Fragment";
    private FirebaseUser muser = FirebaseAuth.getInstance().getCurrentUser();
    private  String uid = muser.getUid();
    private  TextView tvName,tvEmail;
    private View v2,v3,v4,v5;


    // User adapter;

    private CircleImageView ProfileImage;
    private final static int PICK_IMAGE = 1;
    //private Uri mainImageURI = null;
    Uri imageUri;
    private FloatingActionButton shear;
    private ImageView img;
    int TAKE_IMAGE_CODE=1001;
    private Button btn_Expense;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // linking to the fragment xml file;
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        v2 = root.findViewById(R.id.view2);
        v3 = root.findViewById(R.id.view3);
        v4 = root.findViewById(R.id.view4);
        v5 = root.findViewById(R.id.view5);
        img = root.findViewById(R.id.image);

        Animation anm = AnimationUtils.loadAnimation(getContext(),R.anim.faded);

        img.setAnimation(anm);
        /* profile Image animation*/
//        Animation profile = AnimationUtils.loadAnimation(getContext(),R.anim.faded);
//
//        ProfileImage.setAnimation(profile);


      //   AddAmount = root.findViewById(R.id.btn_AddAmount);
         btn_Expense = root.findViewById(R.id.btn_AddExpense);
        Animation an = AnimationUtils.loadAnimation(getContext(),R.anim.faded);
        btn_Expense.setAnimation(an);

         // animation
       // btn_Expense.setTranslationY(800);
       v2.setTranslationY(800);
        v3.setTranslationY(800);
        v4.setTranslationY(800);
        v5.setTranslationY(800);
       // btn_Expense.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        v2.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        v3.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(200).start();
        v4.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(100).start();
        v5.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(50).start();

         btn_Expense.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AddExpenses fragment = new AddExpenses();
                 FragmentTransaction fragmentmanager =getFragmentManager().beginTransaction();
                 fragmentmanager.replace(R.id.nav_host_fragment,fragment,fragment.getTag());
                 fragmentmanager.addToBackStack(null);
                 fragmentmanager.commit();

             }
         });
         // user name

        tvName= root.findViewById(R.id.tv_Name);
        tvEmail= root.findViewById(R.id.tv_Email);

        // animation
        tvName.setTranslationX(800);
        tvEmail.setTranslationX(800);
        tvName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        tvEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();



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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user.getPhotoUrl() != null) {
            Glide.with(getContext())
                    .load(user.getPhotoUrl())
                    .into(ProfileImage);
        }



        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseStorage stroge = FirebaseStorage.getInstance();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) !=null){
                    startActivityForResult(intent,TAKE_IMAGE_CODE);
                  //  handleUpload(bitmap);
                 //   handleUpload(bitmap);


                }
//                Intent gallery = new Intent();
//                gallery.setType("image/*");
//                gallery.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(gallery, "Select picture"),PICK_IMAGE);

            }


        });


        return root;





    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ProfileImage.setImageBitmap(bitmap);
                    handleUpload(bitmap);
            }
        }
    }

    private void handleUpload(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ",e.getCause() );
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);
                        setUserProfileUrl(uri);

                    }
                });
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Updated succesfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });




    }

}