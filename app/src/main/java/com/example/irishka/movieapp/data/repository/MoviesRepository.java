package com.example.irishka.movieapp.data.repository;

import com.example.irishka.movieapp.data.MovieModel;
import com.example.irishka.movieapp.data.mapper.MoviesMapper;
import com.example.irishka.movieapp.data.network.ApiManager;
import com.example.irishka.movieapp.data.database.DatabaseManager;
import com.example.irishka.movieapp.data.MoviePage;
import com.example.irishka.movieapp.domain.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepository implements IMoviesRepository {

    private MoviesMapper moviesMapper = new MoviesMapper();

    private int page = 1;

    public MoviesRepository(){
    }

    private void onSaveMovies(List<MovieModel> movies) {
        DatabaseManager.getInstance().getAppDatabase().getMovieDao().insertAll(movies);
    }

    private Single<List<Movie>> getMoviesFromInternet() {

        return ApiManager.getInstance().getMoviesApi()
                .getMovies(page)
                .map(MoviePage::getResults)
                .doOnSuccess(this::onSaveMovies)
                .doOnSuccess(movies -> page++)
                .map(movies -> moviesMapper.getMoviesList(movies));
    }

    private Single<List<Movie>> getMoviesFromDatabase() {

        return DatabaseManager.getInstance().getAppDatabase().getMovieDao().getAllMovies()
                .map(movies -> moviesMapper.getMoviesList(movies))
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Movie>> downloadMovies() {

        return getMoviesFromInternet()
                .onErrorResumeNext(getMoviesFromDatabase())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
