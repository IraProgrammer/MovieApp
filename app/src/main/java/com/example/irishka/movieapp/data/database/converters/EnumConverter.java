package com.example.irishka.movieapp.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.irishka.movieapp.domain.MainType;

public class EnumConverter {

    @TypeConverter
    public int fromTabs(MainType tabs) {
        return tabs.ordinal();
    }

    @TypeConverter
    public MainType toTabs(int number) {

        return MainType.values()[number];
    }
}