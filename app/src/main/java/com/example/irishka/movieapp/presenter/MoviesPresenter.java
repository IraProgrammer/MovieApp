package com.example.irishka.movieapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.irishka.movieapp.model.ApiManager;
import com.example.irishka.movieapp.model.Pojo.ConcreteMovie;
import com.example.irishka.movieapp.model.Pojo.MoviePage;
import com.example.irishka.movieapp.view.MoviesActivity;
import com.example.irishka.movieapp.view.MoviesView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class MoviesPresenter extends MvpPresenter<MoviesView> {

    private ApiManager apiManager = ApiManager.getInstance();

    private Disposable disposable;


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onDownloadMovies();
    }

    private Observable<MoviePage> getMoviesFromInternet(){
        return apiManager.getMoviesApi().getMovies();
    }

    private void onDownloadMovies() {

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = getMoviesFromInternet()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moviePage -> getViewState().showMovies(moviePage.getResults()), Throwable::printStackTrace);
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
