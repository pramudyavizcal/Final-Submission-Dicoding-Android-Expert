package com.pramu.mymovies.TV;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MyTV implements Parcelable {
    private int id;
    private String judulTV;
    private String overviewTV;
    private String skorPengunjungTV;
    private String backdrop_path;
    private String release;

    public MyTV(int id, String title, String description, String image, String release_date, String rating) {
        this.id = id;
        this.judulTV = title;
        this.overviewTV = description;
        this.backdrop_path = image;
        this.release = release_date;
        this.skorPengunjungTV = rating;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getJudulTV() {
        return judulTV;
    }

    public void setJudulTV(String judulTV) {
        this.judulTV = judulTV;
    }

    public String getOverviewTV() {
        return overviewTV;
    }

    public void setOverviewTV(String overviewTV) {
        this.overviewTV = overviewTV;
    }

    public String getSkorPengunjungTV() {
        return skorPengunjungTV;
    }

    public void setSkorPengunjungTV(String skorPengunjungTV) {
        this.skorPengunjungTV = skorPengunjungTV;
    }

    public String getposterTV() {
        return backdrop_path;
    }

    public void setposterTV(String posterTV) {
        this.backdrop_path = posterTV;
    }

    public String getrelease() {
        return release;
    }

    public void setrelease(String release) {
        this.release = release;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judulTV);
        dest.writeString(this.overviewTV);
        dest.writeString(this.skorPengunjungTV);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.release);
    }

    public MyTV(JSONObject object) {
        try {
            int id = object.getInt("id");
            String judulTV =  object.getString("name");
            String overviewTV = object.getString("overview");
            String skorPengunjungTV = object.getString("vote_average");
            String backdrop_path = object.getString("backdrop_path");
            String release = object.getString("first_air_date");

            this.id = id;
            this.judulTV = judulTV;
            this.overviewTV = overviewTV;
            this.skorPengunjungTV = skorPengunjungTV;
            this.backdrop_path = backdrop_path;
            this.release = release;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected MyTV(Parcel in) {
        this.id = in.readInt();
        this.judulTV = in.readString();
        this.overviewTV = in.readString();
        this.skorPengunjungTV = in.readString();
        this.backdrop_path = in.readString();
        this.release = in.readString();
    }

    public static final Parcelable.Creator<MyTV> CREATOR = new Parcelable.Creator<MyTV>() {
        @Override
        public MyTV createFromParcel(Parcel source) {
            return new MyTV(source);
        }

        @Override
        public MyTV[] newArray(int size) {
            return new MyTV[size];
        }
    };
}
