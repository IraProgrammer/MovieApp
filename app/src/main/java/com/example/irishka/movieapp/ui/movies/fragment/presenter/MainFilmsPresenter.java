package com.example.irishka.movieapp.ui.movies.fragment.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.MainType;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class MainFilmsPresenter extends BasePresenter<MainFilmsView> {

    private IMoviesRepository moviesRepository;

    private int page = 1;

    private MainType type;

    @Inject
    public MainFilmsPresenter(IMoviesRepository repository, MainType type) {
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

        int a = page;

        addDisposables(moviesRepository.downloadMovies(page, type)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(moviesListWithError -> {
                    if (!moviesListWithError.isError()) {
                        getViewState().finishLoading();
                        page++;
                    }
                })
//                .doOnSuccess(moviesListWithError -> {
//                    if (moviesListWithError.getMovies().size() == 0 && moviesListWithError.isError() && page == 1) {
//                        getViewState().noInternetAndEmptyDb();
//                    }
//                })
                .doOnSuccess(moviesListWithError -> getViewState().hideProgress())
                .subscribe(moviesListWithError -> getViewState().showMovies(moviesListWithError), throwable -> {
                }));
    }
}
