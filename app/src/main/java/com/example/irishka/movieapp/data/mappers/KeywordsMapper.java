package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.data.models.KeywordModel;
import com.example.irishka.movieapp.data.models.TrailerModel;
import com.example.irishka.movieapp.domain.entity.Keyword;
import com.example.irishka.movieapp.domain.entity.Trailer;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class KeywordsMapper {

    @Inject
    public KeywordsMapper() {

    }

    public List<String> mapKeywordList(List<KeywordModel> keywordModels){

        List<String> keywords = new ArrayList<>();

        for (KeywordModel keywordModel: keywordModels) {
            keywords.add(keywordModel.getName());
        }

        if (keywords.size() > 15) return keywords.subList(0, 14);
        return keywords;

    }


    public List<String> mapKeywordListFromDb(List<KeywordDb> keywordsDb){

        List<String> keywords = new ArrayList<>();

        for (KeywordDb keywordDb: keywordsDb) {
            keywords.add(keywordDb.getName());
        }

        if (keywords.size() > 15) return keywords.subList(0, 14);
        return keywords;

    }

}
