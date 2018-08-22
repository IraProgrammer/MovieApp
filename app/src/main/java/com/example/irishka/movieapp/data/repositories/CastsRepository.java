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
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.repositories.ICastsRepository;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class CastsRepository implements ICastsRepository {

    private CastMapper castMapper;

    private CastsDbSource castsDbSource;

    private CastsNetworkSource castsNetworkSource;

    @Inject
    CastsRepository(CastMapper castMapper, CastsDbSource castsDbSource, CastsNetworkSource castsNetworkSource) {
        this.castMapper = castMapper;
        this.castsDbSource = castsDbSource;
        this.castsNetworkSource = castsNetworkSource;
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

    @Override
    public void insertAllCoM(long id, List<Movie> movies) {
        castsDbSource.insertAllCoM(castMapper.createMoCList(id, movies));
    }

    @Override
    public Single<List<CastOfMovie>> getMoviesOfCast(long id) {
        return castsDbSource.getMoviesOfCast(id);
    }
}
