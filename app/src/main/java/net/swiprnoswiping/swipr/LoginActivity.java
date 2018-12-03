package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
    }

    public void goToHome(View view) {
        Intent homeIntent = new Intent(this, HomePage.class);
        startActivity(homeIntent);
    }

    public void attemptLogin(){
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        if (email.isEmpty()) {
            mEmailView.setError("Please enter an email.");
            mEmailView.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailView.setError("Please enter a valid email.");
            mEmailView.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mPasswordView.setError("Please enter a password.");
            mPasswordView.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mPasswordView.setError("Password is too short.");
            mPasswordView.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (task.isSuccessful()) {
                    finish();
                    if (user.isEmailVerified()) {
                        Intent intent = new Intent(LoginActivity.this, HomePage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Email not verified", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, HomePage.class));
        }
    }
    */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
        }
    }


}

