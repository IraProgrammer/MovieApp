package com.example.irishka.movieapp.ui.film.di;

import android.support.v4.app.Fragment;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.film.view.CreatorsFragment;
import com.example.irishka.movieapp.ui.film.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.film.view.MovieActivity;
import com.example.irishka.movieapp.ui.film.view.TrailersFragment;
import com.example.irishka.movieapp.ui.film.view.ViewPagerAdapter;
import com.example.irishka.movieapp.ui.movies.view.MoviesListActivity;
import com.example.irishka.movieapp.ui.movies.view.MoviesListAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

@Module
public abstract class MovieActivityModule {

    @Provides
    @PerActivity
    static long provideId(MovieActivity movieActivity) {
        return movieActivity.getIntent().getLongExtra(MOVIE_ID, 164558);
    }

 /*  @PerFragment
    @ContributesAndroidInjector(modules = {DescriptionFragmentModule.class})
    abstract DescriptionFragment providesDescriptionFragment(); */

    @PerFragment
    @ContributesAndroidInjector(modules = {CreatorsFragmentModule.class})
    abstract CreatorsFragment providesCreatorsFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {TrailersFragmentModule.class})
    abstract TrailersFragment providesTrailersFragment();

    @Provides
    @PerActivity
    static ViewPagerAdapter providesViewPagerAdapter(MovieActivity movieActivity){
        return new ViewPagerAdapter(movieActivity.getSupportFragmentManager());
    }
}
