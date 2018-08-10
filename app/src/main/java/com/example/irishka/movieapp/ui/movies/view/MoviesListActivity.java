package com.example.irishka.movieapp.ui.movies.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageButton;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.example.irishka.movieapp.ui.movies.presenter.MoviesListPresenter;
import com.example.irishka.movieapp.ui.search.view.SearchActivity;
import com.example.irishka.movieapp.ui.search.view.SearchAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MoviesListActivity extends MvpAppCompatActivity implements MoviesListView, MoviesListAdapter.OnItemClickListener {

    public static final String MOVIE_ID = "id_of_movie";

    @BindView(R.id.movies_recycler_view)
    RecyclerView moviesRecyclerView;

    @BindView(R.id.btn_search)
    ImageButton btnSearch;

    @Inject
    Provider<MoviesListPresenter> moviesPresenterProvider;

    @InjectPresenter
    MoviesListPresenter moviesPresenter;

    @ProvidePresenter
    MoviesListPresenter providePresenter() {
        return moviesPresenterProvider.get();
    }

    @Inject
    MoviesListAdapter moviesAdapter;

    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        moviesRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        moviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = getLastVisibleItemPosition();

                if (isLoading) return;
                if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    moviesPresenter.downloadMovies();
                }
            }
        });

        moviesRecyclerView.setAdapter(moviesAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoviesListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void showMovies(List<Movie> movies) {
        moviesAdapter.addMoviesList(movies);
    }

    @Override
    public void finishLoading() {
        isLoading = false;
    }

    private int getLastVisibleItemPosition() {
        int[] into = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
        List<Integer> intoList = new ArrayList<>();
        for (int i : into) {
            intoList.add(i);
        }
        return Collections.max(intoList);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        intent.putExtra("TITLE", movie.getTitle());
        startActivity(intent);
    }
}
