package com.example.irishka.movieapp.ui.movies.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.movies.view.MoviesListView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class MoviesListPresenter extends BasePresenter<MoviesListView> {

    private IMoviesRepository moviesRepository;

    private int page = 1;

    @Inject
    public MoviesListPresenter(IMoviesRepository repository) {
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
                .subscribe(movies -> getViewState().showMovies(movies)));
    }
}
