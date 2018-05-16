package com.example.android.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class TrailerResponceValue  implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private ArrayList<Videos> videos = new ArrayList<>();
    public final static Parcelable.Creator<TrailerResponceValue> CREATOR = new Creator<TrailerResponceValue>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TrailerResponceValue createFromParcel(Parcel in) {
            return new TrailerResponceValue(in);
        }

        public TrailerResponceValue[] newArray(int size) {
            return (new TrailerResponceValue[size]);
        }

    }
            ;

    protected TrailerResponceValue(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.videos, (com.example.android.popularmovies.Models.Videos.class.getClassLoader()));
    }

    public TrailerResponceValue() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Videos> getVideos() {
        return videos;
    }

    public void setResults(ArrayList<Videos> results) {
        this.videos = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(videos);
    }

    public int describeContents() {
        return 0;
    }

}
