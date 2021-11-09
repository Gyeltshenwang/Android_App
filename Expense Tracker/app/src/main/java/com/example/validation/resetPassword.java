package com.example.validation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.validation.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class resetPassword extends AppCompatActivity {
    private EditText current_password;
    private EditText new_password;
    private EditText new_password_confirm;
    private Button change_paassword_button;
    private ProgressBar change_password_progressBar;

    //private Toolbar change_password_toolbar;

    private FirebaseAuth firebaseAuth;

    private String user_email;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

//        change_password_toolbar = findViewById(R.id.change_pass_toolbar);
//        setSupportActionBar(change_password_toolbar);
//        getSupportActionBar().setTitle("Change Password");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String user_mail = firebaseAuth.getCurrentUser().getEmail();

        current_password = findViewById(R.id.change_pass_current);
        new_password = findViewById(R.id.change_pass_new);
        new_password_confirm = findViewById(R.id.change_pass_new_confirm);
        change_paassword_button = findViewById(R.id.change_pass_btn);
        change_password_progressBar = findViewById(R.id.change_pass_progress);

        change_password_progressBar.setVisibility(View.INVISIBLE);

        change_paassword_button.setEnabled(true);

        //animation
        current_password.setTranslationX(800);
        new_password.setTranslationX(800);
        new_password_confirm.setTranslationX(800);
        change_paassword_button .setTranslationX(800);

        current_password .animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        new_password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        new_password_confirm.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        change_paassword_button.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();


        change_paassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String current_pass = current_password.getText().toString();
                String new_pass = new_password.getText().toString();
                final String confrim_new_pass = new_password_confirm.getText().toString();

                if(!TextUtils.isEmpty(current_pass) && !TextUtils.isEmpty(new_pass) && !TextUtils.isEmpty(confrim_new_pass) ) {

                    if(confrim_new_pass.equals(new_pass)) {

                        if (!new_pass.equals(current_pass)) {

                            change_password_progressBar.setVisibility(View.VISIBLE);

                            change_paassword_button.setEnabled(false);

                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(user_mail, current_pass);


                            firebaseAuth.getCurrentUser().reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                firebaseAuth.getCurrentUser().updatePassword(confrim_new_pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                         //   Toasty.success(resetPassword.this, "Password changed Successfully!", Toast.LENGTH_LONG, true).show();
                                                            Toast.makeText(resetPassword.this, "Password changed Successfully", Toast.LENGTH_SHORT).show();

                                                            finish();

                                                        } else {

                                                            change_paassword_button.setEnabled(true);

                                                            /*Toast.makeText(ChangePasswordActivity.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();*/
                                                           // Toasty.error(resetPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG, true).show();
                                                            Toast.makeText(resetPassword.this,task.getException().getMessage() , Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });
                                            } else {

                                                change_paassword_button.setEnabled(true);

                                                /*Toast.makeText(ChangePasswordActivity.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();*/
                                              //  Toasty.error(resetPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG, true).show();
                                                Toast.makeText(resetPassword.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                            }

                                            change_password_progressBar.setVisibility(View.INVISIBLE);

                                        }
                                    });

                        } else {

                            change_paassword_button.setEnabled(true);

                            change_password_progressBar.setVisibility(View.INVISIBLE);

                         //   Toasty.warning(resetPassword.this, "Current & New Password cannot be the same", Toast.LENGTH_LONG, true).show();
                            Toast.makeText(resetPassword.this, "Current and New Password cannot be the same", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        change_paassword_button.setEnabled(true);

                        change_password_progressBar.setVisibility(View.INVISIBLE);

                      //  Toasty.warning(resetPassword.this, "Passwords don't match", Toast.LENGTH_LONG, true).show();
                        Toast.makeText(resetPassword.this, "Password did not match", Toast.LENGTH_SHORT).show();


                    }

                } else {

                    change_paassword_button.setEnabled(true);

                    change_password_progressBar.setVisibility(View.INVISIBLE);

                    /*Toast.makeText(ChangePasswordActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();*/

                    //Toasty.warning(resetPassword.this, "All Fields are Mandatory", Toast.LENGTH_LONG, true).show();
                    Toast.makeText(resetPassword.this, "All Field are Mandatory", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


}