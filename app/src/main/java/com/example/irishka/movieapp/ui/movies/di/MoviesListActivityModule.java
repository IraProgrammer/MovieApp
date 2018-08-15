package com.example.irishka.movieapp.ui.movies.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment;
import com.example.irishka.movieapp.ui.movies.view.MoviesListActivity;
import com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoviesListActivityModule {

    @PerFragment
    @ContributesAndroidInjector(modules = {MainFilmsFragmentModule.class})
    abstract MainFilmsFragment providesMainFilmsFragment();

    @Provides
    @PerActivity
    static ViewPagerAdapter providesViewPagerAdapter(MoviesListActivity moviesListActivity) {
        return new ViewPagerAdapter(moviesListActivity);
    }
}
