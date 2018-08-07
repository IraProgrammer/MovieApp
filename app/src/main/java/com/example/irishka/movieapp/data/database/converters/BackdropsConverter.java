package com.example.irishka.movieapp.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.irishka.movieapp.data.database.entity.ImageDb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackdropsConverter {

    @TypeConverter
    public String fromBackdropsList(List<ImageDb> backdrops) {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < backdrops.size(); i++) {
            str.append(backdrops.get(i).getFileUrl()).append(", ");
            if (i == backdrops.size() - 1) str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    @TypeConverter
    public List<ImageDb> toBackdropsList(String backdropsStr) {
        List<String> urls = Arrays.asList(backdropsStr.split(","));

        List<ImageDb> backdrops = new ArrayList<>();

        for (String url: urls) {
            backdrops.add(new ImageDb(url));
        }

        return backdrops;
    }

}
