package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.BackdropDb;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.domain.entity.Backdrop;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class BackdropMapper{

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public BackdropMapper() {

    }

    public Backdrop apply(BackdropModel backdropModel) {
        Backdrop backdrop = new Backdrop();
        backdrop.setFileUrl(BASE_IMAGE_URL + backdropModel.getFilePath());
        return backdrop;
    }

    public BackdropDb applyToDb(Backdrop backdrop) {
        BackdropDb backdropDb = new BackdropDb();
        backdropDb.setFileUrl(backdrop.getFileUrl());
        return backdropDb;
    }

    public Backdrop applyFromDb(BackdropDb backdropDb) {
        Backdrop backdrop = new Backdrop();
        backdrop.setFileUrl(backdropDb.getFileUrl());
        return backdrop;
    }

    public List<Backdrop> mapBackdropsList(List<BackdropModel> backdropModels){
        List<Backdrop> backdrops = new ArrayList<>();

        for (BackdropModel backdropModel: backdropModels) {
            backdrops.add(apply(backdropModel));
        }

        return backdrops;
    }

    public List<BackdropDb> mapBackdropsListToDb(List<Backdrop> backdropList){
        List<BackdropDb> backdrops = new ArrayList<>();

        for (Backdrop backdrop: backdropList) {
            backdrops.add(applyToDb(backdrop));
        }

        return backdrops;
    }

    public List<Backdrop> mapBackdropsListFromDb(List<BackdropDb> backdropsDb){
        List<Backdrop> backdrops = new ArrayList<>();

        for (BackdropDb backdropDb: backdropsDb) {
            backdrops.add(applyFromDb(backdropDb));
        }

        return backdrops;
    }

}
