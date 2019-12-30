package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExamScheduleActivity extends AppCompatActivity {

    Toolbar ExamScheduleToolbar;
    PDFView PDFView;
    TextView PDFTextTV;
    WebView ExamwebView;
    ProgressDialog progressDialog;
    //Firebase
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference();
    //Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Exam Details..");
        progressDialog.show();

        CastingViews();
        setSupportActionBar(ExamScheduleToolbar);
        getSupportActionBar().setTitle("Exam Schedule");

        RetriveURL();


    }

    private void CastingViews() {
        ExamScheduleToolbar = findViewById(R.id.exam_schedule_toolbar);
        PDFView = findViewById(R.id.PDFView_ID);
        PDFTextTV = findViewById(R.id.PDFTextTV);
        ExamwebView = findViewById(R.id.ExamdetailesView);
    }


    private void GetExamDetails(String URL) {
        progressDialog.show();
        ExamwebView.getSettings().setJavaScriptEnabled(true);
        ExamwebView.setWebViewClient(new WebViewClient());
        ExamwebView.getSettings().setBuiltInZoomControls(true);
        ExamwebView.getSettings().setBuiltInZoomControls(true);
        ExamwebView.loadUrl(URL);
        progressDialog.dismiss();
    }


    private void RetriveURL() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String URL = dataSnapshot.child("EXAM SCHEDULES").child("url").getValue().toString();
                GetExamDetails(URL);
                Toast.makeText(ExamScheduleActivity.this, "Please wait while it load...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ExamScheduleActivity.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


}
