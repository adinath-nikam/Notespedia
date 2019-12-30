package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionPaperViewActivity extends AppCompatActivity {

    Toolbar QuestionPaperViewToolbar;
    WebView QuestionPaperWebView;
    ProgressDialog progressDialog;
    String Branch,QuestionPapers,Department,Semester,Scholarship;
    //Firebase
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference();
    //Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper_view);

        Bundle extras = getIntent().getExtras();
        QuestionPapers = extras.getString("QuestionPapers");
        Department = extras.getString("Department");
        Semester = extras.getString("Semester");

        Scholarship = getIntent().getStringExtra("Scholarship");


        CastingViews();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Question papers..");
        progressDialog.show();






        if(Scholarship == null){
            setSupportActionBar(QuestionPaperViewToolbar);
            getSupportActionBar().setTitle(QuestionPapers);
            RetriveURL();
        }
        else {

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) QuestionPaperWebView.getLayoutParams();
            // Left Top Right Bottom Margin
            lp.setMargins(0,200,0,0);

            // Apply the updated layout parameters to TextView
            QuestionPaperWebView.setLayoutParams(lp);
            ScholarshipView();
            setSupportActionBar(QuestionPaperViewToolbar);
            getSupportActionBar().setTitle(Scholarship);
        }











    }


    private void CastingViews() {
        QuestionPaperViewToolbar = findViewById(R.id.QuestionPaperView_layout_bar);
        QuestionPaperWebView = findViewById(R.id.QuestionPaperView);
    }

    private void GetQuestionPapers(String URL) {
        QuestionPaperWebView.getSettings().setJavaScriptEnabled(true);
        QuestionPaperWebView.setWebViewClient(new WebViewClient());
        QuestionPaperWebView.getSettings().setBuiltInZoomControls(true);
        QuestionPaperWebView.getSettings().setBuiltInZoomControls(true);
        QuestionPaperWebView.loadUrl(URL);
        progressDialog.dismiss();
    }

    private void RetriveURL() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String URL = dataSnapshot.child(QuestionPapers).child(Department).child(Semester).getValue().toString();
                GetQuestionPapers(URL);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuestionPaperViewActivity.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void ScholarshipView() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String URL = dataSnapshot.child(Scholarship).child("URL").getValue().toString();
                GetScholarshipView(URL);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuestionPaperViewActivity.this, "Failed to Load", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void GetScholarshipView(String URL) {
        QuestionPaperWebView.getSettings().setJavaScriptEnabled(true);
        QuestionPaperWebView.setWebViewClient(new WebViewClient());
        QuestionPaperWebView.getSettings().setBuiltInZoomControls(true);
        QuestionPaperWebView.getSettings().setBuiltInZoomControls(true);
        QuestionPaperWebView.loadUrl(URL);
        progressDialog.dismiss();
    }


    @Override
    public void onBackPressed() {
        if(QuestionPaperWebView != null && QuestionPaperWebView.canGoBack())
            QuestionPaperWebView.goBack();// if there is previous page open it
        else
            super.onBackPressed();//if there is no previous page, close app
    }

}
