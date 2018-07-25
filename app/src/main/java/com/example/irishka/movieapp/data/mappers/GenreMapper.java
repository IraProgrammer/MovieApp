package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GenreModel;
import com.example.irishka.movieapp.domain.entity.Description;
import com.example.irishka.movieapp.domain.entity.Genre;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class GenreMapper implements Function<GenreModel, Genre> {

    @Inject
    public GenreMapper() {

    }

    @Override
    public Genre apply(GenreModel genreModel){
        Genre genre = new Genre();
        genre.setId(genreModel.getId());
        genre.setName(genreModel.getName());
        return genre;
    }

    public List<Genre> mapGenresList(List<GenreModel> genreModels){
        List<Genre> genres = new ArrayList<>();

        for (GenreModel genreModel: genreModels) {
            genres.add(apply(genreModel));
        }

        return genres;
    }


}
