package com.example.irishka.movieapp.ui.film.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.film.view.DescriptionView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class DescriptionPresenter extends MvpPresenter<DescriptionView> {

    private IMoviesRepository moviesRepository;

    private Disposable disposable;

    //@Inject
    private final long movieId;

    @Inject
    public DescriptionPresenter(IMoviesRepository repository, long movieId) {
        this.moviesRepository = repository;
        this.movieId = movieId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadDescriptions(movieId);
    }

  /*  public void setId(long movieId){
        this.movieId = movieId;
    } */

    public void downloadDescriptions(long movieId) {

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = moviesRepository.downloadDescription(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> getViewState().showDescription(movies), Throwable::printStackTrace);
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
