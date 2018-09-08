package com.example.irishka.movieapp.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RelatedConverter {
    @TypeConverter
    public String fromRelstedList(List<Long> related) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < related.size(); i++) {
            str.append(related.get(i)).append(", ");
            if (i == related.size() - 1) str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    @TypeConverter
    public List<Long> toRelatedList(String relatedStr) {
        List<String> related = Arrays.asList(relatedStr.split(","));

        List<Long> reldtedLong = new ArrayList<>();

        for (String id: related) {
            reldtedLong.add(Long.parseLong(id));
        }

        return reldtedLong;
    }

}
