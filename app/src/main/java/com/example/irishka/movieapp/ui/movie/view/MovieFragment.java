package com.example.irishka.movieapp.ui.movie.view;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatFragment;

import dagger.android.support.AndroidSupportInjection;

public abstract class MovieFragment extends MvpAppCompatFragment {

    // TODO: теперь нет нужды в этом методе
    // как и в общем фрагменте
    public abstract String getTitle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
