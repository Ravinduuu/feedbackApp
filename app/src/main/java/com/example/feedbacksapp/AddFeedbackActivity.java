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

public class AddFeedbackActivity extends AppCompatActivity {

    private TextInputEditText placeNameEdt, customerNameEdt, ratingEdt, placeImgEdt, feedbackEdt;
    private Button addFeedbackBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String feedbackID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        placeNameEdt = findViewById(R.id.idEdtPlaceName);
        customerNameEdt = findViewById(R.id.idEdtCustomerName);
        ratingEdt = findViewById(R.id.idEdtRating);
        placeImgEdt = findViewById(R.id.idEdtPlaceImageLink);
        feedbackEdt = findViewById(R.id.idEdtFeedback);
        addFeedbackBtn = findViewById(R.id.idBtnAddFeedback);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Feedbacks");

        addFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String placeName = placeNameEdt.getText().toString();
                String customerName = customerNameEdt.getText().toString();
                String rating = ratingEdt.getText().toString();
                String placeImg = placeImgEdt.getText().toString();
                String feedback = feedbackEdt.getText().toString();
                feedbackID = placeName;


                //pass data to the model class
                FeedbackRVModal feedbackRVModal = new FeedbackRVModal(placeName, customerName, rating, placeImg, feedback, feedbackID);

                //adding modal to the database
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(feedbackID).setValue(feedbackRVModal);
                        Toast.makeText(AddFeedbackActivity.this, "Feedback Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddFeedbackActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
                        Toast.makeText(AddFeedbackActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
    }
}