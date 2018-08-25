package com.example.irishka.movieapp.ui.movie.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.example.irishka.movieapp.ui.movie.view.MovieViewPagerAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment.MOVIE_ID;
import static com.example.irishka.movieapp.ui.search.view.SearchActivity.IS_SEARCH;

@Module
public abstract class MovieActivityModule {

    @Provides
    @PerActivity
    static long provideId(MovieActivity movieActivity) {
        return movieActivity.getIntent().getLongExtra(MOVIE_ID, 0);
    }

    @Provides
    @PerActivity
    static boolean provideIsSearch(MovieActivity movieActivity) {
        return movieActivity.getIntent().getBooleanExtra(IS_SEARCH, false);
    }

    @PerFragment
    @ContributesAndroidInjector(modules = {DescriptionFragmentModule.class})
    abstract DescriptionFragment providesDescriptionFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {CreatorsFragmentModule.class})
    abstract CreatorsFragment providesCreatorsFragment();

    @Provides
    @PerActivity
    static MovieViewPagerAdapter providesViewPagerAdapter(MovieActivity movieActivity) {
        return new MovieViewPagerAdapter(movieActivity);
    }
}
