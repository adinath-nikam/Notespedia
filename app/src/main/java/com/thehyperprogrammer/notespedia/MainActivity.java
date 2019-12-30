package com.thehyperprogrammer.notespedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText ETLoginEmail,ETLoginPassword;
    String LoginEmail,LoginPassword;
    TextView signin_option,forget_password;
    Button login_btn;
    ProgressBar progressBar;
    FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();
        //Get current logged in user
        if(currentUser != null){
            finish();
            startActivity(new Intent(MainActivity.this,Home.class));
        }
        //Get current logged in user code ends
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Developer Code Starts.

        //progress bar code
        final Wave waveProgressBar = new Wave();
        //progress bar code ends

        //Firebase Instance
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();




        //hide ststus bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //hide status bar code ends


        //views casting
        ETLoginEmail = findViewById(R.id.login_email);
        ETLoginPassword = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.login_progressbar);
        forget_password = findViewById(R.id.forgot_password);
        //views casting

        // Redirecting to signup activity
        signin_option = (TextView) findViewById(R.id.sign_in_option);
        signin_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this,signup.class));
            }
        });
        //Redirecting to signup activity code ends

        //firebase login
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminateDrawable(waveProgressBar);
                LoginEmail = ETLoginEmail.getText().toString().trim();
                LoginPassword = ETLoginPassword.getText().toString().trim();

                // Checking Input Fields isEmpty
                if(LoginEmail.isEmpty() || LoginPassword.isEmpty()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (LoginEmail.isEmpty()) {
                        ETLoginEmail.setError("Please Enter Email");
                        ETLoginEmail.requestFocus();
                    }
                    if (LoginPassword.isEmpty()) {
                        ETLoginPassword.setError("Please Enter Password");
                        ETLoginPassword.requestFocus();
                    }

                    if (LoginEmail.isEmpty() && LoginPassword.isEmpty()) {
                        ETLoginPassword.setError("Please Enter Password");
                        ETLoginPassword.requestFocus();
                        ETLoginEmail.setError("Please Enter Email");
                        ETLoginEmail.requestFocus();
                    }
                }
                // Checking Input Fields isEmpty

                // Else Login Validation
                else{
                    mAuth.signInWithEmailAndPassword(LoginEmail, LoginPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                        Toast.makeText(MainActivity.this, "Welcome Back :)", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,Home.class));
                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        ETLoginPassword.setError("Password is not Correct");
                                        ETLoginPassword.requestFocus();
                                        ETLoginEmail.setError("Email is not Correct");
                                        ETLoginEmail.requestFocus();
                                        Toast.makeText(MainActivity.this, "Login Failed Check Your Email and Password", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                // Login Valiadation Ends
            }
        });

        //Forgot password Operation
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Password_Reset.class));
            }
        });
        //Forgot password Operation code ends

        //Developer Code Ends.
    }
}
