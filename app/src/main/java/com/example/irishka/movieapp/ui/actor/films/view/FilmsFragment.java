package com.example.irishka.movieapp.ui.actor.films.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.models.MovieModel;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.actor.films.presenter.FilmsPresenter;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

public class FilmsFragment extends MvpAppCompatFragment implements FilmsView, FilmsAdapter.OnItemClickListener {

    @Inject
    Provider<FilmsPresenter> presenterProvider;

    @InjectPresenter
    FilmsPresenter presenter;

    @ProvidePresenter
    FilmsPresenter providePresenter() {
        return presenterProvider.get();
    }

    @Inject
    FilmsAdapter filmsAdapter;

    @BindView(R.id.movies_recycler_view)
    RecyclerView filmsRecyclerView;

    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    public static FilmsFragment newInstance(){
        return new FilmsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, v);

        filmsRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        filmsRecyclerView.setAdapter(filmsAdapter);

        return v;
    }

    @Override
    public void showMovies(List<Movie> movies) {
        filmsAdapter.setMoviesList(movies);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        startActivity(intent);
    }
}
