package com.thehyperprogrammer.notespedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextView ProfileHeaderName,ProfileHeaderEmail,ProfileName,ProfileEmail,ProfileGender,ProfilePhone,ProfileDepartment,ProfileSemester;
    DatabaseReference mRef;
    ProgressDialog progressDialog;
    ImageView   update_name_btn,
                update_email_btn,
                update_phone_btn,
                update_gender_btn,
                update_department_btn,
                update_semester_btn;
    CircleImageView Profile_Image;
    String newUsername,newEmail,newPhone,newGender,newDepartment;
    String ProfileNameVAR,ProfileEmailVAR,ProfilePhoneVAR,ProfileGenderVAR,ProfileDepartmentVAR,ProfileSemesterVAR;
    private FirebaseStorage firebaseStorage;
    FirebaseUser CurrentUser;
    CharSequence[] values = {"COMPUTER SCIENCE AND ENGINEERING",
                            "MECHANICAL ENGINEERING",
                            "AUTOMOBILE ENGINEERING",
                            "ELECTRONICS AND COMMUNICATION ENGINEERING",
                            "ELECTRICAL AND ELECTRONICS ENGINEERING",
                            "CIVIL ENGINEERING",
                            "MECHATRONICS AND ENGINEERING"};

    CharSequence[] Gendervalues = {"Male","Female"};

    CharSequence[] Semstervalues = {"1ST SEMESTER",
                                    "2ND SEMESTER",
                                    "3RD SEMESTER",
                                    "4TH SEMESTER",
                                    "5TH SEMESTER",
                                    "6TH SEMESTER"};

    private static int PICK_IMAGE = 1;
    Uri imagePath;
    StorageReference storageReference, mStorageRef;
    ImageView set_image,cancel_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();





        HideTaskBar();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference().child("USERS").child(mAuth.getUid());
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        CurrentUser = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference().child(mAuth.getUid()).child("Images").child("Profile Pic");

        //get images
        storageReference.child(mAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(Profile_Image);
            }
        });

        // get images


        CastingViews();
        GetDatabaseValuesToVariables();
        retriveProfileData();
        OnupdatenamebtnClicked();
        OnupdateemailbtnClicked();
        OnupdatephonebtnClicked();
        OnupdategenderbtnClicked();
        OnupdatedepartmentbtnClicked();
        OnupdatesemesterbtnClicked();
        Onupdateprofileimageclicked();


    }


    private void OnupdatedepartmentbtnClicked() {

        update_department_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder UpdateDepartmentADB = new AlertDialog.Builder(Profile.this);

                UpdateDepartmentADB.setTitle("Select Your Choice");

                UpdateDepartmentADB.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                SendDataDB UpdateDeptToCS = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR, ProfileGenderVAR, "COMPUTER SCIENCE AND ENGINEERING",ProfileSemesterVAR);
                                mRef.setValue(UpdateDeptToCS);
                                break;
                            case 1:
                                    SendDataDB UpdateDeptToME = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR, ProfileGenderVAR, "MECHANICAL ENGINEERING",ProfileSemesterVAR);
                                mRef.setValue(UpdateDeptToME);
                                break;
                            case 2:
                                SendDataDB UpdateDeptToAT = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR, ProfileGenderVAR, "AUTOMOBILE ENGINEERING",ProfileSemesterVAR);
                                mRef.setValue(UpdateDeptToAT);
                                break;
                            case 3:
                                SendDataDB UpdateDeptToEC = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR, ProfileGenderVAR, "ELECTRONICS AND COMMUNICATION ENGINEERING",ProfileSemesterVAR);
                                mRef.setValue(UpdateDeptToEC);
                                break;
                            case 4:
                                SendDataDB UpdateDeptToEE = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR, ProfileGenderVAR, "ELECTRICAL AND ELECTRONICS ENGINEERING",ProfileSemesterVAR);
                                mRef.setValue(UpdateDeptToEE);
                                break;
                            case 5:
                                SendDataDB UpdateDeptToCivil = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR, ProfileGenderVAR, "CIVIL ENGINEERING",ProfileSemesterVAR);
                                mRef.setValue(UpdateDeptToCivil);
                                break;
                            case 6:
                                SendDataDB UpdateDeptMC = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR, ProfileGenderVAR, "MECHATRONICS AND ENGINEERING",ProfileSemesterVAR);
                                mRef.setValue(UpdateDeptMC);
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                UpdateDepartmentADB.show();
            }
        });

    }//

    private void OnupdategenderbtnClicked() {
        update_gender_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder UpdateGenderADB = new AlertDialog.Builder(Profile.this);

                UpdateGenderADB.setTitle("Select Your Choice:");

                UpdateGenderADB.setSingleChoiceItems(Gendervalues, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                progressDialog.show();
                                SendDataDB UpdateGenderToMale = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR,"Male", ProfileDepartmentVAR,ProfileSemesterVAR);
                                mRef.setValue(UpdateGenderToMale);
                                Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                            case 1:
                                progressDialog.show();
                                SendDataDB UpdateGenderToFemale = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR, "Female",ProfileDepartmentVAR,ProfileSemesterVAR);
                                mRef.setValue(UpdateGenderToFemale);
                                Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                UpdateGenderADB.show();
            }
        });
    }

    private void OnupdatephonebtnClicked() {
        update_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder UpdatePhoneADB = new AlertDialog.Builder(Profile.this);
                UpdatePhoneADB.setTitle("New Phone Number:");
                final EditText ETUpdatePhone = new EditText(Profile.this);
                ETUpdatePhone.setInputType(InputType.TYPE_CLASS_PHONE);
                UpdatePhoneADB.setView(ETUpdatePhone);
                UpdatePhoneADB.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }); // cancel
                UpdatePhoneADB.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        newPhone = ETUpdatePhone.getText().toString();
                        if (newPhone.length() < 10 || newPhone.length() > 10) {
                            Toast.makeText(Profile.this, "Please Enter Valid Phone Number.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            SendDataDB UpdateData = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, newPhone, ProfileGenderVAR, ProfileDepartmentVAR,ProfileSemesterVAR);
                            mRef.setValue(UpdateData);
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this, "Phone Number Update Successful.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }); //Ok buton


                UpdatePhoneADB.show();
            }
        });
    }//

    private void OnupdateemailbtnClicked() {
        update_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder UpdateEmailADB = new AlertDialog.Builder(Profile.this);
                UpdateEmailADB.setTitle("New Email");
                final EditText ETUpdateEmail = new EditText(Profile.this);
                UpdateEmailADB.setView(ETUpdateEmail);
                UpdateEmailADB.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }); // cancel
                UpdateEmailADB.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newEmail = ETUpdateEmail.getText().toString();
                        if (newEmail.isEmpty()) {
                            ETUpdateEmail.setError("Please Enter Email");
                            ETUpdateEmail.requestFocus();
                        }
                        else {
                            progressDialog.show();
                            CurrentUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        SendDataDB UpdateData = new SendDataDB(ProfileNameVAR, newEmail, ProfilePhoneVAR, ProfileGenderVAR, ProfileDepartmentVAR,ProfileSemesterVAR);
                                        mRef.setValue(UpdateData);
                                        Toast.makeText(Profile.this, "Email update successful", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(Profile.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });

                        }
                    }
                }); //Ok buton


                UpdateEmailADB.show();
            }
        });
    }

    private void OnupdatenamebtnClicked() {
        update_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder UpdateNameADB = new AlertDialog.Builder(Profile.this);
                UpdateNameADB.setTitle("New Username");
                final EditText ETUpdateName = new EditText(Profile.this);
                UpdateNameADB.setView(ETUpdateName);
                UpdateNameADB.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }); // cancel
                UpdateNameADB.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newUsername = ETUpdateName.getText().toString();
                        if (newUsername.isEmpty()) {
                            ETUpdateName.setError("Please Enter Username");
                            ETUpdateName.requestFocus();
                        }
                        else {
                            SendDataDB UpdateData = new SendDataDB(newUsername, ProfileEmailVAR, ProfilePhoneVAR, ProfileGenderVAR, ProfileDepartmentVAR,ProfileSemesterVAR);
                            mRef.setValue(UpdateData);
                            Toast.makeText(Profile.this, "Username Update Successful.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }); //Ok buton


                UpdateNameADB.show();
            }
        });
    }//

    private void GetDatabaseValuesToVariables() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SendDataDB userProfile = dataSnapshot.getValue(SendDataDB.class);
                ProfileNameVAR = userProfile.getUserName();
                ProfileEmailVAR = userProfile.getUserEmail();
                ProfilePhoneVAR = userProfile.getUserPhoneNumber();
                ProfileGenderVAR = userProfile.getUserGender();
                ProfileDepartmentVAR = userProfile.getUserDepartment();
                ProfileSemesterVAR = userProfile.getUserSemester();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Profile.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });//
    }

    public void CastingViews() {
        //Casting Views
        ProfileHeaderName = findViewById(R.id.profile_header_name);
        ProfileHeaderEmail = findViewById(R.id.profile_header_email);
        ProfileName = findViewById(R.id.profile_name);
        ProfileEmail = findViewById(R.id.profile_email);
        ProfilePhone = findViewById(R.id.profile_phone);
        ProfileGender = findViewById(R.id.profile_gender);
        ProfileDepartment = findViewById(R.id.profile_department);
        ProfileSemester = findViewById(R.id.profile_semester);
        Profile_Image = findViewById(R.id.profile_image);
        update_name_btn = findViewById(R.id.update_name_btn);
        update_email_btn = findViewById(R.id.update_email_btn);
        update_phone_btn = findViewById(R.id.update_phone_btn);
        update_gender_btn = findViewById(R.id.update_gender_btn);
        update_department_btn = findViewById(R.id.update_department_btn);
        update_semester_btn = findViewById(R.id.update_semester_btn);
        set_image = findViewById(R.id.set_image);
        cancel_image = findViewById(R.id.cancel_image);
        //casting Views
    }

    private void retriveProfileData() {

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SendDataDB userProfile = dataSnapshot.getValue(SendDataDB.class);
                ProfileHeaderName.setText(userProfile.getUserName());
                ProfileHeaderEmail.setText(userProfile.getUserEmail());
                ProfileName.setText(userProfile.getUserName());
                ProfileEmail.setText(userProfile.getUserEmail());
                ProfilePhone.setText(userProfile.getUserPhoneNumber());
                ProfileGender.setText(userProfile.getUserGender());
                ProfileDepartment.setText(userProfile.getUserDepartment());
                ProfileSemester.setText(userProfile.getUserSemester());
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Profile.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });//

    }

    private void HideTaskBar() {
        //hide ststus bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //hide status bar code ends
    }



    private void OnupdatesemesterbtnClicked() {
        update_semester_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder UpdateSemesterADB = new AlertDialog.Builder(Profile.this);

                UpdateSemesterADB.setTitle("Select Your Choice:");

                UpdateSemesterADB.setSingleChoiceItems(Semstervalues, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                progressDialog.show();
                                SendDataDB UpdateSemesterTo1 = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR,ProfileGenderVAR, ProfileDepartmentVAR,"1ST SEMESTER");
                                mRef.setValue(UpdateSemesterTo1);
                                Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                            case 1:
                                progressDialog.show();
                                SendDataDB UpdateSemesterTo2 = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR,ProfileGenderVAR, ProfileDepartmentVAR,"2ND SEMESTER");
                                mRef.setValue(UpdateSemesterTo2);
                                Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                            case 2:
                                progressDialog.show();
                                SendDataDB UpdateSemesterTo3 = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR,ProfileGenderVAR, ProfileDepartmentVAR,"3RD SEMESTER");
                                mRef.setValue(UpdateSemesterTo3);
                                Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                            case 3:
                                progressDialog.show();
                                SendDataDB UpdateSemesterTo4 = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR,ProfileGenderVAR, ProfileDepartmentVAR,"4TH SEMESTER");
                                mRef.setValue(UpdateSemesterTo4);
                                Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                            case 4:
                                progressDialog.show();
                                SendDataDB UpdateSemesterTo5 = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR,ProfileGenderVAR, ProfileDepartmentVAR,"5TH SEMESTER");
                                mRef.setValue(UpdateSemesterTo5);
                                Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                            case 5:
                                progressDialog.show();
                                SendDataDB UpdateSemesterTo6 = new SendDataDB(ProfileNameVAR, ProfileEmailVAR, ProfilePhoneVAR,ProfileGenderVAR, ProfileDepartmentVAR,"6TH SEMESTER");
                                mRef.setValue(UpdateSemesterTo6);
                                Toast.makeText(Profile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                UpdateSemesterADB.show();
            }
        });
    }



    private void Onupdateprofileimageclicked() {


        // on click
        Profile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
        });

        //
        set_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                UploadTask uploadTask = mStorageRef.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, "Something went Wrong :(", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        set_image.setVisibility(View.GONE);
                        cancel_image.setVisibility(View.GONE);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        set_image.setVisibility(View.GONE);
                        cancel_image.setVisibility(View.GONE);
                        Toast.makeText(Profile.this, "Profile Image Updated.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
        //

        //
        cancel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_image.setVisibility(View.GONE);
                cancel_image.setVisibility(View.GONE);
                finish();
                startActivity(new Intent(Profile.this,Home.class));
            }
        });
        //


    }
    //Pick image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imagePath = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                Profile_Image.setImageBitmap(bitmap);
                set_image.setVisibility(View.VISIBLE);
                cancel_image.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    //pick image


} // <-- Main Paranthesis Closed



