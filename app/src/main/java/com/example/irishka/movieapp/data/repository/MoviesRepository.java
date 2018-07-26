package com.example.irishka.movieapp.data.repository;

import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.network.mappers.BackdropMapper;
import com.example.irishka.movieapp.data.network.mappers.CastMapper;
import com.example.irishka.movieapp.data.network.mappers.DescriptionMapper;
import com.example.irishka.movieapp.data.network.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.MoviePageModel;
import com.example.irishka.movieapp.data.network.MoviesApi;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Description;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepository implements IMoviesRepository {

    private MoviesMapper moviesMapper;

    private DescriptionMapper descriptionMapper;

    private CastMapper castMapper;

    private BackdropMapper backdropMapper;

    private MovieDao movieDao;

    private MoviesApi moviesApi;

  //  private int page = 1;

    @Inject
    public MoviesRepository(MoviesMapper moviesMapper, DescriptionMapper descriptionMapper,
                            CastMapper castMapper, BackdropMapper backdropMapper,
                            MovieDao movieDao, MoviesApi moviesApi) {
        this.moviesMapper = moviesMapper;
        this.descriptionMapper = descriptionMapper;
        this.castMapper = castMapper;
        this.backdropMapper = backdropMapper;
        this.movieDao = movieDao;
        this.moviesApi = moviesApi;
    }

    private Single<List<Movie>> getMoviesFromInternet(int page) {
        return moviesApi
                .getMovies(page)
                .map(MoviePageModel::getResults)
                .doOnSuccess(movies -> movieDao.insertAll(movies))
            //    .doOnSuccess(movies -> page++)
                .map(movies -> moviesMapper.mapMoviesList(movies));
    }

    private Single<List<Movie>> getMoviesFromDatabase() {
        return movieDao.getAllMovies()
                .map(movies -> moviesMapper.mapMoviesList(movies))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadMovies(int page) {
        return getMoviesFromInternet(page)
                .onErrorResumeNext(getMoviesFromDatabase());
    }

    @Override
    public Single<Description> downloadDescription(long movieId){
        return moviesApi
                .getDescription(movieId)
                .map(description -> descriptionMapper.apply(description));
    }

    @Override
    public Single<List<Movie>> downloadRelatedMovies(long movieId){
        return moviesApi
                .getRelated(movieId)
                .map(MoviePageModel::getResults)
                .map(movies -> moviesMapper.mapMoviesList(movies));
    }

    @Override
    public Single<List<Cast>> downloadCasts(long movieId){
        return moviesApi
                .getCreators(movieId)
                .map(CreditsModel::getCast)
                .map(cast -> castMapper.mapCastsList(cast));
    }

    @Override
    public Single<List<Backdrop>> downloadGallery(long movieId) {
        return moviesApi
                .getGallery(movieId)
                .map(GalleryModel::getBackdrops)
                .map(backdrops -> backdropMapper.mapBackdropsList(backdrops));
    }
}
