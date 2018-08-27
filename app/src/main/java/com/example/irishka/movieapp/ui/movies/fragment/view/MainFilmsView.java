package com.example.irishka.movieapp.ui.movies.fragment.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface MainFilmsView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMovies(List<Movie> movie);

    void noInternetAndEmptyDb();

    void finishLoading();

    void showSnack();

    void hideSnack();
}
