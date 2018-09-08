package com.example.irishka.movieapp.domain.repositories;

import java.util.List;

import io.reactivex.Single;

public interface IKeywordsRepository {

    Single<List<String>> getKeywordsFromInternet(String query);

    Single<List<String>> getKeywordsFromDb();

    void insertKeyword(String query);
}
