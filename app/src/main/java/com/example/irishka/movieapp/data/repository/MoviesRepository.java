package com.example.irishka.movieapp.data.repository;

import android.util.Pair;

import com.example.irishka.movieapp.data.database.dao.BackdropDao;
import com.example.irishka.movieapp.data.database.dao.CastDao;
import com.example.irishka.movieapp.data.database.dao.DescriptionDao;
import com.example.irishka.movieapp.data.database.dao.GenreDao;
import com.example.irishka.movieapp.data.database.dao.GenreOfDescriptionDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.entity.GenreDb;
import com.example.irishka.movieapp.data.database.entity.GenresOfDescription;
import com.example.irishka.movieapp.data.mappers.BackdropMapper;
import com.example.irishka.movieapp.data.mappers.CastMapper;
import com.example.irishka.movieapp.data.mappers.DescriptionMapper;
import com.example.irishka.movieapp.data.mappers.GenreMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.mappers.ProductionCountryMapper;
import com.example.irishka.movieapp.data.models.CreditsModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GalleryModel;
import com.example.irishka.movieapp.data.models.MoviePageModel;
import com.example.irishka.movieapp.data.network.MoviesApi;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Description;
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

    private DescriptionMapper descriptionMapper;

    private CastMapper castMapper;

    private BackdropMapper backdropMapper;

    private GenreMapper genreMapper;

    private ProductionCountryMapper countryMapper;

    private MovieDao movieDao;

    private BackdropDao backdropDao;

    private CastDao castDao;

    private GenreDao genreDao;

    private GenreOfDescriptionDao genreOfDescriptonDao;

    private DescriptionDao descriptionDao;

    private MoviesApi moviesApi;

    @Inject
    public MoviesRepository(MoviesMapper moviesMapper, DescriptionMapper descriptionMapper,
                            CastMapper castMapper, BackdropMapper backdropMapper,
                            GenreMapper genreMapper, ProductionCountryMapper countryMapper,
                            MovieDao movieDao, BackdropDao backdropDao, CastDao castDao,
                            GenreDao genreDao, GenreOfDescriptionDao genreOfDescriptonDao, DescriptionDao descriptionDao,
                            MoviesApi moviesApi) {
        this.moviesMapper = moviesMapper;
        this.descriptionMapper = descriptionMapper;
        this.castMapper = castMapper;
        this.backdropMapper = backdropMapper;
        this.genreMapper = genreMapper;
        this.countryMapper = countryMapper;
        this.movieDao = movieDao;
        this.backdropDao = backdropDao;
        this.castDao = castDao;
        this.genreDao = genreDao;
        this.genreOfDescriptonDao = genreOfDescriptonDao;
        this.descriptionDao = descriptionDao;
        this.moviesApi = moviesApi;
    }

    private Single<List<Movie>> getMoviesFromInternet(int page) {
        return moviesApi
                .getMovies(page)
                .map(MoviePageModel::getResults)
                .doOnSuccess(movies -> movieDao.insertAll(moviesMapper.mapMoviesListToDb(movies)))
                .map(movies -> moviesMapper.mapMoviesList(movies));
    }

    private Single<List<Movie>> getMoviesFromDatabase() {
        return movieDao.getAllMovies()
                .map(movies -> moviesMapper.mapMoviesListFromDb(movies))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Movie>> downloadMovies(int page) {
        return getMoviesFromInternet(page)
                .onErrorResumeNext(getMoviesFromDatabase());
    }

    private Single<Description> getDescriptionFromInternet(long movieId) {
        return moviesApi
                .getDescription(movieId)
                .doOnSuccess(descriptionModel -> insertGoD(descriptionModel))
             //   .doOnSuccess(descriptionModel -> insertCoD(descriptionModel))
                .doOnSuccess(description -> descriptionDao.insert(descriptionMapper.applyToDb(description)))
                .map(descriptionModel -> descriptionMapper.apply(descriptionModel));
    }

    private Single<List<Genre>> getGenres(long movieId) {
        return genreOfDescriptonDao.getGoD(movieId)
                .toObservable()
                .flatMapIterable(list -> list)
                .map(genreOfDescription -> genreOfDescription.getGenreId())
                .flatMapSingle(id -> genreDao.getGenre(id))
                .toList()
                .map(list -> genreMapper.mapGenresListFromDb(list))
                .subscribeOn(Schedulers.io());
    }

    private Single<Description> getDescriptionFromDatabase(long movieId) {
        return descriptionDao.getDescription(movieId)
                .flatMap(descriptionDb -> getGenres(movieId)
                        .map(list -> new Pair<>(descriptionDb, list)))
                .map(pair -> descriptionMapper.applyFromDb(pair.first, pair.second))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Description> downloadDescription(long movieId){

        return getDescriptionFromInternet(movieId)
                .onErrorResumeNext(getDescriptionFromDatabase(movieId));
    }

    @Override
    public Single<List<Movie>> downloadRelatedMovies(long movieId){
        return moviesApi
                .getRelated(movieId)
                .map(MoviePageModel::getResults)
                .map(movies -> moviesMapper.mapMoviesList(movies));
    }

    private Single<List<Cast>> getCastsFromInternet(long movieId) {
        return moviesApi
                .getCreators(movieId)
                .map(CreditsModel::getCast)
                .doOnSuccess(casts -> castDao.insertAll(castMapper.mapCastsListToDb(casts)))
                .map(casts -> castMapper.mapCastsList(casts));
    }

    private Single<List<Cast>> getCastsFromDatabase() {
        return castDao.getAllCasts()
                .map(casts -> castMapper.mapCastsListFromDb(casts))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Cast>> downloadCasts(long movieId){
        return getCastsFromInternet(movieId)
                .onErrorResumeNext(getCastsFromDatabase());
    }

    private Single<List<Backdrop>> getBackdropsFromInternet(long movieId) {
        return moviesApi
                .getGallery(movieId)
                .map(GalleryModel::getBackdrops)
                .doOnSuccess(backdrops -> backdropDao.insertAll(backdropMapper.mapBackdropsListToDb(backdrops)))
                .map(backdrops -> backdropMapper.mapBackdropsList(backdrops));
    }

    private Single<List<Backdrop>> getBackdropsFromDatabase() {
        return backdropDao.getAllBackdrops()
                .map(backdrops -> backdropMapper.mapBackdropsListFromDb(backdrops))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Backdrop>> downloadGallery(long movieId) {
        return getBackdropsFromInternet(movieId)
                .onErrorResumeNext(getBackdropsFromDatabase());
    }

//    private void insertCoD(DescriptionModel description) {
//
//        List<CountriesOfDescription> countriesOfDescriptons = new ArrayList<>();
//
//        List<ProductionCountryDb> countries = countryMapper.mapProductionCountryListToDb(description.getProductionCountries());
//
//        for (int i = 0; i < description.getGenres().size(); i++) {
//
//            countriesOfDescriptons.add(new CountriesOfDescription(description.getId(), countries.get(i).getName()));
//
//        }
//
//        countriesOfDescriptionDao.insert(countriesOfDescriptons);
//    }

    private void insertGoD(DescriptionModel description) {

        List<GenresOfDescription> genreOfDescriptons = new ArrayList<>();

        List<GenreDb> genres = genreMapper.mapGenresListToDb(description.getGenres());

        for (int i = 0; i < description.getGenres().size(); i++) {

            genreOfDescriptons.add(new GenresOfDescription(description.getId(), genres.get(i).getId()));

        }

        genreDao.insert(genres);
        genreOfDescriptonDao.insert(genreOfDescriptons);
    }
}
