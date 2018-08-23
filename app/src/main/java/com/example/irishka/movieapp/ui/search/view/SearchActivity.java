package com.example.irishka.movieapp.ui.search.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.search.ExampleAdapter;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.example.irishka.movieapp.ui.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.irishka.movieapp.ui.movie.view.MovieActivity.TITLE;
import static com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment.MOVIE_ID;

public class SearchActivity extends MvpAppCompatActivity implements com.example.irishka.movieapp.ui.search.view.SearchView,
        com.example.irishka.movieapp.ui.search.view.SearchAdapter.OnItemClickListener {

    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.btn_home)
    ImageButton btnHome;

    @BindView(R.id.search_recycler_view)
    RecyclerView searchRecyclerView;

    @BindView(R.id.progress)
    MaterialProgressBar progressBar;

//    @BindView(R.id.error)
//    LinearLayout error;

    @BindView(R.id.error_btn)
    Button errorBtn;

    @BindView(R.id.sorry)
    TextView sorry;

    @Inject
    SearchManager manager;

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

    ExampleAdapter exampleAdapter;

    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchView.setIconified(false);

        btnHome.setOnClickListener(view -> finish());

        searchRecyclerView.setLayoutManager(linearLayoutManager);

        searchRecyclerView.addOnScrollListener(getOnScrollListener());

        searchRecyclerView.setAdapter(searchAdapter);

        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(getOnQueryTextListener());

        searchView.setOnSuggestionListener(getOnSuggestionListener());

        errorBtn.setOnClickListener(view -> searchPresenter.downloadMoviesFromSearch(query, false));

    }

    @Override
    public void load(String query, List<String> items) {

        String[] columns = new String[]{"_id", "text"};
        Object[] temp = new Object[]{0, "default"};

        MatrixCursor cursor = new MatrixCursor(columns);

        for (int i = 0; i < items.size(); i++) {
            temp[0] = i;
            temp[1] = items.get(i);
            cursor.addRow(temp);
        }

        this.items = new ArrayList<>();
        this.items.addAll(items);

        exampleAdapter = new ExampleAdapter(this, cursor, items);

        searchView.setSuggestionsAdapter(exampleAdapter);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        sorry.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void noFound() {
        sorry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSnack() {
        Snackbar snackbar = Snackbar.make(root, getResources().getString(R.string.snack), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.error_button), view -> searchPresenter.downloadMoviesFromSearch(query, true));
        snackbar.show();
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
        intent.putExtra(TITLE, movie.getTitle());
        startActivity(intent);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                    if (isLoading) return;
                    if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                            && lastVisibleItemPosition >= 0) {
                        isLoading = true;
                        searchPresenter.downloadMoviesFromSearch(query, true);
                    }
            }
        };
    }

    private SearchView.OnQueryTextListener getOnQueryTextListener() {
        return new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                query = s;
                searchAdapter.clearList();
                searchPresenter.downloadMoviesFromSearch(s, false);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                if (query.length() == 0) {
                    searchPresenter.downloadKeywordsFromDb();
                } else {
                    searchPresenter.downloadKeywords(query);
                }
                return true;
            }

        };
    }

    private SearchView.OnSuggestionListener getOnSuggestionListener() {
        return new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {

                searchView.setQuery(items.get(i), true);
                return true;
            }
        };
    }
}

