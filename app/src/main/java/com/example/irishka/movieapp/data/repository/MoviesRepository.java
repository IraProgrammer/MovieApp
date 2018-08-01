package com.example.irishka.movieapp.data.repository;

import android.util.Pair;

import com.example.irishka.movieapp.BuildConfig;
import com.example.irishka.movieapp.data.database.MoviesDbSource;
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
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.GenreModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.MoviePageModel;
import com.example.irishka.movieapp.data.network.MoviesApi;
import com.example.irishka.movieapp.data.network.MoviesNetworkSource;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepository implements IMoviesRepository {

    private MoviesMapper moviesMapper;

    private CastMapper castMapper;

    private GenreMapper genreMapper;

    private MoviesDbSource dbSource;

    private MoviesNetworkSource networkSource;

    @Inject
    public MoviesRepository(MoviesMapper moviesMapper, CastMapper castMapper, GenreMapper genreMapper,
                            MoviesNetworkSource networkSource, MoviesDbSource dbSource) {
        this.moviesMapper = moviesMapper;
        this.castMapper = castMapper;
        this.genreMapper = genreMapper;
        this.networkSource = networkSource;
        this.dbSource = dbSource;
    }

    private Single<List<Movie>> getMoviesFromInternet(int page) {
        return networkSource
                .getMovies(page)
                .map(MoviePageModel::getResults)
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));
    }

    private Single<List<Genre>> getGenres(long movieId) {
        return dbSource.getGenresOfMovie(movieId)
                .flattenAsObservable(list -> list)
                .map(genreOfMovie -> genreOfMovie.getGenreId())
                .flatMapSingle(id -> dbSource.getGenre(id))
                .toList()
                .map(list -> genreMapper.mapGenresListFromDb(list))
                .subscribeOn(Schedulers.io());
    }

    private Single<List<Movie>> getMoviesFromDatabase() {
        return dbSource.getAllMovies()
                .map(moviesDb -> moviesMapper.mapMoviesListFromDb(moviesDb))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadMovies(int page) {
        return getMoviesFromInternet(page)
                .onErrorResumeNext(throwable -> {
                    // на будущее - логи можно выводить только в дев билде, можно ставить проверку на BuildConfig.DEBUG
                    // но такие проверки везде будут нагромождать код, поэтому логи можно вынести в одно место
                    // здесь не надо ничего править
                    throwable.printStackTrace();
                    return getMoviesFromDatabase();
                });
    }

    private Single<List<Movie>> getRelatedFromInternet(long movieId) {
        return networkSource
                .getRelated(movieId)
                .map(MoviePageModel::getResults)
                .map(movies -> moviesMapper.mapMovies(movies))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies, movieId)));
    }

    private Single<List<Movie>> getRelatedFromDatabase(long movieId) {
        return dbSource.getRelatedMovies(movieId)
                .map(moviesDb -> moviesMapper.mapMoviesListFromDb(moviesDb))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadRelatedMovies(long movieId) {
        return getRelatedFromInternet(movieId)
                .onErrorResumeNext(throwable -> {
                    throwable.printStackTrace();
                    return getRelatedFromDatabase(movieId);
                });
    }

    private Single<List<Cast>> getCastsFromInternet(long movieId) {
        return networkSource
                .getCreators(movieId)
                .map(CreditsModel::getCast)
                .doOnSuccess(castModels -> dbSource.insertAllCasts(castMapper.mapCastsListToDb(castModels)))
                .doOnSuccess(castModels -> insertCastsOfMovie(movieId, castModels))
                .map(castModels -> castMapper.mapCastsList(castModels));

    }

    private Single<List<Cast>> getCastsFromDatabase(long movieId) {
        return dbSource.getCastsOfMovie(movieId)
                .flattenAsObservable(list -> list)
                .map(castOfMovie -> castOfMovie.getCastId())
                .flatMapSingle(id -> dbSource.getCast(id))
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
        return networkSource
                .getGallery(movieId)
                .map(GalleryModel::getBackdrops);
    }

    private void insertGenresOfMovie(DescriptionModel description) {

        dbSource.insertAllGoM(description.getId(), genreMapper.createGoMList(description));

    }

    private void insertCastsOfMovie(long movieId, List<CastModel> castModels) {

        List<CastOfMovie> castOfMovies = new ArrayList<>();

        List<CastDb> casts = castMapper.mapCastsListToDb(castModels);

        for (int i = 0; i < castModels.size(); i++) {

            castOfMovies.add(new CastOfMovie(movieId, casts.get(i).getCastId()));

        }

        dbSource.insertAllCoM(castOfMovies);
    }

    private Single<Movie> getMovieFromInternet(long movieId) {
        return networkSource
                .getDescription(movieId)
                .doOnSuccess(descriptionModel -> dbSource.insertAllGenres(genreMapper.mapGenresListToDb(descriptionModel.getGenres())))
                .doOnSuccess(descriptionModel -> insertGenresOfMovie(descriptionModel))
                .flatMap(descriptionModel -> getBackdropsFromInternet(descriptionModel.getId())
                        .map(backdrops -> moviesMapper.apply(descriptionModel, backdrops)))
                .doOnSuccess(movie -> dbSource.insertMovie(moviesMapper.applyToDb(movie)));
    }

    private Single<Movie> getMovieFromDatabase(long movieId) {
        return dbSource.getMovie(movieId)
                .flatMap(movieDb -> getGenres(movieId)
                        .map(genres -> new Pair<>(movieDb, genres)))
                .map(pair -> moviesMapper.applyFromDb(pair.first, pair.second))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Movie> downloadMovie(long movieId) {
        return getMovieFromInternet(movieId)
                .onErrorResumeNext(getMovieFromDatabase(movieId));
    }

    @Override
    public Single<ActorPhotosModel> getActorPhotoModel(long castId) {
        return networkSource
                .getActorPhotos(castId);
    }
}
