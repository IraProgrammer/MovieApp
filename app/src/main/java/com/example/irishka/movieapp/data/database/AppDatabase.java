package com.example.irishka.movieapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.models.MovieModel;

@Database(entities = {MovieModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "com.example.irishka.movieapp.data.database.movies";

    public abstract MovieDao getMovieDao();
}
