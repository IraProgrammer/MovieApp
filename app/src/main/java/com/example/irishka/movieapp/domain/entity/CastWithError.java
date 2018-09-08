package com.example.irishka.movieapp.domain.entity;

public class CastWithError {

    private Cast cast;

    private boolean error;

    public CastWithError(Cast cast, boolean error){
        this.cast = cast;
        this.error = error;
    }

    public Cast getCast() {
        return cast;
    }

    public void setCast(Cast cast) {
        this.cast = cast;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
