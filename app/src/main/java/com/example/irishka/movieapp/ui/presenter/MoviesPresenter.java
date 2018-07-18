package com.example.irishka.movieapp.ui.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.irishka.movieapp.data.repository.MoviesRepository;
import com.example.irishka.movieapp.domain.IMoviesRepository;
import com.example.irishka.movieapp.ui.view.MoviesView;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class MoviesPresenter extends MvpPresenter<MoviesView> {

    private IMoviesRepository moviesRepository = new MoviesRepository();

    private Disposable disposable;

    // TODO: я предложил плохую идею, все таки лучше вернуть в активити
    // а в цепочке rx поставить на doOnSuccess и doOnError установку isLoading в false
    // всмысле ты будешь обращаться к view (например, методы будет finishLoading())
    private boolean isLoading;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public MoviesPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        downloadMovies();
    }

    public void downloadMovies() {

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = moviesRepository.downloadMovies()
                .subscribe(movies -> getViewState().showMovies(movies), Throwable::printStackTrace);
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
