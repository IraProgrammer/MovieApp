package com.example.irishka.movieapp.di.module;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.ui.slideGallery.ImagePagerActivity;
import com.example.irishka.movieapp.ui.slideGallery.di.ImagePagerActivityModule;
import com.example.irishka.movieapp.ui.actor.di.ActorActivityModule;
import com.example.irishka.movieapp.ui.actor.view.ActorActivity;
import com.example.irishka.movieapp.ui.movie.di.MovieActivityModule;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.example.irishka.movieapp.ui.movies.di.MoviesListActivityModule;
import com.example.irishka.movieapp.ui.movies.view.MoviesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = AndroidSupportInjectionModule.class)
public abstract class BuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = MoviesListActivityModule.class)
    abstract MoviesListActivity provideMoviesActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = MovieActivityModule.class)
    abstract MovieActivity provideMovieActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = ActorActivityModule.class)
    abstract ActorActivity provideActorActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = ImagePagerActivityModule.class)
    abstract ImagePagerActivity provideImagePagerActivity();

}
