package com.example.irishka.movieapp.ui.filters.di;

import android.support.v7.widget.LinearLayoutManager;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.filters.view.FiltersActivity;
import com.example.irishka.movieapp.ui.filters.view.FiltersAdapter;
import com.example.irishka.movieapp.ui.search.view.SearchActivity;
import com.example.irishka.movieapp.ui.search.view.SearchAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class FiltersActivityModule {

    @Provides
    @PerActivity
    static FiltersAdapter providesFiltersAdapter(FiltersActivity filtersActivity, GlideHelper glideHelper) {
        return new FiltersAdapter(filtersActivity, glideHelper);
    }

    @Provides
    @PerActivity
    static LinearLayoutManager providesLinearLayoutManager(FiltersActivity filtersActivity){
        return new LinearLayoutManager(filtersActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }

//    @Provides
//    @PerActivity
//    static String providesFilters(FiltersActivity filtersActivity){
//        return filtersActivity.getIntent().getStringExtra(FILTERS);
//    }

}
