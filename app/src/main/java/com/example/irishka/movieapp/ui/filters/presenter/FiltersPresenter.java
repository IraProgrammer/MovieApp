package com.example.irishka.movieapp.ui.filters.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.filters.view.FiltersView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class FiltersPresenter extends BasePresenter<FiltersView> {

    private IMoviesRepository moviesRepository;

    private int page = 1;

//    private String sort = "popularity.desc";
//
//    private boolean isFiltred;

    @Inject
    public FiltersPresenter(IMoviesRepository repository) {
        this.moviesRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        downloadForScroll("", "");
    }

    public void downloadForScroll(String sort, String genres) {

        addDisposables(moviesRepository.getWithFilters(page, sort, genres)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> page++)
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies, false), throwable -> {
                }));
    }

    public void downloadMovies(String sort, String genres) {

        page = 1;

        getViewState().clearList();
        getViewState().showProgress();

        addDisposables(moviesRepository.getWithFilters(page, sort, genres)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnError(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> getViewState().hideProgress())
                .subscribe(movies -> getViewState().showMovies(movies, true), throwable -> {
                }));
    }
}
