package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.GenreDb;
import com.example.irishka.movieapp.data.database.entity.GenreOfMovie;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.GenreModel;
import com.example.irishka.movieapp.domain.entity.Genre;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class GenreMapper{

    @Inject
    public GenreMapper() {

    }

    public Genre apply(GenreModel genreModel){
        Genre genre = new Genre();
        genre.setId(genreModel.getId());
        genre.setName(genreModel.getName());
        return genre;
    }

    public GenreDb applyToDb(GenreModel genreModel){
        GenreDb genre = new GenreDb();
        genre.setId(genreModel.getId());
        genre.setName(genreModel.getName());
        return genre;
    }

    public GenreDb applyToDb(Genre genreModel){
        GenreDb genre = new GenreDb();
        genre.setId(genreModel.getId());
        genre.setName(genreModel.getName());
        return genre;
    }

    public Genre applyFromDb(GenreDb genreDb){
        Genre genre = new Genre();
        genre.setId(genreDb.getId());
        genre.setName(genreDb.getName());
        return genre;
    }

    public List<GenreOfMovie> createGoMList(DescriptionModel description){
        List<GenreOfMovie> genresOfMovie = new ArrayList<>();

        List<GenreModel> genres = description.getGenres();

        for (int i = 0; i < genres.size(); i++) {

            genresOfMovie.add(new GenreOfMovie(description.getId(), genres.get(i).getId()));
        }

        return genresOfMovie;

    }

    public List<Genre> mapGenresList(List<GenreModel> genreModels){
        List<Genre> genres = new ArrayList<>();

        for (GenreModel genreModel: genreModels) {
            genres.add(apply(genreModel));
        }

        return genres;
    }

    public List<GenreDb> mapGenresListToDb(List<GenreModel> genreModels){
        List<GenreDb> genres = new ArrayList<>();
        for (GenreModel genreModel: genreModels) {
            genres.add(applyToDb(genreModel));
        }

        return genres;
    }

    public List<Genre> mapGenresListFromDb(List<GenreDb> genresDb){
        List<Genre> genres = new ArrayList<>();

        for (GenreDb genreDb: genresDb) {
            genres.add(applyFromDb(genreDb));
        }

        return genres;
    }
}
