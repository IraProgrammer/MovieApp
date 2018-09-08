package com.example.irishka.movieapp.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActorProfileModel {

    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}

