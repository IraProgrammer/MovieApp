package com.example.irishka.movieapp.ui.movie.creators.actor.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.movie.creators.actor.ActorActivity;
import com.example.irishka.movieapp.ui.movie.creators.actor.ActorViewPagerAdapter;
import com.example.irishka.movieapp.ui.movie.creators.actor.films.FilmsFragment;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.InfoFragment;
import com.example.irishka.movieapp.ui.movie.di.ReviewFragmentModule;
import com.example.irishka.movieapp.ui.movie.review.ReviewFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment.CAST_ID;

@Module
public abstract class ActorActivityModule {

    @Provides
    @PerActivity
    static long provideId(ActorActivity actorActivity) {
        return actorActivity.getIntent().getLongExtra(CAST_ID, 0);
    }

    @PerFragment
    @ContributesAndroidInjector(modules = {InfoFragmentModule.class})
    abstract InfoFragment providesInfoFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {FilmsFragmentModule.class})
    abstract FilmsFragment providesFilmsFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {ReviewFragmentModule.class})
    abstract ReviewFragment providesTrailersFragment();

    @Provides
    @PerActivity
    static ActorViewPagerAdapter providesActorViewPagerAdapter(ActorActivity actorActivity){
        return new ActorViewPagerAdapter(actorActivity);
    }

}
