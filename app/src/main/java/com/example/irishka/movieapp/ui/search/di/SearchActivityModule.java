package com.example.irishka.movieapp.ui.search.di;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.search.view.SearchActivity;
import com.example.irishka.movieapp.ui.search.view.SearchAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchActivityModule {

    @Provides
    @PerActivity
    static SearchAdapter providesSearchAdapter(SearchActivity searchActivity, GlideHelper glideHelper) {
        return new SearchAdapter(searchActivity, glideHelper);
    }

    @Provides
    @PerActivity
    static LinearLayoutManager providesLinearLayoutManager(SearchActivity searchActivity){
        return new LinearLayoutManager(searchActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    @PerActivity
    static SearchManager providesSearchManager(SearchActivity searchActivity){
        return (SearchManager) searchActivity.getSystemService(Context.SEARCH_SERVICE);
    }
}
