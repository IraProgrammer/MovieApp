package com.example.irishka.movieapp.ui.actor.info;

import com.arellomobile.mvp.MvpView;
import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;

public interface InfoView extends MvpView {

    void showInfo(ActorInfoModel photosModel);

    void showPhotos(ActorPhotosModel photosModel);

}
