package com.example.irishka.movieapp.ui.movie.description.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface DescriptionView extends MvpView{

    void finishLoading();

    void showDescription(Movie movie);

    void showRelatedMovies(List<Movie> movies);

}
