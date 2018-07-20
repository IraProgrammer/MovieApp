package com.example.irishka.movieapp.ui.film.view;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.data.models.DescriptionModel;

public interface DescriptionView extends MvpView{

    void showDescription(DescriptionModel description);

}
