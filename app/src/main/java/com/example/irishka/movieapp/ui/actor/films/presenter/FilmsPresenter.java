package com.example.irishka.movieapp.ui.actor.films.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.actor.films.view.FilmsView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class FilmsPresenter extends BasePresenter<FilmsView> {
    private IMoviesRepository moviesRepository;

    private final long id;

    @Inject
    public FilmsPresenter(IMoviesRepository repository, long id) {
        this.moviesRepository = repository;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadFilms();
    }

    private void downloadFilms() {

        addDisposables(moviesRepository.getActorFilms(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));
    }
}

