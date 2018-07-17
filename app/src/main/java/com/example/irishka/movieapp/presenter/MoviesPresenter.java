package com.example.irishka.movieapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.irishka.movieapp.model.ApiManager;
import com.example.irishka.movieapp.model.DatabaseManager;
import com.example.irishka.movieapp.model.Pojo.ConcreteMovie;
import com.example.irishka.movieapp.model.Pojo.MoviePage;
import com.example.irishka.movieapp.view.MoviesActivity;
import com.example.irishka.movieapp.view.MoviesView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MoviesPresenter extends MvpPresenter<MoviesView> {

    private ApiManager apiManager = ApiManager.getInstance();

    private DatabaseManager databaseManager;

    private Disposable disposable;

    private int page = 1;

    public MoviesPresenter() {
        databaseManager = DatabaseManager.getInstance();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initDatabase();
        onDownloadMovies();
    }

    private void onSaveMovies(List<ConcreteMovie> movies) {
        databaseManager.getAppDatabase().getMovieDao().insertAll(movies);
    }

    private Single<List<ConcreteMovie>> getMoviesFromInternet() {

        Single<List<ConcreteMovie>> singleConcreteMovie = apiManager.getMoviesApi()
                .getMovies(page)
                .map(MoviePage::getResults)
                .doOnSuccess(this::onSaveMovies);

        page++;

        return singleConcreteMovie;
    }

    private Single<List<ConcreteMovie>> getMoviesFromDatabase() {

        return databaseManager.getAppDatabase().getMovieDao().getAllMovies()
                .subscribeOn(Schedulers.io());
    }

    public void onDownloadMovies() {

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = getMoviesFromInternet()
                .onErrorResumeNext(getMoviesFromDatabase())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> getViewState().showMovies(movies), Throwable::printStackTrace);
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
