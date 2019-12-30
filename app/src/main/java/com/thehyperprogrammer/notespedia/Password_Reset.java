package com.thehyperprogrammer.notespedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Password_Reset extends AppCompatActivity {

    // Gloabval Variables Declaration
    EditText Reset_Password_Email;
    Button Reset_Password_Button;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    // Global Variables Declaration Ends


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__reset);

        //Developer Code starts
        //progress bar
        final Wave waveProgressBar = new Wave();
        //progress bar ends

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Casting views
        Reset_Password_Email = findViewById(R.id.reset_password_email);
        Reset_Password_Button = findViewById(R.id.reset_password_button);
        progressBar = findViewById(R.id.resetPassword_progressbar);
        // Casting views ends

        //progress bar
        progressBar.setVisibility(View.INVISIBLE);
        //progress bar code ends

        //Reset operation
        Reset_Password_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                String ResetPasswordEmail = Reset_Password_Email.getText().toString().trim();

                if(ResetPasswordEmail.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Reset_Password_Email.setError("Email Required");
                    Reset_Password_Email.requestFocus();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(ResetPasswordEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(Password_Reset.this,passwordResetEmailSent.class));
                            }
                            else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Password_Reset.this, "Email not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });
        //Developer code ends

    }
}
