package com.example.irishka.movieapp.data.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseManager {

    private static final String DATABASE_NAME = "com.example.irishka.movieapp.data.database.movies";

    private static DatabaseManager databaseManager;

    private AppDatabase appDatabase;

    private DatabaseManager() {
    }

    /* public MovieDao getMoviesDao() {
        return appDatabase.getMovieDao();
    } */

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
        }

        return databaseManager;
    }

    public void init(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }
}

