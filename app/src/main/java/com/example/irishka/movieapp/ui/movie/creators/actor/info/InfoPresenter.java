package com.example.irishka.movieapp.ui.movie.creators.actor.info;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class InfoPresenter extends BasePresenter<InfoView> {
    private IMoviesRepository moviesRepository;

    private final long castId;

    @Inject
    public InfoPresenter(IMoviesRepository repository, long castId) {
        this.moviesRepository = repository;
        this.castId = castId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadPhotos(castId);
    }

    public void downloadPhotos(long castId) {

        addDisposables(moviesRepository.getActorPhotoModel(castId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actorPhotosModel -> getViewState().showPhotos(actorPhotosModel), Throwable::printStackTrace));
    }
}

