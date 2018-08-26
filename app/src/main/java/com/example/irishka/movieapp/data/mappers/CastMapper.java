package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.CastOfMovie;
import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class CastMapper {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    private ImageMapper backdropMapper;

    @Inject
    public CastMapper(ImageMapper backdropMapper) {
        this.backdropMapper = backdropMapper;
    }

    public Cast apply(ActorInfoModel infoModel, ActorPhotosModel actorPhotosModel) {
        Cast cast = new Cast();
        cast.setId(infoModel.getId());
        cast.setName(infoModel.getName());
        cast.setProfileUrl(BASE_IMAGE_URL + infoModel.getProfilePath());
        cast.setBiography(infoModel.getBiography());
        cast.setBirthday(infoModel.getBirthday());
        cast.setPlaceOfBirth(infoModel.getPlaceOfBirth());
        cast.setPhotos(backdropMapper.mapActorProfilesList(actorPhotosModel.getProfiles()));
        return cast;
    }

    public Cast apply(CastModel castModel) {
        Cast cast = new Cast();
        cast.setId(castModel.getId());
        cast.setName(castModel.getName());
        cast.setProfileUrl(BASE_IMAGE_URL + castModel.getProfilePath());
        cast.setBiography("");
        cast.setPhotos(Collections.emptyList());
        cast.setPlaceOfBirth("");
        cast.setBirthday("");
        return cast;
    }

    public CastDb applyToDb(Cast cast) {
        CastDb castDb = new CastDb();
        castDb.setId(cast.getId());
        castDb.setName(cast.getName());
        castDb.setProfileUrl(cast.getProfileUrl());
        castDb.setBiography(cast.getBiography());
        castDb.setPhotos(backdropMapper.mapBackdropsListToDb(cast.getPhotos()));
        castDb.setPlaceOfBirth(cast.getPlaceOfBirth());
        castDb.setBirthday(cast.getBirthday());
        return castDb;
    }

    public Cast applyFromDb(CastDb castDb) {
        Cast cast = new Cast();
        cast.setId(castDb.getId());
        cast.setName(castDb.getName());
        cast.setProfileUrl(castDb.getProfileUrl());
        cast.setBirthday(castDb.getBirthday());
        cast.setPlaceOfBirth(castDb.getPlaceOfBirth());
        cast.setBiography(castDb.getBiography());
        cast.setPhotos(backdropMapper.mapBackdropsListFromDb(castDb.getPhotos()));
        return cast;
    }

//    public List<Cast> mapCastsList(List<ActorInfoModel> actorInfos, List<ActorPhotosModel> actorPhotosModels) {
//        List<Cast> casts = new ArrayList<>();
//
//        for (int i = 0; i < actorInfos.size(); i++) {
//            casts.add(apply(actorInfos.get(i), actorPhotosModels.get(i)));
//        }
//
//        return casts;
//    }

    public List<Cast> mapCastsList(List<CastModel> castModels) {
        List<Cast> casts = new ArrayList<>();

        for (int i = 0; i < castModels.size(); i++) {
            if (castModels.get(i).getProfilePath() != null) {
                casts.add(apply(castModels.get(i)));
            }
        }

        return casts;
    }

    public List<CastDb> mapCastsListToDb(List<Cast> casts) {
        List<CastDb> castsDb = new ArrayList<>();

        for (int i = 0; i < casts.size(); i++) {
            castsDb.add(applyToDb(casts.get(i)));
        }

        return castsDb;
    }

    public List<Cast> mapCastsListFromDb(List<CastDb> castsDb) {
        List<Cast> casts = new ArrayList<>();

        for (int i = 0; i < castsDb.size(); i++) {
            casts.add(applyFromDb(castsDb.get(i)));
        }

        return casts;
    }

//    private List<String> getPhotosUrl(ActorPhotosModel actorPhotosModel) {
//        List<String> photosUrl = new ArrayList<>();
//        for (ActorProfileModel profileModel : actorPhotosModel.getProfiles()) {
//            photosUrl.add(BASE_IMAGE_URL + profileModel.getFilePath());
//        }
//        return photosUrl;
//    }

    public List<CastOfMovie> createCoMList(long movieId, List<Cast> casts) {
        List<CastOfMovie> castsOfMovie = new ArrayList<>();

        for (int i = 0; i < casts.size(); i++) {

            castsOfMovie.add(new CastOfMovie(movieId, casts.get(i).getId()));
        }

        return castsOfMovie;
    }

    public List<CastOfMovie> createMoCList(long id, List<Movie> movies) {
        List<CastOfMovie> castsOfMovie = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++) {

            castsOfMovie.add(new CastOfMovie(movies.get(i).getId(), id));
        }

        return castsOfMovie;
    }
}
