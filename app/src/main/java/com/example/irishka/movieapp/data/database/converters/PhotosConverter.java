package com.example.irishka.movieapp.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.irishka.movieapp.data.database.entity.ImageDb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhotosConverter {

    @TypeConverter
    public String fromPhotosList(List<ImageDb> photos) {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < photos.size(); i++) {
            str.append(photos.get(i).getFileUrl()).append(", ");
            if (i == photos.size() - 1) str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    @TypeConverter
    public List<ImageDb> toPhotosList(String photosStr) {
        List<String> urls = Arrays.asList(photosStr.split(","));

        List<ImageDb> backdrops = new ArrayList<>();

        for (String url: urls) {
            backdrops.add(new ImageDb(url));
        }

        return backdrops;
    }
}
