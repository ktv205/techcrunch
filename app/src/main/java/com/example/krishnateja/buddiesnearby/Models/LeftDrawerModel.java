package com.example.krishnateja.buddiesnearby.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class LeftDrawerModel implements Parcelable {
    private  int res;
    private String name;

    public LeftDrawerModel() {

    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<LeftDrawerModel> CREATOR=new Parcelable.Creator<LeftDrawerModel>(){

        @Override
        public LeftDrawerModel createFromParcel(Parcel source) {
            return new LeftDrawerModel(source);
        }

        @Override
        public LeftDrawerModel[] newArray(int size) {
            return new LeftDrawerModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(res);
        dest.writeString(name);
    }

    public LeftDrawerModel(Parcel in){
        res=in.readInt();
        name=in.readString();
    }
}
