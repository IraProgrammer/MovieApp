package com.example.irishka.movieapp.ui.search.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface SearchView extends MvpView {

    void finishLoading();

  //  void setItems(List<String> items);

    void load(String query, List<String> items);

    void showMovies(List<Movie> movies);

}
