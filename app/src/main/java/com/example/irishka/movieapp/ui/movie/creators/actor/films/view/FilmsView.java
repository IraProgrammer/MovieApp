package com.example.irishka.movieapp.ui.movie.creators.actor.films.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface FilmsView extends MvpView {

    void showMovies(List<MovieModel> movie);

}
