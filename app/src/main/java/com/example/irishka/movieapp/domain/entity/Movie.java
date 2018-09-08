
package com.example.irishka.movieapp.domain.entity;

import java.util.List;

public class Movie {

    private long id;

    private String title;

    private String posterUrl;

    private String releaseDate;

    private boolean adult;

    private String voteAverageStr;

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    private double voteAverage;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    private String overview;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    private List<Genre> genres;

    private int runtime;

    private List<ProductionCountry> countries;

    private List<Image> backdrops;

    private Trailer trailer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getVoteAverageStr() {
        return voteAverageStr;
    }

    public void setVoteAverageStr(String voteAverageStr) {
        this.voteAverageStr = voteAverageStr;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<ProductionCountry> getCountries() {
        return countries;
    }

    public void setCountries(List<ProductionCountry> countries) {
        this.countries = countries;
    }

    public List<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Image> backdrops) {
        this.backdrops = backdrops;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

}
