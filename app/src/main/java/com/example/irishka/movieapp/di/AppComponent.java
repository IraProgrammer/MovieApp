package com.example.irishka.movieapp.di;

import android.app.Activity;
import android.content.Context;

import com.example.irishka.movieapp.App;
import com.example.irishka.movieapp.di.module.BuilderModule;
import com.example.irishka.movieapp.di.module.DatabaseModule;
import com.example.irishka.movieapp.di.module.NetworkModule;
import com.example.irishka.movieapp.di.module.RepModule;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {DatabaseModule.class, NetworkModule.class, RepModule.class, BuilderModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
        @BindsInstance
        public abstract Builder context(Context context);
    }
}