package com.thehyperprogrammer.notespedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText ETusername,ETemail,ETphonenumber,ETpassword1,ETpassword2;
    RadioButton RadioGenderMale,RadioGenderFemale;
    Button signin_btn;
    String username,email,phonenumber,password,password1,password2,Gender="",SelectedCourse,SelectedSemester;
    Spinner spinner,spinnerSEMESTERS;
    String[] spinnerArray,spinnerArraySEMESTERS;
    ProgressBar progressBar;
    TextView login_option;
    private Spinner Departments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Developer Code Starts.

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        HideTaskBar();
        CastingViews();
        gotoLoginActivity();
        ProgressBarShow();
        ProgressBarHide();
        SemestersSpinner();


        //Spinner
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedCourse = spinnerArray[position].toUpperCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Ends

        //on sign in_btn clicked
        // Create Account Code Starts
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBarShow();

                //Getting string values from edittext

                username = ETusername.getText().toString().trim();
                email = ETemail.getText().toString().trim();
                phonenumber = ETphonenumber.getText().toString().trim();
                password1 = ETpassword1.getText().toString().trim();
                password2 = ETpassword2.getText().toString().trim();

                // Setting Gender Value
                if (RadioGenderMale.isChecked()) {
                    Gender = "Male";
                }
                if (RadioGenderFemale.isChecked()) {
                    Gender = "Female";
                }

                //Checking is input fields are empty

                if ( SelectedSemester.equals("Select Your Semester..")||SelectedCourse.equals("Select Your Course..") ||username.isEmpty() || email.isEmpty() || phonenumber.isEmpty() || password1.isEmpty() || password2.isEmpty() ||  password1.length()<8 || password2.length()<8 || phonenumber.length() < 10 || phonenumber.length() > 10 || Gender=="") {

                    ProgressBarHide();

                    if (username.isEmpty()) {
                        ETusername.setError("Please Enter Username");
                        ETusername.requestFocus();
                    }
                    if (email.isEmpty()) {
                        ETemail.setError("Please Enter Email");
                        ETemail.requestFocus();
                    }
                    if (phonenumber.isEmpty()) {
                        ETphonenumber.setError("Please Enter Phone Number");
                        ETphonenumber.requestFocus();
                    }
                    if (password1.isEmpty() || password2.isEmpty()) {
                        ETpassword2.setError("Please Enter Confirm Password");
                        ETpassword2.requestFocus();
                        ETpassword1.setError("Please Enter Password");
                        ETpassword1.requestFocus();
                    }
                    if (password1.length() < 8 || password2.length() < 8) {
                        ETpassword2.setError("Password Must be Atleast 8 Characters long");
                        ETpassword2.requestFocus();
                        ETpassword1.setError("Password Must be Atleast 8 Characters long");
                        ETpassword1.requestFocus();
                    }
                    if (phonenumber.length() < 10 || phonenumber.length() > 10) {
                        ETphonenumber.setError("Phone Number is Not Valid");
                        ETphonenumber.requestFocus();
                    }
                    if (Gender == "") {
                        RadioGenderMale.setError("Phone Number is Not Valid");
                        RadioGenderMale.requestFocus();
                    }
                    if (SelectedCourse.equals("Select Your Course..")){
                        Toast.makeText(signup.this, "Please Select your Course", Toast.LENGTH_SHORT).show();
                    }
                    if (SelectedSemester.equals("Select Your Semester..")){
                        Toast.makeText(signup.this, "Please Select your Semester", Toast.LENGTH_SHORT).show();
                    }



                } else {


                    if (password1.equals(password2)) {
                        password = password1;

                        ProgressBarShow();

                        // Creating user
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            sendDatatoDB();
                                            finish();
                                            Toast.makeText(signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(signup.this, SetProfilePic.class));
                                        } else {
                                            ProgressBarHide();
                                            Toast.makeText(signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }// <-- matching password condition end.
                    else {
                            ProgressBarHide();
                            ETpassword2.setError("Password Doesn't Match");
                            ETpassword2.requestFocus();
                    }// <- else condition of if ends.
                    //
                }
            }
        });

        // Create Account Code Ends



        //Developer Code Ends.
    }


    // User Defined Functions Starts
    private void ProgressBarHide() {

        final Wave waveProgressBar = new Wave();
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminateDrawable(waveProgressBar);

    }


    private void ProgressBarShow() {

        final Wave waveProgressBar = new Wave();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminateDrawable(waveProgressBar);

    }

    private void gotoLoginActivity() {

        login_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void CastingViews() {

        //Views Casting
        ETusername = findViewById(R.id.signin_username);
        ETemail = findViewById(R.id.signin_email);
        ETphonenumber = findViewById(R.id.signin_phno);
        ETpassword1 = findViewById(R.id.signin_password1);
        ETpassword2 = findViewById(R.id.signin_password2);
        RadioGenderMale = findViewById(R.id.radioMale);
        RadioGenderFemale = findViewById(R.id.radioFemale);
        signin_btn = findViewById(R.id.signin_btn);
        progressBar = findViewById(R.id.signup_progressbar);
        spinner = findViewById(R.id.Departments);
        spinnerSEMESTERS = findViewById(R.id.Semesters);
        spinnerArray = getResources().getStringArray(R.array.Departments);
        spinnerArraySEMESTERS = getResources().getStringArray(R.array.Semesters);
        login_option = findViewById(R.id.login_option);
        //Views Casting

    }

    private void HideTaskBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }


    private void sendDatatoDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("USERS").child(mAuth.getUid());
        SendDataDB senddatatodb = new SendDataDB(username,email,phonenumber,Gender,SelectedCourse,SelectedSemester);
        myRef.setValue(senddatatodb);
    }

    private void SemestersSpinner() {
        //Spinner
        ArrayAdapter arrayAdapterSemesters = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerArraySEMESTERS);
        arrayAdapterSemesters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSEMESTERS.setAdapter(arrayAdapterSemesters);

        spinnerSEMESTERS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedSemester = spinnerArraySEMESTERS[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Ends
    }

    // User Defined Functions Ends
}
