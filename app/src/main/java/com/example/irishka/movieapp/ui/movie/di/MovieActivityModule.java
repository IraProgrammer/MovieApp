package com.example.irishka.movieapp.ui.movie.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.example.irishka.movieapp.ui.movie.view.ViewPagerAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

@Module
public abstract class MovieActivityModule {

    @Provides
    @PerActivity
    static long provideId(MovieActivity movieActivity) {
        return movieActivity.getIntent().getLongExtra(MOVIE_ID, 0);
    }

   @PerFragment
    @ContributesAndroidInjector(modules = {DescriptionFragmentModule.class})
    abstract DescriptionFragment providesDescriptionFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {CreatorsFragmentModule.class})
    abstract CreatorsFragment providesCreatorsFragment();

    @Provides
    @PerActivity
    static ViewPagerAdapter providesViewPagerAdapter(MovieActivity movieActivity){
        return new ViewPagerAdapter(movieActivity);
    }
}
