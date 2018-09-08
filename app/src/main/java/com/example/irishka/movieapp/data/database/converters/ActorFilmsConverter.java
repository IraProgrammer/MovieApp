package com.example.irishka.movieapp.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActorFilmsConverter {
    @TypeConverter
    public String fromActorFilmsList(List<Long> actorFilms) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < actorFilms.size(); i++) {
            str.append(actorFilms.get(i)).append(", ");
            if (i == actorFilms.size() - 1) str.deleteCharAt(str.length()-1);
        }
        return str.toString();
    }

    @TypeConverter
    public List<Long> toActorFilmsList(String actorFilmsStr) {
        List<String> actorFilms = Arrays.asList(actorFilmsStr.split(","));

        List<Long> actorFilmsLong = new ArrayList<>();

        for (String id: actorFilms) {
            actorFilmsLong.add(Long.parseLong(id));
        }

        return actorFilmsLong;
    }

}

