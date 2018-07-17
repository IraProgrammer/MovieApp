package com.example.irishka.movieapp.view;

import android.content.Context;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.model.Pojo.ConcreteMovie;

import java.util.List;

public interface MoviesView extends MvpView {

    void showMovies(List<ConcreteMovie> movie);

    void initDatabase();

}
