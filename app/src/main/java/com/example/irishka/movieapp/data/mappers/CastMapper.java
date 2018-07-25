package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.domain.entity.Cast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class CastMapper implements Function<CastModel, Cast> {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public CastMapper() {

    }

    @Override
    public Cast apply(CastModel castModel) {
        Cast cast = new Cast();
        cast.setName(castModel.getName());
        cast.setProfileUrl(BASE_IMAGE_URL + castModel.getProfilePath());
        return cast;
    }

    public List<Cast> mapCastsList(List<CastModel> castModels){
        List<Cast> casts = new ArrayList<>();

        for (CastModel castModel: castModels) {
            casts.add(apply(castModel));
        }

        return casts;
    }
}
