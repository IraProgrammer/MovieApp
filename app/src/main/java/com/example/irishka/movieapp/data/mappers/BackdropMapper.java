package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.domain.entity.Backdrop;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class BackdropMapper implements Function<BackdropModel, Backdrop> {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public BackdropMapper() {

    }

    @Override
    public Backdrop apply(BackdropModel backdropModel) {
        Backdrop backdrop = new Backdrop();
        backdrop.setFileUrl(BASE_IMAGE_URL + backdropModel.getFilePath());
        return backdrop;
    }

    public List<Backdrop> mapBackdropsList(List<BackdropModel> backdropModels){
        List<Backdrop> backdrops = new ArrayList<>();

        for (BackdropModel backdropModel: backdropModels) {
            backdrops.add(apply(backdropModel));
        }

        return backdrops;
    }

}
