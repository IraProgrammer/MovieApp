package com.example.irishka.movieapp.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.irishka.movieapp.data.database.entity.ProductionCountryDb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhotosUrlConverter {

    @TypeConverter
    public String fromPhotosList(List<String> photosUrl) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < photosUrl.size(); i++) {
            str.append(photosUrl.get(i)).append(", ");
            if (i == photosUrl.size() - 1) str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    @TypeConverter
    public List<String> toPhotosList(String photosUrlStr) {
        return Arrays.asList(photosUrlStr.split(","));
    }

}
