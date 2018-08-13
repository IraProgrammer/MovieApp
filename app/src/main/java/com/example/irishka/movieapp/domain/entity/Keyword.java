package com.example.irishka.movieapp.domain.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keyword {

    private long id;
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
