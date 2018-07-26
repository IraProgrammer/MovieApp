package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.DescriptionDb;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.domain.entity.Description;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class DescriptionMapper{

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    private GenreMapper genreMapper;

    private ProductionCountryMapper productionCountryMapper;

    @Inject
    public DescriptionMapper(GenreMapper genreMapper, ProductionCountryMapper productionCountryMapper) {
        this.genreMapper = genreMapper;
        this.productionCountryMapper = productionCountryMapper;
    }

    public Description apply(DescriptionModel descriptionModel) {
        Description description = new Description();
        description.setAdult(descriptionModel.getAdult());

        description.setGenres(genreMapper.mapGenresList(descriptionModel.getGenres()));

        description.setId(descriptionModel.getId());
        description.setOriginalLanguage(descriptionModel.getOriginalLanguage());
        description.setOverview(descriptionModel.getOverview());
        description.setPosterUrl(BASE_IMAGE_URL + descriptionModel.getPosterPath());
        description.setPopularity(descriptionModel.getPopularity());
        description.setReleaseDate(descriptionModel.getReleaseDate());

        description.setProductionCountries(productionCountryMapper.mapProductionCountryList(descriptionModel.getProductionCountries()));

        description.setRuntime(descriptionModel.getRuntime());
        description.setTitle(descriptionModel.getTitle());
        description.setVideo(descriptionModel.getVideo());
        description.setVoteAverage(descriptionModel.getVoteAverage());

        return description;
    }

    public DescriptionDb applyToDb(DescriptionModel description) {
        DescriptionDb descriptionDb = new DescriptionDb();
        descriptionDb.setAdult(description.getAdult());
        descriptionDb.setId(description.getId());
        descriptionDb.setOverview(description.getOverview());
        descriptionDb.setPosterUrl(BASE_IMAGE_URL + description.getPosterPath());
        descriptionDb.setReleaseDate(description.getReleaseDate());
        descriptionDb.setRuntime(description.getRuntime());
        descriptionDb.setTitle(description.getTitle());
        descriptionDb.setVideo(description.getVideo());
        descriptionDb.setVoteAverage(description.getVoteAverage());

        return descriptionDb;
    }

    public Description applyFromDb(DescriptionDb descriptionDb, List<Genre> genres, List<ProductionCountry> productionCountries) {
        Description description = new Description();
        description.setAdult(descriptionDb.getAdult());
        description.setId(descriptionDb.getId());

        description.setGenres(genres);
        description.setProductionCountries(productionCountries);

        description.setOverview(descriptionDb.getOverview());
        description.setPosterUrl(descriptionDb.getPosterUrl());
        description.setReleaseDate(descriptionDb.getReleaseDate());
        description.setRuntime(descriptionDb.getRuntime());
        description.setTitle(descriptionDb.getTitle());
        description.setVideo(descriptionDb.getVideo());
        description.setVoteAverage(descriptionDb.getVoteAverage());

        return description;
    }
}

