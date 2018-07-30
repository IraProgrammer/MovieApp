package com.example.irishka.movieapp.data.database;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import com.example.irishka.movieapp.data.database.entity.BackdropDb;
import com.example.irishka.movieapp.data.database.entity.ProductionCountryDb;
import com.example.irishka.movieapp.domain.entity.Backdrop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackdropsConverter {

    @TypeConverter
    public String fromBackdropsList(@Nullable List<BackdropDb> backdrops) {
        StringBuilder str = new StringBuilder();
        for (BackdropDb backdrop: backdrops) {
            str.append(backdrop.getFileUrl()).append(", ");
        }
        if (backdrops.size() == 0) return "";
        return str.deleteCharAt(str.length()-1).toString();
    }

    @TypeConverter
    public List<BackdropDb> toCountriesList(@Nullable String backdropsStr) {
        List<String> urls = Arrays.asList(backdropsStr.split(","));

        List<BackdropDb> backdrops = new ArrayList<>();

        for (String url: urls) {
            backdrops.add(new BackdropDb(url));
        }

        return backdrops;
    }

}
