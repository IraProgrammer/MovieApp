package com.example.irishka.movieapp.data.repositories;

import com.example.irishka.movieapp.data.database.CastsDbSource;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.mappers.CastMapper;
import com.example.irishka.movieapp.data.network.CastsNetworkSource;
import com.example.irishka.movieapp.domain.entity.CastListWithError;
import com.example.irishka.movieapp.domain.entity.CastWithError;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.repositories.ICastsRepository;

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

    private Single<CastListWithError> getCastsFromInternet(long movieId) {
        return castsNetworkSource
                .getCasts(movieId)
                .map(castModels -> castMapper.mapCastsList(castModels))
                .doOnSuccess(casts -> castsDbSource.insertAllCasts(castMapper.mapCastsListToDb(casts)))
                .doOnSuccess(castModels -> castsDbSource.insertAllCoM(castMapper.createCoMList(movieId, castModels)))
                .map(casts -> new CastListWithError(casts, false));

    }

    private Single<CastListWithError> getCastsFromDatabase(long movieId) {
        return castsDbSource.getCastsOfMovie(movieId)
                .flattenAsObservable(list -> list)
                .map(CastOfMovie::getCastId)
                .flatMapSingle(id -> castsDbSource.getCast(id))
                .toList()
                .map(castsDb -> castMapper.mapCastsListFromDb(castsDb))
                .map(casts -> new CastListWithError(casts, true))
                .subscribeOn(Schedulers.io());
    }

    private Single<CastWithError> getMoreAboutActorFromInternet(long id) {
        return castsNetworkSource
                .getActorInfo(id)
                .flatMap(actorInfoModel -> castsNetworkSource.getActorPhotos(id)
                        .map(actorPhotosModel -> castMapper.apply(actorInfoModel, actorPhotosModel)))
                .doOnSuccess(cast -> castsDbSource.insertCast(castMapper.applyToDb(cast)))
                .map(cast -> new CastWithError(cast, false));
    }

    private Single<CastWithError> getConcreteCastFromDatabase(long id) {
        return castsDbSource.getCast(id)
                .map(castDb -> castMapper.applyFromDb(castDb))
                .map(cast -> new CastWithError(cast, true))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<CastListWithError> downloadCasts(long movieId) {
        return getCastsFromInternet(movieId)
                .onErrorResumeNext(getCastsFromDatabase(movieId));
    }

    @Override
    public Single<CastWithError> downloadConcreteCast(long id) {
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
