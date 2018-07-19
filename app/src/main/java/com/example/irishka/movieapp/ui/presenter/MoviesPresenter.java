package com.example.irishka.movieapp.ui.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.irishka.movieapp.App;
import com.example.irishka.movieapp.data.repository.MoviesRepository;
import com.example.irishka.movieapp.di.MovieScope;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.view.MoviesView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class MoviesPresenter extends MvpPresenter<MoviesView> {

    private IMoviesRepository moviesRepository;

    private Disposable disposable;

    @Inject
    public MoviesPresenter(IMoviesRepository repository) {
        this.moviesRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadMovies();
    }

    public void downloadMovies() {

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = moviesRepository.downloadMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies), Throwable::printStackTrace);
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
