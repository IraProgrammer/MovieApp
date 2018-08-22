package com.example.irishka.movieapp.ui.movie.description.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class DescriptionPresenter extends BasePresenter<DescriptionView> {

    private IMoviesRepository moviesRepository;

    private final long movieId;

    private int page = 1;

    @Inject
    public DescriptionPresenter(IMoviesRepository repository, long movieId) {
        this.moviesRepository = repository;
        this.movieId = movieId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadDescription();
        downloadRelatedMovies(false);
    }

    public void downloadDescription() {

        getViewState().showProgress();

        addDisposables(moviesRepository.downloadMovie(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(movies -> getViewState().onDownloadError())
                .doOnSuccess(movie -> getViewState().hideProgress())
                .subscribe(movie -> getViewState().showDescription(movie), throwable -> {
                }));
    }

    public void downloadRelatedMovies(boolean isScroll) {

        if (!isScroll) {
            page = 1;
        }

        addDisposables(moviesRepository.downloadRelatedMovies(movieId, page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> page++)
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showRelatedMovies(movies), throwable -> {
                }));
    }
}
