package com.example.irishka.movieapp.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.irishka.movieapp.data.database.AppDatabase;
import com.example.irishka.movieapp.data.database.dao.BackdropDao;
import com.example.irishka.movieapp.data.database.dao.CastDao;
import com.example.irishka.movieapp.data.database.dao.DescriptionDao;
import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfDescriptionDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;

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

    @Singleton
    @Provides
    static BackdropDao provideBackdropDao(AppDatabase database){
        return database.getBackdropDao();
    }

    @Singleton
    @Provides
    static CastDao provideCastDao(AppDatabase database){
        return database.getCastDao();
    }

    @Singleton
    @Provides
    static GenreDao provideGenreDao(AppDatabase database){
        return database.getGenreDao();
    }

    @Singleton
    @Provides
    static GenreOfDescriptionDao provideGenreOfDescriptionDao(AppDatabase database){
        return database.getGenreOfDescroptionDao();
    }

    @Singleton
    @Provides
    static DescriptionDao provideDescriptionDao(AppDatabase database){
        return database.getDescriptionDao();
    }
}
