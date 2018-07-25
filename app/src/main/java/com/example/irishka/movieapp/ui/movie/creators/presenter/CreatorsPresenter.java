package com.example.irishka.movieapp.ui.movie.creators.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsView;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class CreatorsPresenter extends MvpPresenter<CreatorsView> {

    private IMoviesRepository moviesRepository;

    private Disposable disposable;

    private final long movieId;

    @Inject
    public CreatorsPresenter(IMoviesRepository repository, long movieId) {
        this.moviesRepository = repository;
        this.movieId = movieId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadCasts(movieId);
    }

    public void downloadCasts(long movieId) {

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = moviesRepository.downloadCasts(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(casts -> getViewState().showCasts(casts), Throwable::printStackTrace);
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}

