package com.example.izzy.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by izzyengelbert on 2/10/2018.
 */

public class Weather implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Weather createFromParcel(Parcel in){
            return new Weather(in);
        }

        public Weather[] newArray(int size){
            return new Weather[size];
        }
    };

    private String id;
    private String status;
    private Double tempMax;
    private Double tempMin;
    private String time;

    public Weather(String id, String status, Double tempMax, Double tempMin, String time){
        this.id = id;
        this.status = status;
        this.tempMax = tempMax;
        this.tempMin = tempMax;
        this.time = time;
    }

    public Weather(Parcel in) {
        this.id = in.readString();
        this.status = in.readString();
        this.tempMax = in.readDouble();
        this.tempMin = in.readDouble();
        this.time = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.status);
        parcel.writeDouble(this.tempMax);
        parcel.writeDouble(this.tempMin);
        parcel.writeString(this.time);
    }
}
