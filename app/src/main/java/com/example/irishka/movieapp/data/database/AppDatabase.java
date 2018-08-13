package com.example.irishka.movieapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.irishka.movieapp.data.database.dao.CastDao;
import com.example.irishka.movieapp.data.database.dao.CastOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.KeywordsDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.dao.RelatedOfMovieDao;
import com.example.irishka.movieapp.data.database.entity.ImageDb;
import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.database.entity.GenreDb;
import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;
import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.database.entity.ProductionCountryDb;
import com.example.irishka.movieapp.data.database.entity.RelatedOfMovie;

@Database(entities = {MovieDb.class, ImageDb.class, CastDb.class,
        GenreDb.class, GenreOfMovie.class,
        ProductionCountryDb.class, CastOfMovie.class,
        RelatedOfMovie.class, KeywordDb.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "com.example.irishka.movieapp.data.database.movies";

    public abstract MovieDao getMovieDao();

    public abstract CastDao getCastDao();

    public abstract GenreDao getGenreDao();

    public abstract GenreOfMovieDao getGenreOfDescroptionDao();

    public abstract CastOfMovieDao getCastOfMovieDao();

    public abstract RelatedOfMovieDao getRelatedOfMovieDao();

    public abstract KeywordsDao getKeywordsDao();
}
