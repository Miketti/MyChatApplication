package com.example.mychatdomain.mychatapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//This is the WelcomeActivity, the first activity of the application. This activity will be shown only if the device is not logged in to the service.

public class WelcomeActivity extends AppCompatActivity implements CreateAccountFragment.OnFragmentInteractionListener, LogInFragment.OnFragmentInteractionListener, PasswordResetSendCodeFragment.OnFragmentInteractionListener, PasswordResetUseCodeFragment.OnFragmentInteractionListener, SetNewPasswordFragment.OnFragmentInteractionListener, WelcomeFragment.OnFragmentInteractionListener, CallBackListener {

    //Firebase is used as back-end.
    private FirebaseAuth mAuth;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_activity_id, new WelcomeFragment()).commit();
        mAuth = FirebaseAuth.getInstance();
        progressdialog = new ProgressDialog(WelcomeActivity.this);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateLoginStatus(currentUser);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Implements CallBackListener
    @Override
    public void onRegistrationDataPass(String email, String username, String password) {
        createAccount(email, password);
    }

    //Implements CallBackListener
    @Override
    public void onSignInDataPass(String email, String password) {
        signIn(email, password);
    }

    //This is mainly used for Logging purposes
    private void updateLoginStatus (FirebaseUser user) {
        if (user == null) {
            Log.d("Login status", "Not signed in");
        } else {
            Log.d("Login status", "Signed in");
        }
    }

    private void createAccount(String email, String password) {

        progressdialog.setTitle("Create account");
        progressdialog.setMessage("Creating account...");
        progressdialog.setIndeterminate(false);
        progressdialog.show();

        //The account is created here and Toast are used to show the result of the account creation.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Create account: ", "Success");
                            Toast.makeText(WelcomeActivity.this, "Account successfully created. You can now sign in.", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateLoginStatus(user);
                        } else {
                            Log.d("Create account: ", "Failure", task.getException());
                            Toast.makeText(WelcomeActivity.this, "Account creation failed. Please try again.", Toast.LENGTH_LONG).show();
                            updateLoginStatus(null);
                        }
                        progressdialog.dismiss();
                    }
                });
    }

    private void signIn(String email, String password) {

        progressdialog.setTitle("Sign in");
        progressdialog.setMessage("Signing in to application...");
        progressdialog.setIndeterminate(false);
        progressdialog.show();

        //The login is executed here and Toast are used to show the result of the login.
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Login: ", "Success");
                            Toast.makeText(WelcomeActivity.this, "Logged in successufully", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateLoginStatus(user);
                            startActivity(new Intent(WelcomeActivity.this, ChatWindowActivity.class));
                            finish();
                        } else {
                            Log.d("Login: ", "Failure", task.getException());
                            Toast.makeText(WelcomeActivity.this, "Login failed. Please try again.", Toast.LENGTH_LONG).show();
                            updateLoginStatus(null);
                        }
                        progressdialog.dismiss();
                    }
                });
    }
}
