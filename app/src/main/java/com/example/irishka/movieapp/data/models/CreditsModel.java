
package com.example.irishka.movieapp.data.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditsModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("cast")
    @Expose
    private List<CastModel> cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CastModel> getCast() {
        return cast;
    }

    public void setCast(List<CastModel> cast) {
        this.cast = cast;
    }

}
