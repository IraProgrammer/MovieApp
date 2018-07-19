package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.ui.di.MoviesActivityModule;
import com.example.irishka.movieapp.ui.view.MoviesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = AndroidSupportInjectionModule.class)
public abstract class BuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = MoviesActivityModule.class)
    abstract MoviesActivity provideMoviesActivity();
}
