package com.doiliomatsinhe.cularte;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseUser user;
    private FirebaseAuth authentication;
    private FirebaseAuth.AuthStateListener authStateListener;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private static final String TEXT_PLAIN = "text/plain";
    private static final int HEADER_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.categoryFragment, R.id.favoriteFragment)
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

                    View headerView = navigationView.getHeaderView(HEADER_INDEX);

                    CircleImageView profileImg = headerView.findViewById(R.id.profile_image);
                    TextView textName = headerView.findViewById(R.id.text_name);
                    TextView textEmail = headerView.findViewById(R.id.text_email);

                    textName.setText(username);
                    textEmail.setText(email);

                    if (!photoUri.isEmpty()) {
                        Picasso.get().load(photoUri).into(profileImg);
                    }

                    Timber.d(userId + "\n" + username + "\n" + photoUri + "\n" + email);

                }
            }
        };
    }

    private void initComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        authentication = FirebaseAuth.getInstance();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

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

    public void shareApp(MenuItem item) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, getString(R.string.download_app));
        i.setType(TEXT_PLAIN);
        startActivity(Intent.createChooser(i, getString(R.string.share_using)));
        drawer.closeDrawer(GravityCompat.START);
    }

    public void conctactUs(MenuItem item) {
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse(getString(R.string.mail_to_)));
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.how_can_we_improve));
        startActivity(Intent.createChooser(i, getString(R.string.send_comments)));
        drawer.closeDrawer(GravityCompat.START);
    }

    public void signOut(MenuItem item) {
        signOut();
        drawer.closeDrawer(GravityCompat.START);
    }
}
