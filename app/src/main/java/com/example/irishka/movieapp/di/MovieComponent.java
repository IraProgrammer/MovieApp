package com.example.irishka.movieapp.di;

import com.example.irishka.movieapp.ui.view.MoviesActivity;

import dagger.Subcomponent;

@Subcomponent
@MovieScope
public interface MovieComponent {

    void inject(MoviesActivity activity);

    @Subcomponent.Builder
    interface Builder {
        MovieComponent build();
    }
}
