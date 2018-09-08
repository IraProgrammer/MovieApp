package com.example.irishka.movieapp.data.database;

import com.example.irishka.movieapp.data.database.dao.KeywordsDao;
import com.example.irishka.movieapp.data.database.dao.MovieDao;
import com.example.irishka.movieapp.data.database.dao.MovieWithCategoryDao;
import com.example.irishka.movieapp.data.database.dao.RelatedOfMovieDao;
import com.example.irishka.movieapp.data.database.entity.KeywordDb;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class KeywordsDbSource {

    private KeywordsDao keywordsDao;

    @Inject
    public KeywordsDbSource(KeywordsDao keywordsDao) {
        this.keywordsDao = keywordsDao;
    }

    public void insertKeyword(KeywordDb keywordDb) {
        keywordsDao.trans(keywordDb);
    }

    public Single<List<KeywordDb>> getKeywords() {
        return keywordsDao.getKeywords();
    }

}
