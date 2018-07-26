package com.example.irishka.movieapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.irishka.movieapp.data.database.dao.BackdropDao;
import com.example.irishka.movieapp.data.database.dao.CastDao;
import com.example.irishka.movieapp.data.database.dao.CountriesOfDescriptionDao;
import com.example.irishka.movieapp.data.database.dao.DescriptionDao;
import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfDescriptionDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.dao.ProductionCountryDao;
import com.example.irishka.movieapp.data.database.entity.BackdropDb;
import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.CountriesOfDescription;
import com.example.irishka.movieapp.data.database.entity.DescriptionDb;
import com.example.irishka.movieapp.data.database.entity.GenreDb;
import com.example.irishka.movieapp.data.database.entity.GenresOfDescription;
import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.database.entity.ProductionCountryDb;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;

@Database(entities = {MovieDb.class, BackdropDb.class, CastDb.class,
        GenreDb.class, DescriptionDb.class, GenresOfDescription.class,
        ProductionCountryDb.class, CountriesOfDescription.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "com.example.irishka.movieapp.data.database.movies";

    public abstract MovieDao getMovieDao();

    public abstract BackdropDao getBackdropDao();

    public abstract CastDao getCastDao();

    public abstract GenreDao getGenreDao();

    public abstract GenreOfDescriptionDao getGenreOfDescroptionDao();

    public abstract DescriptionDao getDescriptionDao();

    public abstract ProductionCountryDao getProductionCountryDao();

    public abstract CountriesOfDescriptionDao getCountriesOfDescriptionDao();
}
