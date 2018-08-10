package com.example.irishka.movieapp.ui.search.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.example.irishka.movieapp.ui.search.presenter.SearchPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

public class SearchActivity extends MvpAppCompatActivity implements com.example.irishka.movieapp.ui.search.view.SearchView,
        com.example.irishka.movieapp.ui.search.view.SearchAdapter.OnItemClickListener {

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.btn_home)
    ImageButton btnHome;

    @BindView(R.id.search_recycler_view)
    RecyclerView searchRecyclerView;

    @Inject
    Provider<SearchPresenter> searchPresenterProvider;

    @InjectPresenter
    SearchPresenter searchPresenter;

    @ProvidePresenter
    SearchPresenter providePresenter() {
        return searchPresenterProvider.get();
    }

    @Inject
    SearchAdapter searchAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    private boolean isLoading;

    String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchView.setIconified(false);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!query.equals(s)) {
                    query = s;
                    searchAdapter.clearList();
                    searchPresenter.downloadMoviesFromSearch(s);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchRecyclerView.setLayoutManager(linearLayoutManager);

        searchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if (isLoading) return;
                if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    searchPresenter.downloadMoviesFromSearch(query);
                }
            }
        });

        searchRecyclerView.setAdapter(searchAdapter);

    }

    @Override
    public void showMovies(List<Movie> movies) {
        searchAdapter.addMoviesList(movies);
    }

    @Override
    public void finishLoading() {
        isLoading = false;
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        intent.putExtra("TITLE", movie.getTitle());
        startActivity(intent);
    }
}

