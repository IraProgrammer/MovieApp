package com.example.irishka.movieapp.ui.movies.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@InjectViewState
public class MainFilmsPresenter extends BasePresenter<MainFilmsView> {

    private IMoviesRepository moviesRepository;

    private int page = 1;

    private String type;

    @Inject
    public MainFilmsPresenter(IMoviesRepository repository, String type) {
        this.moviesRepository = repository;
        this.type = type;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        downloadMovies(false);
    }

    public void downloadMovies(boolean isScroll) {

        if (!isScroll) {
            getViewState().showProgress();
        }

        addDisposables(moviesRepository.downloadMoviesForMainScreen(page, type)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> {
                    if (movies.size() == 0) {
                        getViewState().noInternetAndEmptyDb();
                    }
                })
                .doOnSuccess(movies -> page++)
                .doOnSuccess(movies -> getViewState().hideProgress())
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {
                }));
    }
}
