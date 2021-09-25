package com.example.feedbacksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditFeedbackActivity extends AppCompatActivity {

    private TextInputEditText placeNameEdt, customerNameEdt, ratingEdt, placeImgEdt, feedbackEdt;
    private Button updateFeedbackBtn, deleteFeedbackBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String feedbackID;

    private FeedbackRVModal feedbackRVModal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feedback);

        //initialize database
        firebaseDatabase = FirebaseDatabase.getInstance();


        placeNameEdt = findViewById(R.id.idEdtPlaceName);
        customerNameEdt = findViewById(R.id.idEdtCustomerName);
        ratingEdt = findViewById(R.id.idEdtRating);
        placeImgEdt = findViewById(R.id.idEdtPlaceImageLink);
        feedbackEdt = findViewById(R.id.idEdtFeedback);
        updateFeedbackBtn = findViewById(R.id.idBtnUpdateFeedback);
        deleteFeedbackBtn = findViewById(R.id.idBtnDeleteFeedback);
        loadingPB = findViewById(R.id.idPBLoading);

        //getting the data from previous activity
        feedbackRVModal = getIntent().getParcelableExtra("feedback");
        if(feedbackRVModal!=null){
            placeNameEdt.setText(feedbackRVModal.getPlaceName());
            customerNameEdt.setText(feedbackRVModal.getCustomerName());
            ratingEdt.setText(feedbackRVModal.getRating());
            placeImgEdt.setText(feedbackRVModal.getPlaceImg());
            feedbackEdt.setText(feedbackRVModal.getFeedback());
            feedbackID = feedbackRVModal.getFeedbackID();
        }


        databaseReference = firebaseDatabase.getReference("Feedbacks").child(feedbackID);

        updateFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String placeName = placeNameEdt.getText().toString();
                String customerName = customerNameEdt.getText().toString();
                String rating = ratingEdt.getText().toString();
                String placeImg = placeImgEdt.getText().toString();
                String feedback = feedbackEdt.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("placeName", placeName);
                map.put("customerName", customerName);
                map.put("rating", rating);
                map.put("placeImg", placeImg);
                map.put("feedback", feedback);
                map.put("feedbackID", feedbackID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditFeedbackActivity.this, "Feedback Updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditFeedbackActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
                        Toast.makeText(EditFeedbackActivity.this, "Fail to update feedback", Toast.LENGTH_SHORT).show();

                    }
                });






            }
        });

        deleteFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFeedback();
            }
        });
    }

    private void deleteFeedback() {
        databaseReference.removeValue();
        Toast.makeText(this, "Feedback deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditFeedbackActivity.this, MainActivity.class));
    }
}