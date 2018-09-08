package com.example.irishka.movieapp.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeywordModel {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
