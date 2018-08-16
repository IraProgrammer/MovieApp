package com.example.irishka.movieapp.data.repository;

import android.util.Pair;

import com.example.irishka.movieapp.data.database.MoviesDbSource;
import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.data.database.entity.MovieDb;
import com.example.irishka.movieapp.data.database.entity.MovieWithCategory;
import com.example.irishka.movieapp.data.mappers.CastMapper;
import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.KeywordsMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.TrailerModel;
import com.example.irishka.movieapp.data.network.MoviesNetworkSource;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.Keyword;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.NOW_PLAYING;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.POPULAR;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.TOP_RATED;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.UPCOMING;

public class MoviesRepository implements IMoviesRepository {

    private MoviesMapper moviesMapper;

    private CastMapper castMapper;

    private GenreMapper genreMapper;

    private KeywordsMapper keywordsMapper;

    private MoviesDbSource dbSource;

    private MoviesNetworkSource networkSource;

    @Inject
    public MoviesRepository(MoviesMapper moviesMapper, CastMapper castMapper, GenreMapper genreMapper, KeywordsMapper keywordsMapper,
                            MoviesNetworkSource networkSource, MoviesDbSource dbSource) {
        this.moviesMapper = moviesMapper;
        this.castMapper = castMapper;
        this.genreMapper = genreMapper;
        this.keywordsMapper = keywordsMapper;
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
                .doOnSuccess(movieModels -> dbSource.insertAllRoM(moviesMapper.createRoMList(movieId, movieModels)))
                .map(movies -> moviesMapper.mapMovies(movies))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));
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
                .doOnSuccess(castModels -> dbSource.insertAllCoM(castMapper.createCoMList(movieId, castModels)));

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

    private Single<Movie> getMovieFromInternet(long movieId) {
        return networkSource
                .getDescription(movieId)
                .doOnSuccess(descriptionModel -> dbSource.insertAllGenres(genreMapper.mapGenresListToDb(descriptionModel.getGenres())))
                .doOnSuccess(descriptionModel -> dbSource.insertAllGoM(genreMapper.createGoMList(descriptionModel)))
                .flatMap(descriptionModel -> networkSource
                        .getBackdrops(descriptionModel.getId())
                        .flatMap(backdrops -> networkSource
                                .getTrailers(descriptionModel.getId())
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
                .map(movies -> moviesMapper.mapMoviesWithSort(movies))
                .doOnSuccess(movieModels -> dbSource.insertAllCoM(castMapper.createMoCList(id, movieModels)))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));
    }

    private Single<List<Movie>> getActorFilmsFromDatabase(long id) {
        return dbSource.getMoviesOfCast(id)
                .flattenAsObservable(list -> list)
                .flatMapSingle(castOfMovie -> getMovieFromDatabase(castOfMovie.getMovieId()))
                .toList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadActorFilms(long id) {
        return getActorFilmsFromInternet(id)
                .onErrorResumeNext(getActorFilmsFromDatabase(id));
    }

    @Override
    public Single<List<Movie>> getMoviesFromSearchFromInternet(String query, int page) {
        return networkSource.getMoviesFromSearch(query, page)
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> dbSource.insertKeyword(new KeywordDb(query)));
    }

    @Override
    public Single<List<String>> getKeywordsFromInternet(String query) {
        return networkSource.getKeywords(query)
                .map(keywordModels -> keywordsMapper.mapKeywordList(keywordModels));
    }

    @Override
    public Single<List<String>> getKeywordsFromDb() {
        return dbSource.getKeywords()
                .map(keywordsDb -> keywordsMapper.mapKeywordListFromDb(keywordsDb))
                .subscribeOn(Schedulers.io());
    }

    private Single<List<Movie>> getNowPlayingFromInternet(int page) {
        return networkSource
                .getNowPlaying(page)
                .doOnSuccess(movies -> dbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(NOW_PLAYING, movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

//    private Single<List<Movie>> getNowPlayingFromDatabase() {
//        return dbSource.getMovieWithCategory(NOW_PLAYING)
//                .flattenAsObservable(list -> list)
//                .flatMapSingle(movieWithCategory -> getMovieFromDatabase(movieWithCategory.getMovieId()))
//                .toList()
//                .subscribeOn(Schedulers.io());
//    }

    private Single<List<Movie>> getPopularFromInternet(int page) {
        return networkSource
                .getPopular(page)
                .doOnSuccess(movies -> dbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(POPULAR, movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

//    private Single<List<Movie>> getPopularFromDatabase() {
//        return dbSource.getMovieWithCategory(POPULAR)
//                .flattenAsObservable(list -> list)
//                .flatMapSingle(movieWithCategory -> getMovieFromDatabase(movieWithCategory.getMovieId()))
//                .toList()
//                .subscribeOn(Schedulers.io());
//    }

    private Single<List<Movie>> getTopRatedFromInternet(int page) {
        return networkSource
                .getTopRated(page)
                .doOnSuccess(movies -> dbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(TOP_RATED, movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

//    private Single<List<Movie>> getTopRatedFromDatabase() {
//        return dbSource.getMovieWithCategory(TOP_RATED)
//                .flattenAsObservable(list -> list)
//                .flatMapSingle(movieWithCategory -> getMovieFromDatabase(movieWithCategory.getMovieId()))
//                .toList()
//                .subscribeOn(Schedulers.io());
//    }

    private Single<List<Movie>> getUpcomingFromInternet(int page) {
        return networkSource
                .getUpcoming(page)
                .doOnSuccess(movies -> dbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(UPCOMING, movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> dbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

    private Single<List<Movie>> getMainScreenFromDatabase(String type) {
        return dbSource.getMovieWithCategory(type)
                .flattenAsObservable(list -> list)
                .flatMapSingle(movieWithCategory -> getMovieFromDatabase(movieWithCategory.getMovieId()))
                .toList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadMoviesForMainScreen(int page, String type) {

        if (type.equals(NOW_PLAYING)) {
            return getNowPlayingFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type));
        } else if (type.equals(POPULAR)) {
            return getPopularFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type));
        } else if (type.equals(TOP_RATED)) {
            return getTopRatedFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type));
        } else if (type.equals(UPCOMING)) {
            return getUpcomingFromInternet(page)
                    .onErrorResumeNext(getMainScreenFromDatabase(type));
        } else return null;
    }
}
