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
        downloadCredits(movieId);
    }

    public void downloadCredits(long movieId) {

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = moviesRepository.downloadCreators(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> getViewState().showCreators(movies), Throwable::printStackTrace);
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}

