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

    private String sort = "popularity.desc";

    private boolean isFiltred;

    @Inject
    public FiltersPresenter(IMoviesRepository repository) {
        this.moviesRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        downloadMovies(sort);
    }

    public void downloadMovies(String sort) {

        if (!this.sort.equals(sort)) {
            this.sort = sort;
            page = 1;
            isFiltred = true;
        } else {
            isFiltred = false;
        }

        addDisposables(moviesRepository.getWithFilters(sort)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> page++)
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies, isFiltred), throwable -> {
                }));
    }
}
