package com.example.irishka.movieapp;

import android.app.Application;

import com.example.irishka.movieapp.di.AppComponent;
import com.example.irishka.movieapp.di.DaggerAppComponent;
import com.example.irishka.movieapp.di.MovieComponent;

public class App extends Application {

    private static AppComponent appComponent;
    private static MovieComponent movieComponent;

    public AppComponent buildAppComponent(){
        return DaggerAppComponent.builder()
                .context(this)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }

    public static MovieComponent buildMovieComponent() {
        if(movieComponent == null) {
            movieComponent = appComponent.movieComponentBuilder().build();
    }
        return movieComponent;
    }

    public void clearRepComponent() {
        movieComponent = null;
    }
}
