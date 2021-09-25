package com.example.feedbacksapp;


import android.os.Parcel;
import android.os.Parcelable;

public class FeedbackRVModal implements Parcelable {
    private String placeName;
    private String customerName;
    private String rating;
    private String placeImg;
    private String feedback;
    private String feedbackID;

    public FeedbackRVModal() {

    }

    public FeedbackRVModal(String placeName, String customerName, String rating, String placeImg, String feedback, String feedbackID) {
        this.placeName = placeName;
        this.customerName = customerName;
        this.rating = rating;
        this.placeImg = placeImg;
        this.feedback = feedback;
        this.feedbackID = feedbackID;
    }

    protected FeedbackRVModal(Parcel in) {
        placeName = in.readString();
        customerName = in.readString();
        rating = in.readString();
        placeImg = in.readString();
        feedback = in.readString();
        feedbackID = in.readString();
    }

    public static final Creator<FeedbackRVModal> CREATOR = new Creator<FeedbackRVModal>() {
        @Override
        public FeedbackRVModal createFromParcel(Parcel in) {
            return new FeedbackRVModal(in);
        }

        @Override
        public FeedbackRVModal[] newArray(int size) {
            return new FeedbackRVModal[size];
        }
    };

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPlaceImg() {
        return placeImg;
    }

    public void setPlaceImg(String placeImg) {
        this.placeImg = placeImg;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeString(customerName);
        dest.writeString(rating);
        dest.writeString(placeImg);
        dest.writeString(feedback);
        dest.writeString(feedbackID);
    }
}
