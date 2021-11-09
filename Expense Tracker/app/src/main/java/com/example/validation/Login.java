package com.example.validation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
   // private static int timeOut = 2500;
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
    private TextInputLayout mEmail, mPassword;
    private Button btnLogin;
    private TextView mSignUp, mForgotPass;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseFirestore fStore;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mEmail = findViewById(R.id.edtEmail);
        mPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btn);
        progressBar = findViewById(R.id.progressBar);
        mForgotPass = findViewById(R.id.txtForgot);
        mForgotPass.setOnClickListener(this::forgot);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mEmail.setTranslationX(800);
        mPassword.setTranslationX(800);
        btnLogin.setTranslationX(800);

        mEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();

        getSupportActionBar().hide();

        btnLogin.setOnClickListener(this::logIn);
        if (mAuth.getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
            finish();

        }
    }

    private boolean validateEmail() {
        String email = mEmail.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            mEmail.setError("Field cannot be left empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Invalid email");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {
        String password = mPassword.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            mPassword.setError("Field cannot be left empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            mPassword.setError("Weak password");
            return false;
        } else {
            mPassword.setError(null);
            return true;
        }

    }

    public void logIn(View view) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }
        String email = mEmail.getEditText().getText().toString().trim();
        String password = mPassword.getEditText().getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()) {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        startActivity(new Intent(getApplicationContext(), NavigationDrawer.class));
                    } else {
                        //Toast.makeText(MainActivity.this, "please varify your email id", Toast.LENGTH_SHORT).show();
                        mEmail.setError("Please verify your email ");
                    }
//                        Toast.makeText(MainActivity.this, "user Succesfuly login", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(),MainActivity3.class));
                    //updateUI();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        //   Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        mPassword.setError("Invalid password!!");

                    } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        mEmail.setError("Email does not exist!");
                    }

                }
            }
        });


    }

    public void signUp(View view) {

        startActivity(new Intent(getApplicationContext(), Registration.class));
    }

    public void forgot(View view) {
        EditText resetEmail = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password?");
        passwordResetDialog.setMessage("Enter your email to reset password");
        passwordResetDialog.setView(resetEmail);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // extract the email
                String mail = resetEmail.getText().toString();
                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Failed to reset the password" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // closing the dialog

            }
        });
        passwordResetDialog.create().show();

    }

}