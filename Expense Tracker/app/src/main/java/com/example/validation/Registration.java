package com.example.validation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.validation.ui.home.dataBase;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    public TextInputLayout rUser, rPassword, rEmail, mConfirm;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseFirestore fStore;
    public String userID;
//    DocumentReference documentReference = fStore.document("user");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        rUser = findViewById(R.id.edtUser1);
        rEmail = findViewById(R.id.edtEmail1);
        rPassword = findViewById(R.id.edtPassword1);
        btnRegister = findViewById(R.id.btn1);
        mConfirm = findViewById(R.id.edtConfirm);
        progressBar = findViewById(R.id.progressBar1);
        btnRegister.setOnClickListener(this::registered);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        // hide the toolbar
        rUser.setTranslationX(800);
        rEmail.setTranslationX(800);
        rPassword.setTranslationX(800);
        mConfirm.setTranslationX(800);
        btnRegister.setTranslationX(800);

        rUser.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        rEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(450).start();
        rPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(550).start();
        mConfirm.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(650).start();
        btnRegister.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


    }

    private boolean validateEmail() {
        String email = rEmail.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            rEmail.setError("Field cannot be empty!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            rEmail.setError("invalid email");
            return false;
        } else {
            rEmail.setError(null);
            return true;
        }


    }

    private boolean validatePassword() {
        String password = rPassword.getEditText().getText().toString();
        String confirm = mConfirm.getEditText().getText().toString();
        if (password.isEmpty()) {
            rPassword.setError("Field cannot left empty");
            return false;
        } else if (confirm.isEmpty()) {
            mConfirm.setError("Field cannot be left empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            rPassword.setError("Weak password");
            return false;
        }//else if(password.length()==8){
//            rPassword.setError("password is too long");
//            return false;
//
//        }

        else if (!password.equals(confirm)) {
            mConfirm.setError("Password did not match");
            return false;

        } else {
            rPassword.setError(null);
            return true;
        }

    }

    private boolean validateUser() {
        String user = rUser.getEditText().getText().toString().trim();
        if (user.isEmpty()) {

            rUser.setError("Field cannot left empty");
            return false;
        }

        else {
            rUser.setError(null);
            return true;
        }
    }


    public void registered(View view) {
        if (!validateEmail() | !validatePassword() | !validateUser()) {
            return;
        }
        String email = rEmail.getEditText().getText().toString().trim();
        String password = rPassword.getEditText().getText().toString().trim();
        String user = rUser.getEditText().getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
               // startActivity(new Intent(getApplicationContext(), main3.class));

                Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(Registration.this, "Verification code has been sent", Toast.LENGTH_SHORT).show();
                        rEmail.getEditText().setText(" ");
                        rPassword.getEditText().setText(" ");

                        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        userID= mAuth.getCurrentUser().getUid();
//
                        fStore.collection("user")

                                .add(new userModel(user,email,password,userID));

                        Toast.makeText(Registration.this, "Successfully created", Toast.LENGTH_SHORT).show();

                        sendToLogin();

                     mAuth.signOut();

                    } else {
                        Toast.makeText(Registration.this, Objects.requireNonNull(task1.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }


                });


                progressBar.setVisibility(View.INVISIBLE);

            } else {

                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    rEmail.setError("Email exist");
                    progressBar.setVisibility(View.INVISIBLE);
                }

                //    Toast.makeText(MainActivity2.this, "error",  Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void sendToLogin() {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}
