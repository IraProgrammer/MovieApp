package com.example.irishka.movieapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.data.database.entity.MovieIdsFromSearch;
import com.example.irishka.movieapp.domain.entity.Keyword;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class KeywordsDao {

    @Query("SELECT * FROM KeywordDb")
    public abstract Single<List<KeywordDb>> getKeywords();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insert(KeywordDb keywordDb);

    @Transaction
    public void trans(KeywordDb keywordDb) {

            if (getKeywordsForCount().size() > 15) {
                delete();
            }

            if (getKeyword(keywordDb.getName()).size() == 0) {
                insert(keywordDb);
            }
    }

    @Query("SELECT * FROM KeywordDb WHERE name = :name")
    abstract List<KeywordDb> getKeyword(String name);

    @Query("DELETE FROM KeywordDb WHERE id = (SELECT MIN(id) FROM KeywordDb)")
    abstract void delete();

    @Query("SELECT * FROM KeywordDb")
    abstract List<KeywordDb> getKeywordsForCount();

}
