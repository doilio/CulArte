package com.doiliomatsinhe.cularte;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.doiliomatsinhe.cularte.databinding.ActivitySignInBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

        TextView textView = (TextView) binding.signInButton.getChildAt(0);
        textView.setText(getString(R.string.fui_sign_in_with_google));

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Sign In Successful
            if (resultCode == RESULT_OK) {
                showProgressBar();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                // Sign In Failed
                if (response == null) {
                    showSnackbar("Login Cancelled!");
                    return;
                }
                if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar("No Internet Connection!");
                    return;
                }
                showSnackbar("Unknown Error!");
                Timber.d(response.getError());

            }
        }

    }

    private void showProgressBar() {
        binding.signInButton.setVisibility(View.GONE);
        binding.textTitle.setVisibility(View.GONE);
        binding.textSubtitle.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void showSnackbar(String msg) {
        Snackbar.make(binding.rootLayout, msg, Snackbar.LENGTH_SHORT).show();
    }
}
