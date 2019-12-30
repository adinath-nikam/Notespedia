package com.thehyperprogrammer.notespedia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

public class Sem_Content extends AppCompatActivity {

    FirebaseDatabase SubjectRef = FirebaseDatabase.getInstance();
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem__content);

        recyclerView = findViewById(R.id.sub_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

















//        ///
//        //
//
//
//
//
//
//        FirebaseRecyclerOptions<MessagesModel> options = new FirebaseRecyclerOptions.Builder<MessagesModel>()
//                .setQuery(SubjectRef,SubjectModel.class)
//                .build();
//        FirebaseRecyclerAdapter<MessagesModel, Students_Portal.MessagesViewHolder> adapter
//                = new FirebaseRecyclerAdapter<MessagesModel, Students_Portal.MessagesViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull final Students_Portal.MessagesViewHolder holder, final int position, @NonNull MessagesModel model)
//            {
//
//                holder.MsgUsrname.setText(model.getName());
//                holder.MsgBody.setText(model.getMessage());
//                holder.MsgDate.setText(model.getDate());
//
//
//            }
//
//            @NonNull
//            @Override
//            public Students_Portal.MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(subjects, parent, false);
//                Students_Portal.MessagesViewHolder viewHolder = new Students_Portal.MessagesViewHolder(view);
//                return viewHolder;
//            }
//        };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//
//        //
//        ///













    }

















}
