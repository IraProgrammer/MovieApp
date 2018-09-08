package com.example.irishka.movieapp.data.interactors;

import com.example.irishka.movieapp.data.database.KeywordsDbSource;
import com.example.irishka.movieapp.data.database.entity.KeywordDb;
import com.example.irishka.movieapp.data.mappers.KeywordsMapper;
import com.example.irishka.movieapp.data.mappers.MoviesMapper;
import com.example.irishka.movieapp.data.network.KeywordsNetworkSource;
import com.example.irishka.movieapp.data.network.MoviesNetworkSource;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.MoviesListWithError;
import com.example.irishka.movieapp.domain.interactors.ISearchInteractor;
import com.example.irishka.movieapp.domain.repositories.IKeywordsRepository;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class SearchInteractor implements ISearchInteractor {

    private IMoviesRepository moviesRepository;

    private IKeywordsRepository keywordsRepository;

    @Inject
    SearchInteractor(IMoviesRepository moviesRepository, IKeywordsRepository keywordsRepository) {
        this.moviesRepository = moviesRepository;
        this.keywordsRepository = keywordsRepository;
    }


    @Override
    public Single<List<String>> getKeywordsFromInternet(String query) {
        return keywordsRepository.getKeywordsFromInternet(query);
    }

    @Override
    public Single<List<String>> getKeywordsFromDb() {
        return keywordsRepository.getKeywordsFromDb();
    }


    @Override
    public Single<MoviesListWithError> getMoviesFromSearchFromInternet(String query, int page) {
        return moviesRepository.getMoviesFromSearchFromInternet(query, page)
                .doOnSuccess(movies -> keywordsRepository.insertKeyword(query));
    }

    @Override
    public Single<List<Movie>> getMoviesForSearchFromDatabase() {
        return moviesRepository.getMoviesForSearchFromDatabase();
    }
}
