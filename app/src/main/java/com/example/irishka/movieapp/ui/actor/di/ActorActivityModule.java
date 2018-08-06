package com.example.irishka.movieapp.ui.actor.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.actor.view.ActorActivity;
import com.example.irishka.movieapp.ui.actor.view.ActorViewPagerAdapter;
import com.example.irishka.movieapp.ui.actor.films.view.FilmsFragment;
import com.example.irishka.movieapp.ui.actor.info.InfoFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment.PERSON_ID;

@Module
public abstract class ActorActivityModule {

    @Provides
    @PerActivity
    static long provideId(ActorActivity actorActivity) {
        return actorActivity.getIntent().getLongExtra(PERSON_ID, 0);
    }

    @PerFragment
    @ContributesAndroidInjector(modules = {InfoFragmentModule.class})
    abstract InfoFragment providesInfoFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {FilmsFragmentModule.class})
    abstract FilmsFragment providesFilmsFragment();

    @Provides
    @PerActivity
    static ActorViewPagerAdapter providesActorViewPagerAdapter(ActorActivity actorActivity){
        return new ActorViewPagerAdapter(actorActivity);
    }

}
