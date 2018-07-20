package com.example.irishka.movieapp.ui.movies.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface MoviesListView extends MvpView {

    void showMovies(List<Movie> movie);

    void finishLoading();
}