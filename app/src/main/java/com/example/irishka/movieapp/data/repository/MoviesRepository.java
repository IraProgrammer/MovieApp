package com.example.irishka.movieapp.data.repository;

import android.util.Pair;

import com.example.irishka.movieapp.data.database.MoviesDbSource;
import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.mappers.CastMapper;
import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.FilmsModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.TrailerModel;
import com.example.irishka.movieapp.data.network.MoviesNetworkSource;
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

    private Single<List<Movie>> getRelatedFromInternet(long movieId, int page) {
        return networkSource
                .getRelated(movieId, page)
                .doOnSuccess(movieModels -> insertRelatedOfMovie(movieId, movieModels))
                .map(movies -> moviesMapper.mapMovies(movies))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies, movieId)));
    }

    private Single<List<Movie>> getRelatedFromDatabase(long movieId) {
        return dbSource.getRelatedOfMovie(movieId)
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

    private Single<List<Cast>> getCastsFromInternet(long movieId) {
        return networkSource
                .getCasts(movieId)
                .map(castModels -> castMapper.mapCastsList(castModels))
                .doOnSuccess(casts -> dbSource.insertAllCasts(castMapper.mapCastsListToDb(casts)))
                .doOnSuccess(castModels -> insertCastsOfMovie(movieId, castModels));

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

    private Single<Cast> getMoreAboutActorFromInternet(long id) {
        return networkSource
                .getActorInfo(id)
                .flatMap(actorInfoModel -> networkSource.getActorPhotos(id)
                .map(actorPhotosModel -> castMapper.apply(actorInfoModel, actorPhotosModel)))
                .doOnSuccess(cast -> dbSource.insertCast(castMapper.applyToDb(cast)));
    }

    private Single<Cast> getConcreteCastFromDatabase(long id) {
        return dbSource.getCast(id)
                .map(castDb -> castMapper.applyFromDb(castDb))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Cast>> downloadCasts(long movieId) {
        return getCastsFromInternet(movieId)
                .onErrorResumeNext(getCastsFromDatabase(movieId));
    }

    @Override
    public Single<Cast> downloadConcreteCast(long id) {
        return getMoreAboutActorFromInternet(id)
                .onErrorResumeNext(getConcreteCastFromDatabase(id));
    }

    private Single<List<BackdropModel>> getBackdropsFromInternet(long movieId) {
        return networkSource
                .getBackdrops(movieId);
    }

    private void insertGenresOfMovie(DescriptionModel description) {

        dbSource.insertAllGoM(genreMapper.createGoMList(description));

    }

    private void insertRelatedOfMovie(long movieId, List<MovieModel> relatedMovies) {

        dbSource.insertAllRoM(moviesMapper.createRoMList(movieId, relatedMovies));

    }

    private void insertCastsOfMovie(long movieId, List<Cast> casts) {

        dbSource.insertAllCoM(castMapper.createCoMList(movieId, casts));
    }

    private Single<Movie> getMovieFromInternet(long movieId) {
        return networkSource
                .getDescription(movieId)
                .doOnSuccess(descriptionModel -> dbSource.insertAllGenres(genreMapper.mapGenresListToDb(descriptionModel.getGenres())))
                .doOnSuccess(descriptionModel -> insertGenresOfMovie(descriptionModel))
                .flatMap(descriptionModel -> getBackdropsFromInternet(descriptionModel.getId())
                        .flatMap(backdrops -> getTrailers(descriptionModel.getId())
                                .map(trailers -> moviesMapper.apply(descriptionModel, backdrops, trailers))))
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

    private Single<List<Movie>> getActorFilmsFromInternet(long id) {
        return networkSource
                .getActorFilms(id)
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));
    }

//    private Single<List<Movie>> getActorFilmsFromDatabase(long id) {
//        return dbSource.getActorFilms(id)
//                .map(moviesDb -> moviesMapper.mapMoviesListFromDb(moviesDb))
//                .subscribeOn(Schedulers.io());
//    }

    @Override
    public Single<List<Movie>> downloadActorFilms(long id) {
        return getActorFilmsFromInternet(id);
             //   .onErrorResumeNext(getActorFilmsFromDatabase(id));
    }

    private Single<List<TrailerModel>> getTrailers(long movieId) {
        return networkSource
                .getTrailers(movieId);
    }
}
