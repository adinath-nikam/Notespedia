package com.thehyperprogrammer.notespedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class passwordResetEmailSent extends AppCompatActivity {
    Button BackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_email_sent);
        BackToLogin = findViewById(R.id.back_to_login_btn);

        //Back to login Activity
        BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(passwordResetEmailSent.this,MainActivity.class));
            }
        });
        //Back to login code ends
    }
}
