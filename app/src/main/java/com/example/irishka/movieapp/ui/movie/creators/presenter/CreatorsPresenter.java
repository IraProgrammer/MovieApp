package com.example.irishka.movieapp.ui.movie.creators.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repositories.ICastsRepository;
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
        downloadCasts();
    }

    public void downloadCasts() {

        getViewState().showProgress();

        addDisposables(castsRepository.downloadCasts(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(castListWithError -> {
                    if (castListWithError.getCasts().size() == 0 && castListWithError.isError()) {
                        getViewState().showError();
                    }
                })
                .doOnSuccess(castListWithError -> {
                    if (castListWithError.getCasts().size() == 0 && !castListWithError.isError()) {
                        getViewState().showSorry();
                    }
                })
                .doOnSuccess(castListWithError -> getViewState().hideProgress())
                .doOnSuccess(castListWithError -> {
                    if (!castListWithError.isError()){
                        getViewState().hideError();
                    }
                })
                .subscribe(castListWithError -> getViewState().showCasts(castListWithError.getCasts()), throwable -> {
                }));
    }
}

