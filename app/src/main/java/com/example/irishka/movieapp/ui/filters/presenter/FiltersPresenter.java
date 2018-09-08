package com.example.irishka.movieapp.ui.filters.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.filters.view.FiltersView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class FiltersPresenter extends BasePresenter<FiltersView> {

    private IMoviesRepository moviesRepository;

    private int page = 1;

    private String sort = "";

    private String genres = "";

    @Inject
    public FiltersPresenter(IMoviesRepository repository) {
        this.moviesRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        downloadMovies(sort, genres, true);
    }

    public void downloadMovies(String sort, String genres, boolean isNext) {

        if ((!isNext && (!this.sort.equals(sort) || !this.genres.equals(genres))) || page == 1) {

            this.sort = sort;
            this.genres = genres;
            page = 1;
            getViewState().clearList();
            getViewState().showProgress();
        }

        addDisposables(moviesRepository.getWithFilters(page, sort, genres)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(moviesListWithError -> {
                    if (!moviesListWithError.isError()) {
                        getViewState().finishLoading();
                    }
                })
                .doOnSuccess(moviesListWithError -> {
                    if (moviesListWithError.getMovies().size() == 0 && page == 1 && !moviesListWithError.isError())
                        getViewState().noFound();
                })
                .doOnSuccess(moviesListWithError -> {
                    if (moviesListWithError.getMovies().size() == 0 && moviesListWithError.isError()) {
                        getViewState().showSnack();
                    }
                })
                .doOnSuccess(moviesListWithError -> {
                    if (!moviesListWithError.isError()) {
                        page++;
                    }
                })
                .doOnSuccess(moviesListWithError -> getViewState().hideProgress())
                .subscribe(moviesListWithError -> getViewState().showMovies(moviesListWithError.getMovies()), throwable -> {
                }));
    }
}
