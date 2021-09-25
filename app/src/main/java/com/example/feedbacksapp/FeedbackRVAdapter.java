package com.example.feedbacksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedbackRVAdapter extends RecyclerView.Adapter<FeedbackRVAdapter.ViewHolder> {

    private ArrayList<FeedbackRVModal> feedbackRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private FeedbackClickInterface feedbackClickInterface;

    public FeedbackRVAdapter(ArrayList<FeedbackRVModal> feedbackRVModalArrayList, Context context, FeedbackClickInterface feedbackClickInterface) {
        this.feedbackRVModalArrayList = feedbackRVModalArrayList;
        this.context = context;
        this.feedbackClickInterface = feedbackClickInterface;
    }

    @NonNull
    @Override
    public FeedbackRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedback_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackRVAdapter.ViewHolder holder, int position) {
        FeedbackRVModal feedbackRVModal = feedbackRVModalArrayList.get(position);
        holder.placeNameTV.setText(feedbackRVModal.getPlaceName());
        holder.customerNameTV.setText(feedbackRVModal.getCustomerName());
        Picasso.get().load(feedbackRVModal.getPlaceImg()).into(holder.feedbackIV);
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackClickInterface.onFeedbackClick(position);
            }
        });

    }

    private void setAnimation(View itemView, int position) {
        if(position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }


    @Override
    public int getItemCount() {
        return feedbackRVModalArrayList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        private TextView placeNameTV, customerNameTV;
        private ImageView feedbackIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeNameTV = itemView.findViewById(R.id.idTVPlaceName);
            customerNameTV = itemView.findViewById(R.id.idTVCustomerName);
            feedbackIV = itemView.findViewById(R.id.idIVFeedback);
        }
    }

    public interface FeedbackClickInterface{
        void onFeedbackClick(int position);
    }
}
