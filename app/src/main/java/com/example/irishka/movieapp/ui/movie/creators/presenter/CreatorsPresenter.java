package com.example.irishka.movieapp.ui.movie.creators.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repositories.ICastsRepository;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class CreatorsPresenter extends BasePresenter<CreatorsView> {

    private ICastsRepository castsRepository;

    private final long movieId;

    @Inject
    CreatorsPresenter(ICastsRepository castsRepository, long movieId) {
        this.castsRepository = castsRepository;
        this.movieId = movieId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadCasts(movieId);
    }

    private void downloadCasts(long movieId) {

        addDisposables(castsRepository.downloadCasts(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(casts -> getViewState().showCasts(casts), throwable -> {}));
    }
}

