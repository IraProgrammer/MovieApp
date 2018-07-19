package com.example.irishka.movieapp.di;

import com.example.irishka.movieapp.di.module.MovieModule;
import com.example.irishka.movieapp.ui.view.MoviesActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {MovieModule.class})
@MovieScope
public interface MovieComponent {

    void inject(MoviesActivity activity);

    @Subcomponent.Builder
    interface Builder {
        MovieComponent build();
    }
}
