package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.irishka.movieapp.data.database.entity.CastDb;
import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.domain.entity.Keyword;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface KeywordsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(KeywordDb keywordDb);

    @Query("SELECT * FROM KeywordDb")
    Single<List<KeywordDb>> getKeywords();

}
