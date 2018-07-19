package com.example.irishka.movieapp.ui.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface MoviesView extends MvpView {

    void showMovies(List<Movie> movie);

    void finishLoading();
}
