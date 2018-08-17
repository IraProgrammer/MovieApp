package com.example.irishka.movieapp.ui.movies.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.NOW_PLAYING;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.POPULAR;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.TOP_RATED;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.UPCOMING;

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

        downloadMovies();
    }

    public void downloadMovies(){


        addDisposables(moviesRepository.downloadMoviesForMainScreen(page, type)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> page++)
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));
    }
}
