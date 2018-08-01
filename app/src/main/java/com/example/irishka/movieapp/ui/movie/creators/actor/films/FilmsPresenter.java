package com.example.irishka.movieapp.ui.movie.creators.actor.films;

import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;

import javax.inject.Inject;

public class FilmsPresenter extends BasePresenter<FilmsView> {
    private IMoviesRepository moviesRepository;

    private final long movieId;

    @Inject
    public FilmsPresenter(IMoviesRepository repository, long movieId) {
        this.moviesRepository = repository;
        this.movieId = movieId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
     //   downloadCasts(movieId);
    }

//    public void downloadCasts(long movieId) {
//
//        addDisposables(moviesRepository.downloadCasts(movieId)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(casts -> getViewState().showCasts(casts), Throwable::printStackTrace));
//    }
}

