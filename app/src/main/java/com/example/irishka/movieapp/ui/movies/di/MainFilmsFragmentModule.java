package com.example.irishka.movieapp.ui.movies.di;

import android.graphics.Point;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.MainType;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsAdapter;
import com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment;
import com.example.irishka.movieapp.ui.movies.fragment.presenter.MainFilmsPresenter;

import dagger.Module;
import dagger.Provides;

import static com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment.TYPE;

@Module
public class MainFilmsFragmentModule {

    @Provides
    @PerFragment
    static MainType provideType(MainFilmsFragment mainFilmsFragment) {

        return MainType.values()[mainFilmsFragment.getArguments().getInt(TYPE)];
    }

    @Provides
    @PerFragment
    static MainFilmsPresenter providesMainFilmsPresenter(IMoviesRepository moviesRepository, MainType type) {
        return new MainFilmsPresenter(moviesRepository, type);
    }

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
