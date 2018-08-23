package com.example.irishka.movieapp.data.repositories;

import android.util.Pair;

import com.example.irishka.movieapp.data.database.CastsDbSource;
import com.example.irishka.movieapp.data.database.GenresDbSource;
import com.example.irishka.movieapp.data.database.MoviesDbSource;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.mappers.CastMapper;
import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.network.CastsNetworkSource;
import com.example.irishka.movieapp.data.network.MoviesNetworkSource;
import com.example.irishka.movieapp.domain.Tabs;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepository implements IMoviesRepository {

    private MoviesMapper moviesMapper;

    private GenreMapper genreMapper;

    private MoviesDbSource moviesDbSource;

    private GenresDbSource genresDbSource;

    private MoviesNetworkSource moviesNetworkSource;

    @Inject
    public MoviesRepository(MoviesMapper moviesMapper, GenreMapper genreMapper,
                            MoviesNetworkSource moviesNetworkSource, MoviesDbSource dbSource,
                            GenresDbSource genresDbSource) {
        this.moviesMapper = moviesMapper;
        this.genreMapper = genreMapper;
        this.moviesNetworkSource = moviesNetworkSource;
        this.moviesDbSource = dbSource;
        this.genresDbSource = genresDbSource;
    }

    private Single<List<Genre>> getGenres(long movieId) {
        return genresDbSource.getGenresOfMovie(movieId)
                .flattenAsObservable(list -> list)
                .map(genreOfMovie -> genreOfMovie.getGenreId())
                .flatMapSingle(id -> genresDbSource.getGenre(id))
                .toList()
                .map(list -> genreMapper.mapGenresListFromDb(list))
                .subscribeOn(Schedulers.io());
    }

    private Single<List<Movie>> getRelatedFromInternet(long movieId, int page) {
        return moviesNetworkSource
                .getRelated(movieId, page)
                .doOnSuccess(movieModels -> moviesDbSource.insertAllRoM(moviesMapper.createRoMList(movieId, movieModels)))
                .map(movies -> moviesMapper.mapMovies(movies))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));
    }

    private Single<List<Movie>> getRelatedFromDatabase(long movieId) {
        return moviesDbSource.getRelatedOfMovie(movieId)
                .flattenAsObservable(list -> list)
                .flatMapSingle(relatedOfMovie -> getMovieFromDatabase(relatedOfMovie.getRelatedId()))
                .toList()
                //  .map(movies -> moviesMapper.mapMoviesListFromDb(moviesDb))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadRelatedMovies(long movieId, int page) {
        return getRelatedFromInternet(movieId, page)
                .onErrorResumeNext(throwable -> {
                    throwable.printStackTrace();
                    return getRelatedFromDatabase(movieId);
                });
    }

    private Single<Movie> getMovieFromInternet(long movieId) {
        return moviesNetworkSource
                .getDescription(movieId)
                .doOnSuccess(descriptionModel -> genresDbSource.insertAllGenres(genreMapper.mapGenresListToDb(descriptionModel.getGenres())))
                .doOnSuccess(descriptionModel -> genresDbSource.insertAllGoM(genreMapper.createGoMList(descriptionModel)))
                .flatMap(descriptionModel -> moviesNetworkSource
                        .getBackdrops(descriptionModel.getId())
                        .flatMap(backdrops -> moviesNetworkSource
                                .getTrailers(descriptionModel.getId())
                                .map(trailers -> moviesMapper.apply(descriptionModel, backdrops, trailers))))
                .doOnSuccess(movie -> moviesDbSource.insertMovie(moviesMapper.applyToDb(movie)));
    }

    //TODO
    @Override
    public Single<Movie> getMovieFromDatabase(long movieId) {
        return moviesDbSource.getMovie(movieId)
                .flatMap(movieDb -> getGenres(movieId)
                        .map(genres -> moviesMapper.applyFromDb(movieDb, genres)))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Movie> downloadMovie(long movieId) {
        return getMovieFromInternet(movieId)
                .onErrorResumeNext(getMovieFromDatabase(movieId));
    }

    private Single<List<Movie>> getNowPlayingFromInternet(int page) {
        return moviesNetworkSource
                .getNowPlaying(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(Tabs.NOW_PLAYING.getTitle(), movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

    private Single<List<Movie>> getPopularFromInternet(int page) {
        return moviesNetworkSource
                .getPopular(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(Tabs.POPULAR.getTitle(), movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

    private Single<List<Movie>> getTopRatedFromInternet(int page) {
        return moviesNetworkSource
                .getTopRated(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(Tabs.TOP_RATED.getTitle(), movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

    private Single<List<Movie>> getUpcomingFromInternet(int page) {
        return moviesNetworkSource
                .getUpcoming(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(Tabs.UPCOMING.getTitle(), movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

    private Single<List<Movie>> getMainScreenFromDatabase(String type) {
        return moviesDbSource.getMovieWithCategory(type)
                .flattenAsObservable(list -> list)
                .flatMapSingle(movieWithCategory -> getMovieFromDatabase(movieWithCategory.getMovieId()))
                .toList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadMovies(int page, String type) {

        if (type.equals(Tabs.NOW_PLAYING.getTitle())) {
            return getNowPlayingFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type));
        } else if (type.equals(Tabs.POPULAR.getTitle())) {
            return getPopularFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type));
        } else if (type.equals(Tabs.TOP_RATED.getTitle())) {
            return getTopRatedFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type));
        } else if (type.equals(Tabs.UPCOMING.getTitle())) {
            return getUpcomingFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type));
        } else return null;
    }

    @Override
    public Single<List<Movie>> getWithFilters(int page, String sort, String genres) {
        return moviesNetworkSource
                .getWithFilters(page, sort, genres)
                .map(movieModels -> moviesMapper.mapMovies(movieModels));

    }

    @Override
    public Single<List<Movie>> getMoviesFromSearchFromInternet(String query, int page) {
        return moviesNetworkSource.getMoviesFromSearch(query, page)
                .map(movieModels -> moviesMapper.mapMovies(movieModels));
    }

    @Override
    public Single<List<Movie>> getActorFilmsFromInternet(long id) {
        return moviesNetworkSource
                .getActorFilms(id)
                .map(movies -> moviesMapper.mapMoviesWithSort(movies));
    }

    @Override
    public void insertAllMovies(List<Movie> movies) {
        moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies));
    }
}