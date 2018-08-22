package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.data.models.KeywordModel;
import com.example.irishka.movieapp.data.models.KeywordsPageModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class KeywordsNetworkSource {

    private MoviesApi moviesApi;

    @Inject
    public KeywordsNetworkSource(MoviesApi moviesApi) {
        this.moviesApi = moviesApi;
    }

    public Single<List<KeywordModel>> getKeywords(String query) {
        return  moviesApi.getKeywords(query)
                .map(KeywordsPageModel::getResults); }
}
