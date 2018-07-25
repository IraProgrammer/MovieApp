package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GenreModel;
import com.example.irishka.movieapp.data.models.ProductionCountryModel;
import com.example.irishka.movieapp.domain.entity.Description;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class DescriptionMapper implements Function<DescriptionModel, Description> {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    private GenreMapper genreMapper;

    private ProductionCountryMapper productionCountryMapper;

    @Inject
    public DescriptionMapper(GenreMapper genreMapper, ProductionCountryMapper productionCountryMapper) {
        this.genreMapper = genreMapper;
        this.productionCountryMapper = productionCountryMapper;
    }

    @Override
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

    public List<Description> mapDescriptionList(List<DescriptionModel> descriptionModels) {
        List<Description> descriptions = new ArrayList<>();

        for (DescriptionModel descriptionModel : descriptionModels) {
            descriptions.add(apply(descriptionModel));
        }

        return descriptions;
    }
}

