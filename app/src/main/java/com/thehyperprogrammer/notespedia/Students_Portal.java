package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thehyperprogrammer.notespedia.R.layout.messages_listitem;

public class Students_Portal extends AppCompatActivity {



    //Firebase Variables
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser CurrentUser;
    FirebaseStorage mStorage;
    StorageReference StorageRef;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef, MessagesRef, GroupMessageKeyRef, DisplayMsgRef;
    //Firebase Variables
    ProgressDialog progressDialog;
    EditText UserMessage;
    ImageButton SendMsgButton;
    String Uid,CURRENTDATE,CURRENTTIME,USERID,USERNAME,USEREMAIL,USERGENDER,USERDEPARTMENT,USERSEMESTER,USERPHONE;
    Toolbar StudentPortalToolbar;
    RecyclerView recyclerView;
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child(mAuth.getUid()).child("Images").child("Profile Pic");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students__portal);
        StudentPortalToolbar = findViewById(R.id.student_portal_page_toolbar);
        Uid = FirebaseAuth.getInstance().getUid();
        SendMsgButton = findViewById(R.id.send_msg_btn_id);
        UserMessage = findViewById(R.id.etMessages_id);
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        ProgressDialogBox();
        HideTaskBar();









    }


    private void ProgressDialogBox() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }



    private void FirebaseInitialization() {
        USERID = mAuth.getCurrentUser().getUid();
        CurrentUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance();
        StorageRef = mStorage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("USERS").child(mAuth.getUid());

        // Firebase Initialization Ends
    }

    private void HideTaskBar() {
        // Task Bar Hide
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Task Bar Hide
    }

    private void GetDatabaseValuesToVariables() {
        //

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SendDataDB userProfile = dataSnapshot.getValue(SendDataDB.class);
                USERNAME = userProfile.getUserName();
                USEREMAIL = userProfile.getUserEmail();
                USERPHONE = userProfile.getUserPhoneNumber();
                USERGENDER = userProfile.getUserGender();
                USERDEPARTMENT = userProfile.getUserDepartment();
                USERSEMESTER = userProfile.getUserSemester();
                setSupportActionBar(StudentPortalToolbar);
                getSupportActionBar().setTitle(USERSEMESTER);
                progressDialog.dismiss();





                ///
                //
                MessagesRef = FirebaseDatabase.getInstance().getReference().child("BRANCHES").child(USERDEPARTMENT).child(USERSEMESTER);





                FirebaseRecyclerOptions<MessagesModel> options = new FirebaseRecyclerOptions.Builder<MessagesModel>()
                        .setQuery(MessagesRef,MessagesModel.class)
                        .build();
                FirebaseRecyclerAdapter<MessagesModel, MessagesViewHolder> adapter
                        = new FirebaseRecyclerAdapter<MessagesModel, MessagesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final MessagesViewHolder holder, final int position, @NonNull MessagesModel model)
                    {

                            holder.MsgUsrname.setText(model.getName());
                            holder.MsgBody.setText(model.getMessage());
                            holder.MsgDate.setText(model.getDate());
                            holder.MsgTime.setText(model.getTime());


                        //get images
                        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).fit().centerCrop().into(holder.circleImageView);
                            }
                        });
                        // get images




                    }

                    @NonNull
                    @Override
                    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(messages_listitem, parent, false);
                        MessagesViewHolder viewHolder = new MessagesViewHolder(view);
                        return viewHolder;
                    }
                };
                recyclerView.setAdapter(adapter);
                adapter.startListening();

                //
                ///



                SendMsgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendMessageToDB();
                        UserMessage.setText("");

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Students_Portal.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });//


        //













    }



    private void SendMessageToDB(){
        String Message = UserMessage.getText().toString();
        String MessageKey = MessagesRef.push().getKey();
        if(TextUtils.isEmpty(Message)){
            UserMessage.setError("Write Message");
            UserMessage.requestFocus();
        }else {
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            CURRENTDATE = currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            CURRENTTIME = currentTimeFormat.format(calForTime.getTime());

            HashMap<String, Object> groupMessageKey = new HashMap<>();
            MessagesRef.updateChildren(groupMessageKey);

            GroupMessageKeyRef = MessagesRef.child(MessageKey);

            HashMap<String, Object> MessageInfo = new HashMap<>();
            MessageInfo.put("Name", USERNAME);
            MessageInfo.put("Message", Message);
            MessageInfo.put("Date", CURRENTDATE);
            MessageInfo.put("Time", CURRENTTIME);
            MessageInfo.put("Uid",Uid);

            GroupMessageKeyRef.updateChildren(MessageInfo);


        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseInitialization();
        GetDatabaseValuesToVariables();






//

    }
    public static class MessagesViewHolder extends RecyclerView.ViewHolder{

        TextView MsgBody,MsgUsrname,MsgTime,MsgDate;
        CircleImageView circleImageView;
        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            MsgBody = itemView.findViewById(R.id.MsgBody);
            MsgDate = itemView.findViewById(R.id.MsgDate);
            MsgTime = itemView.findViewById(R.id.MsgTime);
            MsgUsrname = itemView.findViewById(R.id.MsgUsername);
            circleImageView = itemView.findViewById(R.id.MsgUser_pic);


        }
    }
}
