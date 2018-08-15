package com.example.irishka.movieapp.ui.movies.di;

import android.graphics.Point;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.movies.fragment.MainFilmsAdapter;
import com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment;
import com.example.irishka.movieapp.ui.movies.view.MoviesListActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainFilmsFragmentModule {

    @Provides
    @PerFragment
    static StaggeredGridLayoutManager providesStaggeredGridLayoutManager(MainFilmsFragment mainFilmsFragment){

        Point point = new Point();
        mainFilmsFragment.getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        int number = point.x;
        float scalefactor = mainFilmsFragment.getActivity().getResources().getDisplayMetrics().density * 150;
        int columns = (int) ((float) number / (float) scalefactor);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        return staggeredGridLayoutManager;
    }

    @Provides
    @PerFragment
    static MainFilmsAdapter providesMainFilmsAdapter(MainFilmsFragment mainFilmsFragment, GlideHelper glideHelper){
        return new MainFilmsAdapter(mainFilmsFragment, glideHelper);
    }
}
