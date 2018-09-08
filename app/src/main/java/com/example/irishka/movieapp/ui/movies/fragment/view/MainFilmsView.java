package com.example.irishka.movieapp.ui.movies.fragment.view;

import android.view.View;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.MoviesListWithError;

import java.util.List;

public interface MainFilmsView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMovies(MoviesListWithError moviesListWithError);

    void noInternetAndEmptyDb();

    void finishLoading();

  //  void showSnack();
}
