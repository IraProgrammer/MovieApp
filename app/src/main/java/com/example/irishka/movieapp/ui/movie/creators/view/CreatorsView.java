package com.example.irishka.movieapp.ui.movie.creators.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.domain.entity.Cast;

import java.util.List;

public interface CreatorsView extends MvpView {

    void hideProgress();

    void hideError();

    void showError();

    void showSorry();

    void showCasts(List<Cast> casts);

}
