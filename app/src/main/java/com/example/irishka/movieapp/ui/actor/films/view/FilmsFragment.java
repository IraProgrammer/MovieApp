package com.example.irishka.movieapp.ui.actor.films.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.actor.films.presenter.FilmsPresenter;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.irishka.movieapp.ui.movie.view.MovieActivity.TITLE;
import static com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment.MOVIE_ID;

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

    @BindView(R.id.progress)
    MaterialProgressBar progressBar;

    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    int i = 1;

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

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.downloadFilms();
        });

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent_material_dark_1));

        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.background_holo_dark));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            swipeRefreshLayout.setElevation(4);
        }

        return v;
    }

    @Override
    public void showError() {
        swipeRefreshLayout.setRefreshing(false);

        if (i > 1)
        Toast.makeText(this.getContext(), getResources().getString(R.string.error_description), Toast.LENGTH_SHORT).show();
        i++;
    }

    @Override
    public void hideError() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        filmsAdapter.setMoviesList(movies);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        intent.putExtra(TITLE, movie.getTitle());
        startActivity(intent);
    }
}
