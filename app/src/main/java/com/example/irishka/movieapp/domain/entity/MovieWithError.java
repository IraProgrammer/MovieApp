package com.example.irishka.movieapp.domain.entity;

import java.util.List;

public class MovieWithError {

    private Movie movie;

    private boolean error;

    public MovieWithError(Movie movie, boolean error){
        this.movie = movie;
        this.error = error;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
