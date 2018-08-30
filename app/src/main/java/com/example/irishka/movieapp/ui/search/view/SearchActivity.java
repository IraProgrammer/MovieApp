package com.example.irishka.movieapp.ui.search.view;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.MatrixCursor;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.irishka.movieapp.ui.movie.view.MovieActivity.TITLE;
import static com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment.MOVIE_ID;

public class SearchActivity extends MvpAppCompatActivity implements com.example.irishka.movieapp.ui.search.view.SearchView,
        com.example.irishka.movieapp.ui.search.view.SearchAdapter.OnItemClickListener {

    public static final String IS_SEARCH = "is_search";

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

    @BindView(R.id.error_btn)
    Button errorBtn;

    @BindView(R.id.tv_sorry)
    TextView sorryTv;

    @BindView(R.id.list_suggestions)
    ListView lvSuggestions;

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

    Snackbar snackbar;

    private boolean isLoading = true;

    String query = "";

    ArrayAdapter<String> adapter;

    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(false);

        btnHome.setOnClickListener(view -> finish());

        searchRecyclerView.setLayoutManager(linearLayoutManager);

        searchRecyclerView.addOnScrollListener(getOnScrollListener());

        searchRecyclerView.setAdapter(searchAdapter);

        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        errorBtn.setOnClickListener(view -> searchPresenter.downloadMoviesFromSearch(query, false));

        adapter = new ArrayAdapter<String>(this, R.layout.search, items);

        lvSuggestions.setAdapter(adapter);

        root.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int heightDiff = root.getRootView().getHeight() - root.getHeight();
            if (heightDiff > dpToPx(SearchActivity.this, 200)) { // if more than 200 dp, it's probably a keyboard...
                lvSuggestions.setVisibility(View.VISIBLE);
            } else lvSuggestions.setVisibility(View.GONE);
        });

        lvSuggestions.setOnItemClickListener((adapterView, view, i, l) -> {
            searchView.setQuery(items.get(i), true);
            lvSuggestions.setVisibility(View.GONE);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        });

        RxSearchView.queryTextChangeEvents(searchView)
                .debounce(300, TimeUnit.MILLISECONDS) // stream will go down after 1 second inactivity of user
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchViewQueryTextEvent -> {
                    if (searchViewQueryTextEvent.isSubmitted()) {
                        if (!query.equals(searchViewQueryTextEvent.queryText().toString())) {
                            query = searchViewQueryTextEvent.queryText().toString();
                            searchAdapter.clearList();
                            searchPresenter.downloadMoviesFromSearch(searchViewQueryTextEvent.queryText().toString(), false);

                            searchView.setFocusable(false);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(searchView.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                            searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
                        }
                    } else {
                        if (searchViewQueryTextEvent.queryText().toString().length() == 0) {
                            searchPresenter.downloadKeywordsFromDb();
                        } else {
                            searchPresenter.downloadKeywords(searchViewQueryTextEvent.queryText().toString());
                        }
                    }
                });
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public void load(String query, List<String> items) {

        adapter.clear();
        adapter.addAll(items);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        sorryTv.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void noFound() {
        sorryTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSnack() {
        snackbar = Snackbar.make(root, getResources().getString(R.string.snack), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.error_button), view -> searchPresenter.downloadMoviesFromSearch(query, true));
        snackbar.show();
    }

    @Override
    public void hideSnack() {
        if (snackbar != null) snackbar.dismiss();
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
        intent.putExtra(IS_SEARCH, true);
        intent.putExtra(TITLE, movie.getTitle());
        startActivity(intent);
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
}

