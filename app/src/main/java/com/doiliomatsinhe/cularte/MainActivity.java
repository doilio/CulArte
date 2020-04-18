package com.doiliomatsinhe.cularte;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseUser user;
    private FirebaseAuth authentication;
    private FirebaseAuth.AuthStateListener authStateListener;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        checkUserLoggedIn();
        configureProfile();
    }

    private void configureProfile() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = authentication.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    String username = user.getDisplayName();
                    String photoUri = String.valueOf(user.getPhotoUrl());
                    String email = user.getEmail();

                    View headerView = navigationView.getHeaderView(0);

                    ImageView profileImg = headerView.findViewById(R.id.profile_image);
                    TextView textName = headerView.findViewById(R.id.text_name);
                    TextView textEmail = headerView.findViewById(R.id.text_email);

                    textName.setText(username);
                    textEmail.setText(email);

                    Picasso.get().load(photoUri).into(profileImg);

                    Timber.d(userId + "\n" + username + "\n" + photoUri + "\n" + email);

                }
            }
        };
    }

    private void initComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        authentication = FirebaseAuth.getInstance();

    }

    private void checkUserLoggedIn() {
        user = authentication.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        authentication.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        authentication.removeAuthStateListener(authStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                signOut();
        }

        return super.onOptionsItemSelected(item);

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void signOut() {
        AuthUI.getInstance().signOut(this);
        finish();
        startActivity(new Intent(this, SignInActivity.class));

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
