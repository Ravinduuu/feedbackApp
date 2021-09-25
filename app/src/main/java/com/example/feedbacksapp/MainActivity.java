package com.example.feedbacksapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FeedbackRVAdapter.FeedbackClickInterface{

    private RecyclerView feedbackRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<FeedbackRVModal> feedbackRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private FeedbackRVAdapter feedbackRVAdapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedbackRV = findViewById(R.id.idRVFeedbacks);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Feedbacks");
        feedbackRVModalArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        feedbackRVAdapter = new FeedbackRVAdapter(feedbackRVModalArrayList, this, this);
        feedbackRV.setLayoutManager(new LinearLayoutManager(this));
        feedbackRV.setAdapter(feedbackRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddFeedbackActivity.class));
            }
        });

        getAllFeedbacks();
    }

    private void getAllFeedbacks() {
        feedbackRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                feedbackRVModalArrayList.add(snapshot.getValue(FeedbackRVModal.class));
                feedbackRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                feedbackRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                feedbackRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                feedbackRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public void onFeedbackClick(int position) {
        displayBottomSheet(feedbackRVModalArrayList.get(position));

    }

    private void displayBottomSheet(FeedbackRVModal feedbackRVModal) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_diaog, bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView placeNameTV = layout.findViewById(R.id.idTVPlaceName);
        TextView customerNameTV = layout.findViewById(R.id.idTVCustomerName);
        TextView feedbackTV = layout.findViewById(R.id.idTVFeedback);
        TextView ratingTV = layout.findViewById(R.id.idTVRating);
        ImageView feedbackIV = layout.findViewById(R.id.idIVFeedback);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        //Button viewFeedbackBtn = layout.findViewById(R.id.idBtnViewFeedback);

        placeNameTV.setText(feedbackRVModal.getPlaceName());
        customerNameTV.setText(feedbackRVModal.getCustomerName());
        feedbackTV.setText(feedbackRVModal.getFeedback());
        ratingTV.setText(feedbackRVModal.getRating());
        Picasso.get().load(feedbackRVModal.getPlaceImg()).into(feedbackIV);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditFeedbackActivity.class);
                i.putExtra("feedback", feedbackRVModal);
                startActivity(i);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                Toast.makeText(this, "user logged out..", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}