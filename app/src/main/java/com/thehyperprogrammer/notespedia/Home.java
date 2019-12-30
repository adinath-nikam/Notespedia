package com.thehyperprogrammer.notespedia;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {


    //Global Variables
    FirebaseAuth mAuth;
    ImageView about, User_Branch_Icon;
    FirebaseDatabase database;
    DatabaseReference mRef;
    CardView CS_CardView,logout,ResultViewCardView, StudenPortalCardView, ExamSchedule, QuestionPaperCardView, ScholarshipCardView;
    ProgressDialog progressDialog;
    CircleImageView HomeProfileImage;
    String ProfileSemesterVAR,ProfileDepartmentVAR;
    private FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    TextView User_Branch_Text;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel("MyNotifications",
                            "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            startActivity(new Intent(Home.this,ExamScheduleActivity.class));
                        }
                    }
                });




        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");




        HideTaskBar();

        //

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("USERS").child(mAuth.getUid());

        //

        //Set Home Profile Image
        HomeProfileImage();
        //Set Home Profile Image

        CastingViews();

        RetriveProfileData();



        gotoProfileActivity();

        gotoScholarshipsActivity();

        gotoAboutActivity();

        gotoSemestersActivity();

        gotoExamScheduleActivity();

        logoutActivity();

        gotoDiplomaResultView();

        gotoStudentPortal();

        gotoQuestionPapersActivity();











    }


    // User Defined Functions

    //get Images
    private void HomeProfileImage() {
        storageReference.child(mAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(HomeProfileImage);
            }
        });
    }
    // get images

    private void gotoDiplomaResultView() {

        ResultViewCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,ResultWEBVIEWActivity.class));
            }
        });

    }



    private void HideTaskBar() {
        // Task Bar Hide
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Task Bar Hide
    }

    private void gotoStudentPortal() {
        StudenPortalCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Students_Portal.class));
            }
        });
    }

    private void CastingViews() {
        // Casting Variables
        HomeProfileImage = findViewById(R.id.home_profile_image);
        logout = findViewById(R.id.logout_card);
        mAuth = FirebaseAuth.getInstance();
        ResultViewCardView = findViewById(R.id.ResultViewCardView);
        about = findViewById(R.id.aboutopt);
        CS_CardView = findViewById(R.id.CS_CardView_ID);
        StudenPortalCardView = findViewById(R.id.students_portal_id);
        ExamSchedule = findViewById(R.id.ExamScheduleId);
        QuestionPaperCardView = findViewById(R.id.QuestionPaperCard_ID);
        ScholarshipCardView = findViewById(R.id.ScholarshipCardViewID);
        User_Branch_Icon = findViewById(R.id.User_Branch_Icon_ID);
        User_Branch_Text = findViewById(R.id.User_Branch_Text_ID);
    }

    private void gotoProfileActivity() {
        HomeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Profile.class);
                startActivity(intent);
            }
        });
    }

    private void gotoAboutActivity() {
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,About_us.class);
                startActivity(intent);
            }
        });
    }

    private void logoutActivity() {
        // Logout Button

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder logoutADB = new AlertDialog.Builder(Home.this);

                logoutADB.setTitle("LOGOUT");
                logoutADB.setMessage("Are you sure you want to logout ?");

                logoutADB.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(Home.this,MainActivity.class));
                    }
                });

                logoutADB.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                logoutADB.show();

            }
        });
        // Logout Button Code Ends
    }

    private void gotoSemestersActivity() {
        CS_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Semesters.class);
                startActivity(intent);
            }
        });
    }

    private void gotoExamScheduleActivity() {
        ExamSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExamScheduleActivity.class));
            }
        });
    }

    private void gotoQuestionPapersActivity() {
        QuestionPaperCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuestionPaperViewActivity.class);
                Bundle extras = new Bundle();
                extras.putString("QuestionPapers", "QUESTION PAPERS");
                extras.putString("Department", ProfileDepartmentVAR);
                extras.putString("Semester", ProfileSemesterVAR);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void RetriveProfileData() {

        progressDialog.show();
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    SendDataDB userProfile = dataSnapshot.getValue(SendDataDB.class);
                    ProfileDepartmentVAR = userProfile.getUserDepartment();
                    ProfileSemesterVAR = userProfile.getUserSemester();

                    if(ProfileDepartmentVAR.equals("COMPUTER SCIENCE AND ENGINEERING")){
                        User_Branch_Text.setText("COMPUTER SCIENCE & ENGINEERING");
                        User_Branch_Icon.setBackgroundResource(R.drawable.computer);
                    }
                    if(ProfileDepartmentVAR.equals("MECHANICAL ENGINEERING")){
                        User_Branch_Text.setText(ProfileDepartmentVAR);
                        User_Branch_Icon.setBackgroundResource(R.drawable.mechanical);
                    }
                    if(ProfileDepartmentVAR.equals("AUTOMOBILE ENGINEERING")){
                        User_Branch_Text.setText(ProfileDepartmentVAR);
                        User_Branch_Icon.setBackgroundResource(R.drawable.automobile);
                    }
                    if(ProfileDepartmentVAR.equals("ELECTRICAL AND ELECTRONICS ENGINEERING")){
                        User_Branch_Text.setText(ProfileDepartmentVAR);
                        User_Branch_Icon.setBackgroundResource(R.drawable.electrical);
                    }
                    if(ProfileDepartmentVAR.equals("ELECTRONICS AND COMMUNICATION ENGINEERING")){
                        User_Branch_Text.setText("ELECTRONICS & COMM. ENGINEERING");
                        User_Branch_Icon.setBackgroundResource(R.drawable.electronics);
                    }
                    if(ProfileDepartmentVAR.equals("CIVIL ENGINEERING")){
                        User_Branch_Text.setText(ProfileDepartmentVAR);
                        User_Branch_Icon.setBackgroundResource(R.drawable.civil);
                    }
                    if(ProfileDepartmentVAR.equals("MECHATRONICS AND ENGINEERING")){
                        User_Branch_Text.setText(ProfileDepartmentVAR);
                        User_Branch_Icon.setBackgroundResource(R.drawable.mechatronics);
                    }

                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(Home.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();

                }
            });//

    }

    private void gotoScholarshipsActivity() {
        ScholarshipCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,QuestionPaperViewActivity.class);
                intent.putExtra("Scholarship","SCHOLARSHIP URL");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {


                AlertDialog.Builder CloseAppADB = new AlertDialog.Builder(Home.this);

                CloseAppADB.setTitle("EXIT");
                CloseAppADB.setMessage("Are you sure you want to Exit ?");

                CloseAppADB.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                CloseAppADB.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                CloseAppADB.show();


    }


    //User Defined Functions Ends
}
