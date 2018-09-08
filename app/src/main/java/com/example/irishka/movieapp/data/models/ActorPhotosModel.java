package com.example.irishka.movieapp.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActorPhotosModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("profiles")
    @Expose
    private List<ActorProfileModel> profiles = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ActorProfileModel> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ActorProfileModel> profiles) {
        this.profiles = profiles;
    }

}
