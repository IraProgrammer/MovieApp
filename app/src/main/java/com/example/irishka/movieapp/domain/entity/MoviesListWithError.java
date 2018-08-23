package com.example.irishka.movieapp.domain.entity;

import java.util.List;

public class MoviesListWithError {

    private List<Movie> movies;

    private boolean error;

    public MoviesListWithError(List<Movie> movies, boolean error){
        this.movies = movies;
        this.error = error;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
