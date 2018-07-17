package com.example.irishka.movieapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.irishka.movieapp.data.MovieModel;

@Database(entities = {MovieModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao getMovieDao();
}
