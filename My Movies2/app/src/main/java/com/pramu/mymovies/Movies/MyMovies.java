package com.pramu.mymovies.Movies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MyMovies implements Parcelable {
    private int id;
    private String poster_path;
    private String title;
    private String vote;
    private String release;
    private String overview;

    public MyMovies(int id, String title, String description, String image, String release_date, String rating) {
        this.id = id;
        this.title = title;
        this.overview = description;
        this.poster_path = image;
        this.release = release_date;
        this.vote = rating;
    }

    public int getKey() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster_path;
    }

    public void setPoster(String poster) {
        this.poster_path = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster_path);
        dest.writeString(this.title);
        dest.writeString(this.vote);
        dest.writeString(this.release);
        dest.writeString(this.overview);
    }

    MyMovies(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String poster_path = object.getString("poster_path");
            String vote = object.getString("vote_average");
            String release = object.getString("release_date");
            String overview = object.getString("overview");

        this.id = id;
        this.poster_path= poster_path;
        this.title = title;
        this.vote = vote;
        this.release = release;
        this.overview = overview;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected MyMovies(Parcel in) {
        this.id = in.readInt();
        this.poster_path = in.readString();
        this.title = in.readString();
        this.vote = in.readString();
        this.release = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<MyMovies> CREATOR = new Parcelable.Creator<MyMovies>() {
        @Override
        public MyMovies createFromParcel(Parcel source) {
            return new MyMovies(source);
        }

        @Override
        public MyMovies[] newArray(int size) {
            return new MyMovies[size];
        }
    };
}