package com.example.irishka.movieapp;

import android.app.Application;

import com.example.irishka.movieapp.data.database.DatabaseManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.getInstance().init(this);
    }
}
