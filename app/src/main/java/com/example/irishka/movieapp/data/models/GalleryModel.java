
package com.example.irishka.movieapp.data.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GalleryModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("backdrops")
    @Expose
    // TODO: инициализация null не нужна, как и во многих других моделях
    private List<BackdropModel> backdrops = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<BackdropModel> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<BackdropModel> backdrops) {
        this.backdrops = backdrops;
    }
}
