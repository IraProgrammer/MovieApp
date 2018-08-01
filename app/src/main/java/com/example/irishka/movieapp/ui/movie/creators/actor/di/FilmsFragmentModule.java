package com.example.irishka.movieapp.ui.movie.creators.actor.di;

import android.graphics.Point;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.actor.ActorActivity;
import com.example.irishka.movieapp.ui.movie.creators.actor.films.FilmsPresenter;
import com.example.irishka.movieapp.ui.movie.creators.actor.films.view.FilmsAdapter;
import com.example.irishka.movieapp.ui.movie.creators.actor.films.view.FilmsFragment;
import com.example.irishka.movieapp.ui.movie.creators.view.CreatorsFragment;
import com.example.irishka.movieapp.ui.movies.view.MoviesListActivity;

import dagger.Module;
import dagger.Provides;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

@Module
public class FilmsFragmentModule {

    @Provides
    @PerFragment
    static FilmsPresenter providesFilmsPresenter(IMoviesRepository moviesRepository, long id) {
        return new FilmsPresenter(moviesRepository, id);
    }

    @Provides
    @PerFragment
    static FilmsAdapter provideFilmsAdapter(FilmsFragment filmsFragment){
        return new FilmsAdapter(filmsFragment);
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
