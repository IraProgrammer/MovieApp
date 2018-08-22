package com.example.irishka.movieapp.ui.movies.fragment.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

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

        addDisposables(moviesRepository.downloadMovies(page, type)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> {
                    if (movies.size() == 0) {
                        getViewState().noInternetAndEmptyDb();
                    }
                })
                .doOnSuccess(movies -> page++)
                .doOnSuccess(movies -> getViewState().hideProgress())
             //   .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {
                }));
    }
}
