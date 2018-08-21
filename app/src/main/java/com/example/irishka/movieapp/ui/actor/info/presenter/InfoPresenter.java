package com.example.irishka.movieapp.ui.actor.info.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.actor.info.view.InfoView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class InfoPresenter extends BasePresenter<InfoView> {
    private IMoviesRepository moviesRepository;

    private final long id;

    @Inject
    public InfoPresenter(IMoviesRepository repository, long id) {
        this.moviesRepository = repository;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadInfo();
    }

    public void downloadInfo() {

        getViewState().showProgress();

        addDisposables(moviesRepository.downloadConcreteCast(id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(cast -> getViewState().hideProgress())
                .subscribe(cast -> getViewState().showInfo(cast), throwable -> {}));
    }
}

