package com.example.irishka.movieapp.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.irishka.movieapp.BuildConfig;
import com.example.irishka.movieapp.database.AppDatabase;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseManager {

    private static final String DATABASE_NAME = "com.example.irishka.movieapp.database.movies";

    private static DatabaseManager databaseManager;

    private AppDatabase appDatabase;

    private DatabaseManager(){
    }

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

