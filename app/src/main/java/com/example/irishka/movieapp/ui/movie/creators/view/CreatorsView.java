package com.example.irishka.movieapp.ui.movie.creators.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.data.models.Credits;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

public interface CreatorsView extends MvpView {

    void showCreators(Credits credits);

}
