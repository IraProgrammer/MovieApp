package com.example.irishka.movieapp.ui.actor.di;

import android.graphics.Point;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.actor.films.presenter.FilmsPresenter;
import com.example.irishka.movieapp.ui.actor.films.view.FilmsAdapter;
import com.example.irishka.movieapp.ui.actor.films.view.FilmsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class FilmsFragmentModule {

    @Provides
    @PerFragment
    static FilmsPresenter providesFilmsPresenter(IMoviesRepository moviesRepository, long id) {
        return new FilmsPresenter(moviesRepository, id);
    }

    @Provides
    @PerFragment
    static FilmsAdapter provideFilmsAdapter(FilmsFragment filmsFragment, GlideHelper glideHelper){
        return new FilmsAdapter(filmsFragment, glideHelper);
    }

    @Provides
    @PerFragment
    static StaggeredGridLayoutManager providesStaggeredGridLayoutManager(FilmsFragment filmsFragment){

        Point point = new Point();
        filmsFragment.getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        int number = point.x;
        float scalefactor = filmsFragment.getResources().getDisplayMetrics().density * 150;
        int columns = (int) ((float) number / (float) scalefactor);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        return staggeredGridLayoutManager;
    }

}
