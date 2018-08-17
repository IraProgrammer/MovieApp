package com.example.irishka.movieapp.ui.filters.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface FiltersView extends MvpView{

    void showMovies(List<Movie> movies, boolean isFiltred);

    void finishLoading();
}
