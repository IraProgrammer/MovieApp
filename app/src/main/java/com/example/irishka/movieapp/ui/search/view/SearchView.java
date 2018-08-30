package com.example.irishka.movieapp.ui.search.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface SearchView extends MvpView {

    void finishLoading();

    void load(String query, List<String> items);

    void showProgress();

    void hideProgress();

    void noFound();

    void showSnack();

    void hideSnack();

    void showMovies(List<Movie> movies);

}
