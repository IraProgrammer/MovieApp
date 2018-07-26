package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.domain.entity.Cast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class CastMapper{

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public CastMapper() {

    }

    public Cast apply(CastModel castModel) {
        Cast cast = new Cast();
        cast.setCastId(castModel.getCastId());
        cast.setName(castModel.getName());
        cast.setProfileUrl(BASE_IMAGE_URL + castModel.getProfilePath());
        return cast;
    }

    public CastDb applyToDb(CastModel castModel) {
        CastDb cast = new CastDb();
        cast.setCastId(castModel.getCastId());
        cast.setName(castModel.getName());
        cast.setProfileUrl(BASE_IMAGE_URL + castModel.getProfilePath());
        return cast;
    }

    public Cast applyFromDb(CastDb castDb) {
        Cast cast = new Cast();
        cast.setCastId(castDb.getCastId());
        cast.setName(castDb.getName());
        cast.setProfileUrl(castDb.getProfileUrl());
        return cast;
    }

    public List<Cast> mapCastsList(List<CastModel> castModels){
        List<Cast> casts = new ArrayList<>();

        for (CastModel castModel: castModels) {
            casts.add(apply(castModel));
        }

        return casts;
    }

    public List<CastDb> mapCastsListToDb(List<CastModel> castModels){
        List<CastDb> casts = new ArrayList<>();

        for (CastModel castModel: castModels) {
            casts.add(applyToDb(castModel));
        }

        return casts;
    }

    public List<Cast> mapCastsListFromDb(List<CastDb> castsDb){
        List<Cast> casts = new ArrayList<>();

        for (CastDb castDb: castsDb) {
            casts.add(applyFromDb(castDb));
        }

        return casts;
    }
}
