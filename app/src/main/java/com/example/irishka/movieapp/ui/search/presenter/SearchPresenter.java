package com.example.irishka.movieapp.ui.search.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.interactors.ISearchInteractor;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.search.view.SearchView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class SearchPresenter extends BasePresenter<SearchView> {

    private ISearchInteractor searchInteractor;

    private int page = 1;

    private String query = "";

    @Inject
    public SearchPresenter(ISearchInteractor searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadKeywordsFromDb();
        downloadMoviesFromDatabase();
    }

    public void downloadMoviesFromSearch(String query, Boolean isNext) {

        if (!isNext || page == 1) {
            getViewState().showProgress();
        }

        if (!this.query.equals(query)) {
            this.query = query;
            this.page = 1;
        } else if (this.query.equals(query) && !isNext) {
            this.page = 1;
        }

        addDisposables(searchInteractor.getMoviesFromSearchFromInternet(query, page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(moviesListWithError -> {
                    if (!moviesListWithError.isError()) {
                        getViewState().finishLoading();
                        getViewState().hideSnack();
                    }
                })
                .doOnSuccess(moviesListWithError -> {
                    if (moviesListWithError.getMovies().size() == 0 && page == 1 && !moviesListWithError.isError())
                        getViewState().noFound();
                })
                .doOnSuccess(moviesListWithError -> {
                    if (moviesListWithError.isError()) {
                        getViewState().showSnack();
                    }
                })
                .doOnSuccess(moviesListWithError -> {
                    if (!moviesListWithError.isError()) {
                        page++;
                    }
                })
                .doOnSuccess(movies -> getViewState().hideProgress())
                .subscribe(moviesListWithError -> getViewState().showMovies(moviesListWithError.getMovies()), throwable -> {
                }));
    }

    private void downloadMoviesFromDatabase() {

        addDisposables(searchInteractor.getMoviesForSearchFromDatabase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {
                }));
    }

    public void downloadKeywords(String query) {

        addDisposables(searchInteractor.getKeywordsFromInternet(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(keywords -> getViewState().load(query, keywords), throwable -> {
                }));
    }

    public void downloadKeywordsFromDb() {

        addDisposables(searchInteractor.getKeywordsFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(keywords -> getViewState().load(query, keywords), throwable -> {
                }));
    }
}

