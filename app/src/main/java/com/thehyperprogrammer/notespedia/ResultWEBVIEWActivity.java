package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultWEBVIEWActivity extends AppCompatActivity {

    WebView webView;
    ProgressDialog progressDialog;
    //Firebase
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference();
    //Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_webview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Question papers..");
        progressDialog.show();

        HideTaskBar();
        CastingViews();
        RetriveURL();


    }

    // User Defined Functions

    private void CastingViews() {
        webView = findViewById(R.id.result_webview);
    }

    private void DiplomaResultWebView(String URL) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(URL);
    }

    private void HideTaskBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    private void RetriveURL() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String URL = dataSnapshot.child("EXAM RESULT URL").child("URL").getValue().toString();
                DiplomaResultWebView(URL);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ResultWEBVIEWActivity.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    // User Defined Functions

}
