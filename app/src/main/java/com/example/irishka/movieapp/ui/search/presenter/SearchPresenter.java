package com.example.irishka.movieapp.ui.search.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.search.view.SearchView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class SearchPresenter extends BasePresenter<SearchView> {

    private IMoviesRepository moviesRepository;

    private int page = 1;

    private String query = "";

    @Inject
    public SearchPresenter(IMoviesRepository repository) {
        this.moviesRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadKeywordsFromDb();
    }

    public void downloadMoviesFromSearch(String query, Boolean isNext) {

        if (!isNext) {
            getViewState().showProgress();
        }

        if (!this.query.equals(query)) {
            this.query = query;
            this.page = 1;
        } else if (this.query.equals(query) && !isNext) {
            this.page = 1;
        }

        addDisposables(moviesRepository.getMoviesFromSearchFromInternet(query, page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> page++)
                .doOnSuccess(movies -> getViewState().hideProgress())
                .doOnSuccess(movies -> {
                    if (movies.size() == 0) getViewState().noFound();
                })
                .doOnError(movies -> {
                    if (!isNext) getViewState().noInternet();
                })
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {
                }));
    }

    public void downloadKeywords(String query) {

        addDisposables(moviesRepository.getKeywordsFromInternet(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(keywords -> getViewState().load(query, keywords), throwable -> {
                }));
    }

    public void downloadKeywordsFromDb() {

        addDisposables(moviesRepository.getKeywordsFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(keywords -> getViewState().load(query, keywords), throwable -> {
                }));
    }
}

