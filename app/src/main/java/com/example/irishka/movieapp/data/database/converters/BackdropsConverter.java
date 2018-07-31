package com.example.irishka.movieapp.data.database.converters;

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
    public String fromBackdropsList(List<BackdropDb> backdrops) {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < backdrops.size(); i++) {
            str.append(backdrops.get(i).getFileUrl()).append(", ");
            if (i == backdrops.size() - 1) str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    @TypeConverter
    public List<BackdropDb> toCountriesList(String backdropsStr) {
        List<String> urls = Arrays.asList(backdropsStr.split(","));

        List<BackdropDb> backdrops = new ArrayList<>();

        for (String url: urls) {
            backdrops.add(new BackdropDb(url));
        }

        return backdrops;
    }

}
