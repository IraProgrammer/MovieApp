package com.example.irishka.movieapp.ui.movie.creators.actor.films;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;
import com.example.irishka.movieapp.ui.movie.creators.actor.films.view.FilmsView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class FilmsPresenter extends BasePresenter<FilmsView> {
    private IMoviesRepository moviesRepository;

    private final long id;

    @Inject
    public FilmsPresenter(IMoviesRepository repository, long id) {
        this.moviesRepository = repository;
        this.id = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        // TODO: у тебя же id - поле класса, можно не передавать его в dowloadMovies, а сразу там использовать
        downloadFilms(id);
    }

    private void downloadFilms(long id) {

        // TODO: обработка ошибок в rx обязательно, даже если ты укажешь что-то типа такого
        // throwable -> {}
        // иначе у тебя может крашнутся приложение с UndeliverableException
        addDisposables(moviesRepository.getActorFilms(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> getViewState().showMovies(movies)));
    }
}

