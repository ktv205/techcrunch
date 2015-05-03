package com.example.krishnateja.buddiesnearby.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kushal on 5/2/15.
 */
public class User implements Parcelable {
    private String _id;
    private String _name;
    private double _latitude;
    private double _longitude;
    private int _colour;

    public User(Parcel source){
        _id = source.readString();
        _name = source.readString();
        _latitude = source.readDouble();
        _longitude = source.readDouble();
        _colour = source.readInt();
    }
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(_name);
        parcel.writeDouble(_latitude);
        parcel.writeDouble(_longitude);
        parcel.writeInt(_colour);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_latitude() {
        return _latitude;
    }

    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    public double get_longitude() {
        return _longitude;
    }

    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    public int get_colour() {
        return _colour;
    }

    public void set_colour(int _colour) {
        this._colour = _colour;
    }
}
