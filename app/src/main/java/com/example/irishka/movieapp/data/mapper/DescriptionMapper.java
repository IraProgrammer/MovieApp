package com.example.irishka.movieapp.data.mapper;

import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GenreModel;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.data.models.ProductionCountryModel;
import com.example.irishka.movieapp.domain.entity.Description;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class DescriptionMapper implements Function<DescriptionModel, Description> {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    @Inject
    public DescriptionMapper() {

    }

    @Override
    public Description apply(DescriptionModel descriptionModel) {
        Description description = new Description();
        description.setAdult(descriptionModel.getAdult());

        Genre genre;
        List<Genre> genres = new ArrayList<>();
        for (GenreModel genreModel: descriptionModel.getGenres())
              {
                  genre = new Genre();
                  genre.setId(genreModel.getId());
                  genre.setName(genreModel.getName());
            genres.add(genre);
        }
        description.setGenres(genres);

        description.setId(descriptionModel.getId());
        description.setOriginalLanguage(descriptionModel.getOriginalLanguage());
        description.setOverview(descriptionModel.getOverview());
        description.setPosterPath(BASE_IMAGE_URL + descriptionModel.getPosterPath());
        description.setPopularity(descriptionModel.getPopularity());
        description.setReleaseDate(descriptionModel.getReleaseDate());

        ProductionCountry country;
        List<ProductionCountry> countries = new ArrayList<>();
        for (ProductionCountryModel countryModel: descriptionModel.getProductionCountries())
        {
            country = new ProductionCountry();
            country.setIso31661(countryModel.getIso31661());
            country.setName(countryModel.getName());
            countries.add(country);
        }

        description.setProductionCountries(countries);
        description.setRevenue(descriptionModel.getRevenue());
        description.setRuntime(descriptionModel.getRuntime());
        description.setTitle(descriptionModel.getTitle());
        description.setVideo(descriptionModel.getVideo());

        return description;
    }

    public List<Description> mapDescriptionList(List<DescriptionModel> descriptionModels){
        List<Description> descriptions = new ArrayList<>();

        for (DescriptionModel descriptionModel: descriptionModels) {
            descriptions.add(apply(descriptionModel));
        }

        return descriptions;
    }
}

