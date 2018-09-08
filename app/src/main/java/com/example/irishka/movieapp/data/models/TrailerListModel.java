package com.example.irishka.movieapp.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerListModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<TrailerModel> results = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerModel> getResults() {
        return results;
    }

    public void setResults(List<TrailerModel> results) {
        this.results = results;
    }

}
