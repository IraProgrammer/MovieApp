package com.example.irishka.movieapp.ui.movie.creators.actor.info;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;

public interface InfoView extends MvpView {

    void showPhotos(ActorPhotosModel photosModel);

}
