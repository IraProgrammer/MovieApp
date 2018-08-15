package com.example.irishka.movieapp.ui.movies.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class MainFilmsPresenter extends BasePresenter<MainFilmsView> {

    private IMoviesRepository moviesRepository;

    private int page = 1;

    @Inject
    public MainFilmsPresenter(IMoviesRepository repository) {
        this.moviesRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadMovies();
    }

    public void downloadMovies() {

        addDisposables(moviesRepository.downloadMovies(page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> page++)
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));
    }
}
