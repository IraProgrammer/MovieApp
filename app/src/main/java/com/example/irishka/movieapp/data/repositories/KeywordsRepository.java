package com.example.irishka.movieapp.data.repositories;

import com.example.irishka.movieapp.data.database.KeywordsDbSource;
import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.data.mappers.KeywordsMapper;
import com.example.irishka.movieapp.data.network.KeywordsNetworkSource;
import com.example.irishka.movieapp.domain.repositories.IKeywordsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class KeywordsRepository implements IKeywordsRepository {

    private KeywordsMapper keywordsMapper;

    private KeywordsDbSource keywordsDbSource;

    private KeywordsNetworkSource keywordsNetworkSource;

    @Inject
    public KeywordsRepository(KeywordsMapper keywordsMapper, KeywordsDbSource keywordsDbSource,
                              KeywordsNetworkSource keywordsNetworkSource) {
        this.keywordsMapper = keywordsMapper;
        this.keywordsDbSource = keywordsDbSource;
        this.keywordsNetworkSource = keywordsNetworkSource;
    }

    @Override
    public Single<List<String>> getKeywordsFromInternet(String query) {
        return keywordsNetworkSource.getKeywords(query)
                .map(keywordModels -> keywordsMapper.mapKeywordList(keywordModels));
    }

    @Override
    public Single<List<String>> getKeywordsFromDb() {
        return keywordsDbSource.getKeywords()
                .map(keywordsDb -> keywordsMapper.mapKeywordListFromDb(keywordsDb))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void insertKeyword(String query) {
        keywordsDbSource.insertKeyword(new KeywordDb(query));
    }
}
