package com.example.irishka.movieapp.domain.entity;

import java.util.List;

public class CastListWithError {

    private List<Cast> casts;

    private boolean error;

    public CastListWithError(List<Cast> casts, boolean error){
        this.casts = casts;
        this.error = error;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
