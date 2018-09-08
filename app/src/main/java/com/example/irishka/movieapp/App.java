package com.example.irishka.movieapp;

import android.app.Activity;
import android.app.Application;

import com.example.irishka.movieapp.di.AppComponent;
import com.example.irishka.movieapp.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .context(this)
                .create(this);
    }
}
