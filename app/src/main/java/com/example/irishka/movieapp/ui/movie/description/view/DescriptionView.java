package com.example.irishka.movieapp.ui.movie.description.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface DescriptionView extends MvpView{

    void showProgress();

    void hideProgress();

    void finishLoading();

    void onDownloadError();

    void showDescription(Movie movie);

    void showRelatedMovies(List<Movie> movies);

}
