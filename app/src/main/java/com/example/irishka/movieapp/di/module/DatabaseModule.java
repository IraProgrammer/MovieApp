package com.example.irishka.movieapp.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.irishka.movieapp.data.database.AppDatabase;
import com.example.irishka.movieapp.data.database.dao.MovieDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {

    @Singleton
    @Provides
    static AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .build();
    }

    @Singleton
    @Provides
    static MovieDao provideMoviesDao(AppDatabase database){
        return database.getMovieDao();
    }
}
