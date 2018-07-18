package com.example.irishka.movieapp;

import android.app.Application;

import com.example.irishka.movieapp.data.database.DatabaseManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: вместо getApplicationContext() можно this передавать
        DatabaseManager.getInstance().init(getApplicationContext());
    }
}
