
package com.example.irishka.movieapp.domain.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCountry {

    public ProductionCountry() {
    }

    public ProductionCountry(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
