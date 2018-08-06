package com.example.irishka.movieapp.ui.actor.info;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class InfoPresenter extends BasePresenter<InfoView> {
    private IMoviesRepository moviesRepository;

    private final long id;

    @Inject
    public InfoPresenter(IMoviesRepository repository, long id) {
        this.moviesRepository = repository;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadInfo(id);
        downloadPhotos(id);
    }

    private void downloadInfo(long id) {

        addDisposables(moviesRepository.getActorInfoModel(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actorInfoModel -> getViewState().showInfo(actorInfoModel), throwable -> {}));
    }

    private void downloadPhotos(long id) {

        addDisposables(moviesRepository.getActorPhotoModel(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actorPhotosModel -> getViewState().showPhotos(actorPhotosModel), throwable -> {}));
    }
}

