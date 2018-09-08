package com.example.irishka.movieapp.ui.movie.di;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.movie.creators.view.ActorsAdapter;
import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class CreatorsFragmentModule {

    @Provides
    @PerFragment
    static ActorsAdapter provideActorsAdapter(CreatorsFragment creatorsFragment, GlideHelper glideHelper){
        return new ActorsAdapter(creatorsFragment, glideHelper);
    }
}
