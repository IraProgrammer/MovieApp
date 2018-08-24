package com.example.irishka.movieapp.data.repositories;

import com.example.irishka.movieapp.data.database.GenresDbSource;
import com.example.irishka.movieapp.data.database.MoviesDbSource;
import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.network.MoviesNetworkSource;
import com.example.irishka.movieapp.domain.MainType;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.MovieWithError;
import com.example.irishka.movieapp.domain.entity.MoviesListWithError;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
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
                .map(MovieWithError::getMovie)
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

    @Override
    public Single<MoviesListWithError> downloadMovies(int page, String type) {
        return null;
    }

    private Single<MovieWithError> getMovieFromInternet(long movieId) {
        return moviesNetworkSource
                .getDescription(movieId)
                .doOnSuccess(descriptionModel -> genresDbSource.insertAllGenres(genreMapper.mapGenresListToDb(descriptionModel.getGenres())))
                .doOnSuccess(descriptionModel -> genresDbSource.insertAllGoM(genreMapper.createGoMList(descriptionModel)))
                .flatMap(descriptionModel -> moviesNetworkSource
                        .getBackdrops(descriptionModel.getId())
                        .flatMap(backdrops -> moviesNetworkSource
                                .getTrailers(descriptionModel.getId())
                                .map(trailers -> moviesMapper.apply(descriptionModel, backdrops, trailers))))
                .doOnSuccess(movie -> moviesDbSource.insertMovie(moviesMapper.applyToDb(movie)))
                .map(movie -> new MovieWithError(movie, false));
    }

    @Override
    public Single<MovieWithError> getMovieFromDatabase(long movieId) {
        return moviesDbSource.getMovie(movieId)
                .flatMap(movieDb -> getGenres(movieId)
                        .map(genres -> moviesMapper.applyFromDb(movieDb, genres)))
                .map(movie -> new MovieWithError(movie, true))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<MovieWithError> downloadMovie(long movieId) {
        return getMovieFromInternet(movieId)
                .onErrorResumeNext(getMovieFromDatabase(movieId));
    }

    private Single<MoviesListWithError> getNowPlayingFromInternet(int page) {
        return moviesNetworkSource
                .getNowPlaying(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(MainType.NOW_PLAYING, movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)))
                .map(movies -> new MoviesListWithError(movies, false));

    }

    private Single<MoviesListWithError> getPopularFromInternet(int page) {
        return moviesNetworkSource
                .getPopular(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(MainType.POPULAR, movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)))
                .map(movies -> new MoviesListWithError(movies, false));

    }

    private Single<MoviesListWithError> getTopRatedFromInternet(int page) {
        return moviesNetworkSource
                .getTopRated(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(MainType.TOP_RATED, movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)))
                .map(movies -> new MoviesListWithError(movies, false));

    }

    private Single<MoviesListWithError> getUpcomingFromInternet(int page) {
        return moviesNetworkSource
                .getUpcoming(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(MainType.UPCOMING, movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)))
                .map(movies -> new MoviesListWithError(movies, false));

    }

    private Single<MoviesListWithError> getMainScreenFromDatabase(MainType type, int page) {
        return moviesDbSource.getMovieWithCategory(type)
                .flattenAsObservable(list -> list)
                .flatMapSingle(movieWithCategory -> getMovieFromDatabase(movieWithCategory.getMovieId()))
                .map(MovieWithError::getMovie)
                .toList()
                .map(movies -> {
                    if (page > 1) {
                        return new MoviesListWithError(new ArrayList<Movie>(), true);
                    }
                    return new MoviesListWithError(movies, true);
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<MoviesListWithError> downloadMovies(int page, MainType type) {

        if (type.equals(MainType.NOW_PLAYING)) {
            return getNowPlayingFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type, page));
        } else if (type.equals(MainType.POPULAR)) {
            return getPopularFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type, page));
        } else if (type.equals(MainType.TOP_RATED)) {
            return getTopRatedFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type, page));
        } else if (type.equals(MainType.UPCOMING)) {
            return getUpcomingFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type, page));
        } else return null;
    }

    @Override
    public Single<MoviesListWithError> getWithFilters(int page, String sort, String genres) {
        return moviesNetworkSource
                .getWithFilters(page, sort, genres)
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .map(movies -> new MoviesListWithError(movies, false))
                .onErrorResumeNext(throwable -> new Single<MoviesListWithError>() {
                    @Override
                    protected void subscribeActual(SingleObserver<? super MoviesListWithError> observer) {
                        observer.onSuccess(new MoviesListWithError(new ArrayList<Movie>(), true));
                    }
                });

    }

    @Override
    public Single<MoviesListWithError> getMoviesFromSearchFromInternet(String query, int page) {
        return moviesNetworkSource.getMoviesFromSearch(query, page)
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .map(movies -> new MoviesListWithError(movies, false))
                .onErrorResumeNext(throwable -> new Single<MoviesListWithError>() {
                    @Override
                    protected void subscribeActual(SingleObserver<? super MoviesListWithError> observer) {
                        observer.onSuccess(new MoviesListWithError(new ArrayList<Movie>(), true));
                    }
                });
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
