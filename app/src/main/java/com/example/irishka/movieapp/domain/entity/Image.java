package com.example.irishka.movieapp.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import javax.inject.Inject;

public class Image implements Parcelable {

    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Inject
    public Image() {
    }

    public Image(Parcel in) {
        fileUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(fileUrl);
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {

        @Override
        public Image createFromParcel(Parcel parcel) {
            return new Image(parcel);
        }

        @Override
        public Image[] newArray(int i) {
            return new Image[i];
        }
    };
}
