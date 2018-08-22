package com.example.irishka.movieapp.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.irishka.movieapp.data.database.entity.ImageDb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImagesConverter {

    @TypeConverter
    public String fromImagesList(List<ImageDb> images) {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < images.size(); i++) {
            str.append(images.get(i).getFileUrl()).append(", ");
            if (i == images.size() - 1) str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    @TypeConverter
    public List<ImageDb> toImagesList(String imagesStr) {
        List<String> urls = Arrays.asList(imagesStr.split(","));

        List<ImageDb> images = new ArrayList<>();

        for (String url: urls) {
            images.add(new ImageDb(url));
        }

        return images;
    }

}
