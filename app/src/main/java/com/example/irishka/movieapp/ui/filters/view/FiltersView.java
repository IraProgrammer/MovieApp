package com.example.irishka.movieapp.ui.filters.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface FiltersView extends MvpView{

    void showProgress();

    void hideProgress();

    void noFound();

    void showSnack();

    void clearList();

    void showMovies(List<Movie> movies);

    void finishLoading();
}
