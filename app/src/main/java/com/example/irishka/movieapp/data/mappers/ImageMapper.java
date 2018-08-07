package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.ImageDb;
import com.example.irishka.movieapp.data.models.ActorProfileModel;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.domain.entity.Image;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ImageMapper {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public ImageMapper() {

    }

    public Image apply(BackdropModel backdropModel) {
        Image backdrop = new Image();
        backdrop.setFileUrl(BASE_IMAGE_URL + backdropModel.getFilePath());
        return backdrop;
    }

    public ImageDb applyToDb(Image backdrop) {
        ImageDb backdropDb = new ImageDb();
        backdropDb.setFileUrl(backdrop.getFileUrl());
        return backdropDb;
    }

    public Image applyFromDb(ImageDb backdropDb) {
        Image backdrop = new Image();
        backdrop.setFileUrl(backdropDb.getFileUrl());
        return backdrop;
    }

    public Image apply(ActorProfileModel actorProfileModel) {
        Image backdrop = new Image();
        backdrop.setFileUrl(BASE_IMAGE_URL + actorProfileModel.getFilePath());
        return backdrop;
    }


    public List<Image> mapActorProfilesList(List<ActorProfileModel> actorProfileModels){
        List<Image> backdrops = new ArrayList<>();

        for (ActorProfileModel actorProfileModel: actorProfileModels) {
            backdrops.add(apply(actorProfileModel));
        }

        return backdrops;
    }

    public List<Image> mapBackdropsList(List<BackdropModel> backdropModels){
        List<Image> backdrops = new ArrayList<>();

        for (BackdropModel backdropModel: backdropModels) {
            backdrops.add(apply(backdropModel));
        }

        return backdrops;
    }

    public List<ImageDb> mapBackdropsListToDb(List<Image> backdropList){
        List<ImageDb> backdrops = new ArrayList<>();

        for (Image backdrop: backdropList) {
            backdrops.add(applyToDb(backdrop));
        }

        return backdrops;
    }

    public List<Image> mapBackdropsListFromDb(List<ImageDb> backdropsDb){
        List<Image> backdrops = new ArrayList<>();

        for (ImageDb backdropDb: backdropsDb) {
            backdrops.add(applyFromDb(backdropDb));
        }

        return backdrops;
    }

}
