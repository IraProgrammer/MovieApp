package com.example.irishka.movieapp.data.repository;

import android.util.Pair;

import com.example.irishka.movieapp.data.database.CastsDbSource;
import com.example.irishka.movieapp.data.database.GenresDbSource;
import com.example.irishka.movieapp.data.database.KeywordsDbSource;
import com.example.irishka.movieapp.data.database.MoviesDbSource;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.data.mappers.CastMapper;
import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.KeywordsMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.network.CastsNetworkSource;
import com.example.irishka.movieapp.data.network.KeywordsNetworkSource;
import com.example.irishka.movieapp.data.network.MoviesNetworkSource;
import com.example.irishka.movieapp.domain.Tabs;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.*;

public class MoviesRepository implements IMoviesRepository {

    private MoviesMapper moviesMapper;

    private CastMapper castMapper;

    private GenreMapper genreMapper;

    private KeywordsMapper keywordsMapper;

    private MoviesDbSource moviesDbSource;

    private CastsDbSource castsDbSource;

    private GenresDbSource genresDbSource;

    private KeywordsDbSource keywordsDbSource;

    private MoviesNetworkSource networkSource;

    private KeywordsNetworkSource keywordsNetworkSource;

    private CastsNetworkSource castsNetworkSource;

    @Inject
    public MoviesRepository(MoviesMapper moviesMapper, CastMapper castMapper, GenreMapper genreMapper, KeywordsMapper keywordsMapper,
                            MoviesNetworkSource networkSource, MoviesDbSource dbSource,
                            CastsDbSource castsDbSource, GenresDbSource genresDbSource, KeywordsDbSource keywordsDbSource,
                            KeywordsNetworkSource keywordsNetworkSource, CastsNetworkSource castsNetworkSource) {
        this.moviesMapper = moviesMapper;
        this.castMapper = castMapper;
        this.genreMapper = genreMapper;
        this.keywordsMapper = keywordsMapper;
        this.networkSource = networkSource;
        this.moviesDbSource = dbSource;
        this.castsDbSource = castsDbSource;
        this.genresDbSource = genresDbSource;
        this.keywordsDbSource = keywordsDbSource;
        this.keywordsNetworkSource = keywordsNetworkSource;
        this.castsNetworkSource = castsNetworkSource;
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

    private Single<List<Movie>> getMoviesFromDatabase() {
        return moviesDbSource.getAllMovies()
                .map(moviesDb -> moviesMapper.mapMoviesListFromDb(moviesDb))
                .subscribeOn(Schedulers.io());
    }

    private Single<List<Movie>> getRelatedFromInternet(long movieId, int page) {
        return networkSource
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

    private Single<List<Cast>> getCastsFromInternet(long movieId) {
        return castsNetworkSource
                .getCasts(movieId)
                .map(castModels -> castMapper.mapCastsList(castModels))
                .doOnSuccess(casts -> castsDbSource.insertAllCasts(castMapper.mapCastsListToDb(casts)))
                .doOnSuccess(castModels -> castsDbSource.insertAllCoM(castMapper.createCoMList(movieId, castModels)));

    }

    private Single<List<Cast>> getCastsFromDatabase(long movieId) {
        return castsDbSource.getCastsOfMovie(movieId)
                .flattenAsObservable(list -> list)
                .map(CastOfMovie::getCastId)
                .flatMapSingle(id -> castsDbSource.getCast(id))
                .toList()
                .map(list -> castMapper.mapCastsListFromDb(list))
                .subscribeOn(Schedulers.io());
    }

    private Single<Cast> getMoreAboutActorFromInternet(long id) {
        return castsNetworkSource
                .getActorInfo(id)
                .flatMap(actorInfoModel -> castsNetworkSource.getActorPhotos(id)
                        .map(actorPhotosModel -> castMapper.apply(actorInfoModel, actorPhotosModel)))
                .doOnSuccess(cast -> castsDbSource.insertCast(castMapper.applyToDb(cast)));
    }

    private Single<Cast> getConcreteCastFromDatabase(long id) {
        return castsDbSource.getCast(id)
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
                .doOnSuccess(descriptionModel -> genresDbSource.insertAllGenres(genreMapper.mapGenresListToDb(descriptionModel.getGenres())))
                .doOnSuccess(descriptionModel -> genresDbSource.insertAllGoM(genreMapper.createGoMList(descriptionModel)))
                .flatMap(descriptionModel -> networkSource
                        .getBackdrops(descriptionModel.getId())
                        .flatMap(backdrops -> networkSource
                                .getTrailers(descriptionModel.getId())
                                .map(trailers -> moviesMapper.apply(descriptionModel, backdrops, trailers))))
                .doOnSuccess(movie -> moviesDbSource.insertMovie(moviesMapper.applyToDb(movie)));
    }

    private Single<Movie> getMovieFromDatabase(long movieId) {
        return moviesDbSource.getMovie(movieId)
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
        return castsNetworkSource
                .getActorFilms(id)
                .map(movies -> moviesMapper.mapMoviesWithSort(movies))
                .doOnSuccess(movieModels -> castsDbSource.insertAllCoM(castMapper.createMoCList(id, movieModels)))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));
    }

    private Single<List<Movie>> getActorFilmsFromDatabase(long id) {
        return castsDbSource.getMoviesOfCast(id)
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
                .doOnSuccess(movies -> keywordsDbSource.insertKeyword(new KeywordDb(query)));
    }

    @Override
    public Single<List<String>> getKeywordsFromInternet(String query) {
        return keywordsNetworkSource.getKeywords(query)
                .map(keywordModels -> keywordsMapper.mapKeywordList(keywordModels));
    }

    @Override
    public Single<List<String>> getKeywordsFromDb() {
        return keywordsDbSource.getKeywords()
                .map(keywordsDb -> keywordsMapper.mapKeywordListFromDb(keywordsDb))
                .subscribeOn(Schedulers.io());
    }

    private Single<List<Movie>> getNowPlayingFromInternet(int page) {
        return networkSource
                .getNowPlaying(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(Tabs.NOW_PLAYING.getTitle(), movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

    private Single<List<Movie>> getPopularFromInternet(int page) {
        return networkSource
                .getPopular(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(Tabs.POPULAR.getTitle(), movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

    private Single<List<Movie>> getTopRatedFromInternet(int page) {
        return networkSource
                .getTopRated(page)
                .doOnSuccess(movies -> moviesDbSource.insertMoviesWithCategory(moviesMapper.createMovieWithCategoryList(Tabs.TOP_RATED.getTitle(), movies)))
                .map(movieModels -> moviesMapper.mapMovies(movieModels))
                .doOnSuccess(movies -> moviesDbSource.insertAllMovies(moviesMapper.mapMoviesListToDb(movies)));

    }

    private Single<List<Movie>> getUpcomingFromInternet(int page) {
        return networkSource
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
        return networkSource
                .getWithFilters(page, sort, genres)
                .map(movieModels -> moviesMapper.mapMovies(movieModels));

    }

}
