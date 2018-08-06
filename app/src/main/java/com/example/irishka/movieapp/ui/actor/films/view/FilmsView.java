package com.example.irishka.movieapp.ui.actor.films.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.data.models.MovieModel;

import java.util.List;

public interface FilmsView extends MvpView {

    void showMovies(List<MovieModel> movie);

}
