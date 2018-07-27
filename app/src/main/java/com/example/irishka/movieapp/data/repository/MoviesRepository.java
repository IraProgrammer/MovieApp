package com.example.irishka.movieapp.data.repository;

import android.util.Pair;

import com.example.irishka.movieapp.data.database.dao.CastDao;
import com.example.irishka.movieapp.data.database.dao.CastOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfMovieDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.database.entity.GenreDb;
import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;
import com.example.irishka.movieapp.data.mappers.BackdropMapper;
import com.example.irishka.movieapp.data.mappers.CastMapper;
import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.mappers.ProductionCountryMapper;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.MoviePageModel;
import com.example.irishka.movieapp.data.network.MoviesApi;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepository implements IMoviesRepository {

    private MoviesMapper moviesMapper;

    private CastMapper castMapper;

    private BackdropMapper backdropMapper;

    private GenreMapper genreMapper;

    private ProductionCountryMapper countryMapper;

    private MovieDao movieDao;

    private CastDao castDao;

    private GenreDao genreDao;

    private GenreOfMovieDao genreOfMovieDao;

    private CastOfMovieDao castOfMovieDao;

    private MoviesApi moviesApi;

    @Inject
    public MoviesRepository(MoviesMapper moviesMapper, CastMapper castMapper, BackdropMapper backdropMapper,
                            GenreMapper genreMapper, ProductionCountryMapper countryMapper,
                            MovieDao movieDao, CastDao castDao, GenreDao genreDao,
                            GenreOfMovieDao genreOfMovieDao, CastOfMovieDao castOfMovieDao,
                            MoviesApi moviesApi) {
        this.moviesMapper = moviesMapper;
        this.castMapper = castMapper;
        this.backdropMapper = backdropMapper;
        this.genreMapper = genreMapper;
        this.countryMapper = countryMapper;
        this.movieDao = movieDao;
        this.castDao = castDao;
        this.genreDao = genreDao;
        this.genreOfMovieDao = genreOfMovieDao;
        this.castOfMovieDao = castOfMovieDao;
        this.moviesApi = moviesApi;
    }

    private Single<List<Movie>> getMoviesFromInternet(int page) {
        return moviesApi
                .getMovies(page)
                .map(MoviePageModel::getResults)
                //   .toObservable()
                //   .flatMapIterable(list -> list)
                //   .flatMapSingle(movieModel -> getDescriptionFromInternet(movieModel.getId())
                //   .flatMap(descriptionModel -> getBackdropsFromInternet(movieModel.getId())
                //           .map(backdrops -> moviesMapper.apply(descriptionModel, backdrops))))
                //   .toList()
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> movieDao.insertAll(moviesMapper.mapMoviesListToDb(movies)));
    }

    private Single<List<Genre>> getGenres(long movieId) {
        return genreOfMovieDao.getGenresOfMovie(movieId)
                .toObservable()
                .flatMapIterable(list -> list)
                .map(genreOfMovie -> genreOfMovie.getGenreId())
                .flatMapSingle(id -> genreDao.getGenre(id))
                .toList()
                .map(list -> genreMapper.mapGenresListFromDb(list))
                .subscribeOn(Schedulers.io());
    }

    private Single<List<Movie>> getMoviesFromDatabase() {
        return movieDao.getAllMovies()
                //    .toObservable()
                //    .flatMapIterable(list -> list)
                //    .flatMapSingle(movieDb -> getGenres(movieDb.getId())
                //            .map(genres -> moviesMapper.applyFromDb(movieDb, genres)))
                //    .toList()
                //    .map(pairs -> moviesMapper.mapMoviesListFromDb(pairs))
                .map(moviesDb -> moviesMapper.mapMoviesListFromDb(moviesDb))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadMovies(int page) {
        return getMoviesFromInternet(page)
                .onErrorResumeNext(throwable -> {
                    throwable.printStackTrace();
                    return getMoviesFromDatabase();
                });
    }

    private Single<DescriptionModel> getDescriptionFromInternet(long movieId) {
        return moviesApi
                .getDescription(movieId)
                .doOnSuccess(descriptionModel -> insertGenresOfMovie(descriptionModel));
    }

    @Override
    public Single<List<Movie>> downloadRelatedMovies(long movieId) {
        return moviesApi
                .getRelated(movieId)
                .map(MoviePageModel::getResults)
                .toObservable()
                .flatMapIterable(list -> list)
                .flatMapSingle(movieModel -> getDescriptionFromInternet(movieModel.getId())
                        .flatMap(descriptionModel -> getBackdropsFromInternet(movieModel.getId())
                                .map(backdrops -> moviesMapper.apply(descriptionModel, backdrops))))
                .toList()
                .map(movies -> moviesMapper.mapMoviesListToDb(movies))
                .doOnSuccess(list -> movieDao.insertAll(list))
                .toObservable()
                .flatMapIterable(list -> list)
                .flatMapSingle(movieDb -> getGenres(movieDb.getId())
                        .map(genres -> moviesMapper.applyFromDb(movieDb, genres)))
                .toList();
    }

    private Single<List<Cast>> getCastsFromInternet(long movieId) {
        return moviesApi
                .getCreators(movieId)
                .map(CreditsModel::getCast)
                .doOnSuccess(castModels -> castDao.insertAll(castMapper.mapCastsListToDb(castModels)))
                .doOnSuccess(castModels -> insertCastsOfMovie(movieId, castModels))
                .map(castModels -> castMapper.mapCastsList(castModels));

    }

    private Single<List<Cast>> getCastsFromDatabase(long movieId) {
        return castOfMovieDao.getCastsOfMovie(movieId)
                .toObservable()
                .flatMapIterable(list -> list)
                .map(castOfMovie -> castOfMovie.getCastId())
                .flatMapSingle(id -> castDao.getCast(id))
                .toList()
                .map(list -> castMapper.mapCastsListFromDb(list))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Cast>> downloadCasts(long movieId) {
        return getCastsFromInternet(movieId)
                .onErrorResumeNext(getCastsFromDatabase(movieId));
    }

    private Single<List<BackdropModel>> getBackdropsFromInternet(long movieId) {
        return moviesApi
                .getGallery(movieId)
                .map(GalleryModel::getBackdrops);
    }

    private void insertGenresOfMovie(DescriptionModel description) {

        List<GenreOfMovie> genreOfDescriptons = new ArrayList<>();

        List<GenreDb> genres = genreMapper.mapGenresListToDb(description.getGenres());

        for (int i = 0; i < description.getGenres().size(); i++) {

            genreOfDescriptons.add(new GenreOfMovie(description.getId(), genres.get(i).getId()));

        }

        genreDao.insert(genres);
        genreOfMovieDao.insert(genreOfDescriptons);
    }

    private void insertCastsOfMovie(long movieId, List<CastModel> castModels) {

        List<CastOfMovie> castOfMovies = new ArrayList<>();

        List<CastDb> casts = castMapper.mapCastsListToDb(castModels);

        for (int i = 0; i < castModels.size(); i++) {

            castOfMovies.add(new CastOfMovie(movieId, casts.get(i).getCastId()));

        }

        castOfMovieDao.insert(castOfMovies);
    }

    private Single<Movie> getMovieFromInternet(long movieId) {
        return moviesApi
                .getDescription(movieId)
                .flatMap(descriptionModel -> getBackdropsFromInternet(descriptionModel.getId())
                        .map(backdrops -> moviesMapper.apply(descriptionModel, backdrops)))
                .doOnSuccess(movie -> movieDao.insert(moviesMapper.applyToDb(movie)));
    }

    private Single<Movie> getMovieFromDatabase(long movieId) {
        return movieDao.getMovie(movieId)
                .flatMap(movieDb -> getGenres(movieId)
                        .map(list -> new Pair<>(movieDb, list)))
                .map(pair -> moviesMapper.applyFromDb(pair.first, pair.second))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Movie> downloadMovie(long movieId) {
        return getMovieFromInternet(movieId)
                .onErrorResumeNext(getMovieFromDatabase(movieId));
    }


}
