package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetProfilePic extends AppCompatActivity {
    CircleImageView profile_picture;
    TextView skip_set_profile_pic;
    Button select_Profile_Pic_btn, set_profile_image_btn;
    FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    ProgressDialog progressDialog;
    private static int PICK_IMAGE = 1;
    Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile_pic);

        HideTaskBar();

        //Progress Bar
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        //Progress Bar

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference().child(mAuth.getUid()).child("Images").child("Profile Pic");
        //

        CastingViews();
        skipSettingProfileImage();

        // on click
        select_Profile_Pic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        //


        set_profile_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                UploadTask uploadTask = mStorageRef.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SetProfilePic.this, "Something went Wrong :(", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(SetProfilePic.this, "Successful", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                        startActivity(new Intent(SetProfilePic.this,Home.class));
                    }
                });
            }
        });
        //

        //on click











    }

    private void HideTaskBar() {
        // Task Bar Hide
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Task Bar Hide
    }

    private void skipSettingProfileImage() {

        skip_set_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SetProfilePic.this,Home.class));
            }
        });

    }

    private void CastingViews() {

        select_Profile_Pic_btn = findViewById(R.id.pick_image_btn);
        set_profile_image_btn = findViewById(R.id.set_picture_btn);
        profile_picture = findViewById(R.id.profile_pic);
        skip_set_profile_pic = findViewById(R.id.skip_set_profile_pic);


    }

    //Pick image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imagePath = data.getData();
            set_profile_image_btn.setVisibility(View.VISIBLE);
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profile_picture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    //pick image
}
