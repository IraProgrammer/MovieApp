package com.example.irishka.movieapp.ui.actor.films.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.interactors.IActorFilmsInteractor;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.actor.films.view.FilmsView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class FilmsPresenter extends BasePresenter<FilmsView> {

    private IActorFilmsInteractor actorFilmsInteractor;

    private long id;

    @Inject
    public FilmsPresenter(IActorFilmsInteractor actorFilmsInteractor, long id) {
        this.actorFilmsInteractor = actorFilmsInteractor;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadFilms();
    }

    private void downloadFilms() {

        addDisposables(actorFilmsInteractor.downloadActorFilms(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));
    }
}

