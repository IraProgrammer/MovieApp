package com.example.irishka.movieapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.irishka.movieapp.model.Pojo.ConcreteMovie;
import com.example.irishka.movieapp.model.Pojo.MoviePage;

@Database(entities = {ConcreteMovie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao getMovieDao();
}
