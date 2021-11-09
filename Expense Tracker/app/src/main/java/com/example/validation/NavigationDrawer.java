package com.example.validation;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.validation.ui.home.AddExpenses;
import com.example.validation.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;

public class   NavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigatiion__drawer);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_expense, R.id.nav_share,R.id.nav_logout,R.id.nav_graph)
                .setDrawerLayout(drawer)
                .build();






        // inflate


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigatiion_drawer, menu);

        int positionOfMenuItem = 0; // or whatever...
        MenuItem item = menu.getItem(positionOfMenuItem);
        SpannableString s = new SpannableString("Dark Mode");
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        item.setTitle(s);
        int positionOfMenuIte = 1; // or whatever...
        MenuItem ite = menu.getItem(positionOfMenuIte);
        SpannableString si = new SpannableString("Reset Password");
        si.setSpan(new ForegroundColorSpan(Color.GRAY), 0, si.length(), 1);
        ite.setTitle(si);


        return true;
    }
// testing mode
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_mode:
                // set the action
                //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
break;

            case R.id.action_settings:

                startActivity(new Intent(getApplicationContext(), resetPassword.class));

                break;



        }
        return super.onOptionsItemSelected(item);
    }
//
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();

    }

    public void calculate(View view) {
              FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.add(R.id.drawer_layout,new HomeFragment());
        fragmentTransaction.commit();
    }



    public void share(MenuItem item) {
        Intent share = new Intent (Intent.ACTION_SEND);
        share.setType("text/plain");
        String shareBody = "http:/play.google.com/store/apps/detail?id="+getPackageManager();
        String shareSub = "try now";
        share.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        share.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(share,"Share using"));

    }
}