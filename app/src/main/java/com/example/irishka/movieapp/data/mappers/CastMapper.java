package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.ActorInfoDb;
import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;
import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.ActorProfileModel;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GenreModel;
import com.example.irishka.movieapp.domain.entity.ActorInfo;
import com.example.irishka.movieapp.domain.entity.Cast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class CastMapper {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public CastMapper() {
    }

    public Cast apply(ActorInfoModel infoModel, ActorPhotosModel actorPhotosModel) {
        Cast cast = new Cast();
        cast.setId(infoModel.getId());
        cast.setName(infoModel.getName());
        cast.setProfileUrl(BASE_IMAGE_URL + infoModel.getProfilePath());
        cast.setBiography(infoModel.getBiography());
        cast.setBirthday(infoModel.getBirthday());
        cast.setPlaceOfBirth(infoModel.getPlaceOfBirth());
        cast.setPhotosUrl(getPhotosUrl(actorPhotosModel));
        return cast;
    }

    public Cast apply(CastModel castModel) {
        Cast cast = new Cast();
        cast.setId(castModel.getId());
        cast.setName(castModel.getName());
        cast.setProfileUrl(BASE_IMAGE_URL + castModel.getProfilePath());
        cast.setBiography("");
        cast.setPhotosUrl(Collections.emptyList());
        cast.setPlaceOfBirth("");
        cast.setBirthday("");
        return cast;
    }

//    public CastDb applyToDb(ActorInfoModel infoModel, ActorPhotosModel actorPhotosModel) {
//        CastDb castDb = new CastDb();
//        castDb.setId(infoModel.getId());
//        castDb.setName(infoModel.getName());
//        castDb.setProfileUrl(BASE_IMAGE_URL + infoModel.getProfilePath());
//        castDb.setBiography(infoModel.getBiography());
//        castDb.setBirthday(infoModel.getBirthday());
//        castDb.setPlaceOfBirth(infoModel.getPlaceOfBirth());
//        castDb.setPhotosUrl(getPhotosUrl(actorPhotosModel));
//        return castDb;
//    }

    public CastDb applyToDb(Cast cast) {
        CastDb castDb = new CastDb();
        castDb.setId(cast.getId());
        castDb.setName(cast.getName());
        castDb.setProfileUrl(cast.getProfileUrl());
        castDb.setBiography(cast.getBiography());
        castDb.setPhotosUrl(cast.getPhotosUrl());
        castDb.setPlaceOfBirth(cast.getPlaceOfBirth());
        castDb.setBirthday(cast.getBirthday());
        return castDb;
    }

//    public Cast applyFromDb(ActorInfoDb infoDb, List<String> photosUrl) {
//        Cast cast = new Cast();
//        cast.setId(infoDb.getId());
//        cast.setName(infoDb.getName());
//        cast.setProfileUrl(BASE_IMAGE_URL + infoDb.getProfilePath());
//        cast.setBiography(infoDb.getBiography());
//        cast.setBirthday(infoDb.getBirthday());
//        cast.setPlaceOfBirth(infoDb.getPlaceOfBirth());
//        cast.setPhotosUrl(photosUrl);
//        return cast;
//    }

    public Cast applyFromDb(CastDb castDb) {
        Cast cast = new Cast();
        cast.setId(castDb.getId());
        cast.setName(castDb.getName());
        cast.setProfileUrl(castDb.getProfileUrl());
        cast.setBirthday(castDb.getBirthday());
        cast.setPlaceOfBirth(castDb.getPlaceOfBirth());
        cast.setBiography(castDb.getBiography());
        cast.setPhotosUrl(castDb.getPhotosUrl());
        return cast;
    }

    public List<Cast> mapCastsList(List<ActorInfoModel> actorInfos, List<ActorPhotosModel> actorPhotosModels) {
        List<Cast> casts = new ArrayList<>();

        for (int i = 0; i < actorInfos.size(); i++) {
            casts.add(apply(actorInfos.get(i), actorPhotosModels.get(i)));
        }

        return casts;
    }

    public List<Cast> mapCastsList(List<CastModel> castModels) {
        List<Cast> casts = new ArrayList<>();

        for (int i = 0; i < castModels.size(); i++) {
            casts.add(apply(castModels.get(i)));
        }

        return casts;
    }

//    public List<CastDb> mapCastsListToDb(List<CastModel> castModels, List<ActorInfoModel> actorInfos, List<ActorPhotosModel> actorPhotosModels) {
//        List<CastDb> casts = new ArrayList<>();
//
//        for (int i = 0; i < castModels.size(); i++) {
//            casts.add(applyToDb(castModels.get(i), actorInfos.get(i), actorPhotosModels.get(i)));
//        }
//
//        return casts;
//    }

    public List<CastDb> mapCastsListToDb(List<Cast> casts) {
        List<CastDb> castsDb = new ArrayList<>();

        for (int i = 0; i < casts.size(); i++) {
            castsDb.add(applyToDb(casts.get(i)));
        }

        return castsDb;
    }

//    public List<Cast> mapCastsListFromDb(List<ActorInfoDb> actorInfosDb, List<List<String>> photosUrl) {
//        List<Cast> casts = new ArrayList<>();
//
//        for (int i = 0; i < actorInfosDb.size(); i++) {
//            casts.add(applyFromDb(actorInfosDb.get(i), photosUrl.get(i)));
//        }
//
//        return casts;
//    }

    public List<Cast> mapCastsListFromDb(List<CastDb> castsDb) {
        List<Cast> casts = new ArrayList<>();

        for (int i = 0; i < castsDb.size(); i++) {
            casts.add(applyFromDb(castsDb.get(i)));
        }

        return casts;
    }

    private List<String> getPhotosUrl(ActorPhotosModel actorPhotosModel) {
        List<String> photosUrl = new ArrayList<>();
        for (ActorProfileModel profileModel : actorPhotosModel.getProfiles()) {
            photosUrl.add(BASE_IMAGE_URL + profileModel.getFilePath());
        }
        return photosUrl;
    }

    public List<CastOfMovie> createCoMList(long movieId, List<Cast> casts) {
        List<CastOfMovie> castsOfMovie = new ArrayList<>();

        for (int i = 0; i < casts.size(); i++) {

            castsOfMovie.add(new CastOfMovie(movieId, casts.get(i).getId()));
        }

        return castsOfMovie;
    }
}
