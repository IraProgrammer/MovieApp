package com.example.irishka.movieapp.ui.filters.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.filters.di.qualifiers.Genres;
import com.example.irishka.movieapp.ui.filters.di.qualifiers.Sort;
import com.example.irishka.movieapp.ui.filters.presenter.FiltersPresenter;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.irishka.movieapp.ui.movie.view.MovieActivity.TITLE;
import static com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment.MOVIE_ID;

public class FiltersActivity extends MvpAppCompatActivity implements FiltersView, FiltersAdapter.OnItemClickListener {

    @Inject
    Map<String, Integer> genres;

    @Inject
    Map<String, String> sorts;

    @Inject
    @Genres
    List<Chip> genresChipList;

    @Inject
    @Sort
    List<Chip> sortChipList;

    @BindView(R.id.root)
    CoordinatorLayout root;

    @BindView(R.id.expandableLayout)
    ExpandableRelativeLayout expandableLayout;

    @BindView(R.id.layout_filters)
    LinearLayout layoutFilters;

    @BindView(R.id.genres_chipGroup)
    ChipGroup genresChipGroup;

    @BindView(R.id.sort_chipGroup)
    ChipGroup sortChipGroup;

    @BindView(R.id.btn_home)
    ImageButton btnHome;

    @BindView(R.id.btn_ok)
    ImageButton btnOk;

    @BindView(R.id.btn_expand)
    ImageButton btnExpand;

    @BindView(R.id.filters_recycler_view)
    RecyclerView filtersRecyclerView;

    @BindView(R.id.appBar)
    BottomAppBar bottomAppBar;

    @BindView(R.id.progressBar)
    MaterialProgressBar progress;

    @BindView(R.id.sorry)
    TextView sorry;

    @BindView(R.id.error)
    LinearLayout error;

    @BindView(R.id.error_btn)
    Button errorBtn;

    @Inject
    Provider<FiltersPresenter> filtersPresenterProvider;

    @InjectPresenter
    FiltersPresenter filtersPresenter;

    @ProvidePresenter
    FiltersPresenter providePresenter() {
        return filtersPresenterProvider.get();
    }

    @Inject
    FiltersAdapter filtersAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    private boolean isLoading;

    private String sortStr = "";

    private String genresStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        ButterKnife.bind(this);

        btnHome.setOnClickListener(view -> finish());

        filtersRecyclerView.setLayoutManager(linearLayoutManager);

        filtersRecyclerView.addOnScrollListener(getOnScrollListener());

        filtersRecyclerView.setAdapter(filtersAdapter);

        btnOk.setOnClickListener(view -> {

            btnExpand.setVisibility(View.VISIBLE);
            bottomAppBar.setVisibility(View.GONE);
            expandableLayout.collapse();
            filteredWithSort();
            filteredWithGenres();
            filtersPresenter.downloadMovies(sortStr, genresStr, false);
        });

        layoutFilters.setOnClickListener(getClickListener());
        btnExpand.setOnClickListener(getClickListener());

        addAllChips(sortChipList, sortChipGroup);
        addAllChips(genresChipList, genresChipGroup);
        setSortListeners();
        setGenresListeners();

        errorBtn.setOnClickListener(view -> filtersPresenter.downloadMovies(sortStr, genresStr, false));

    }

    @Override
    public void showProgress() {
        sorry.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void noFound() {
        sorry.setVisibility(View.VISIBLE);
    }

    @Override
    public void noInternet() {
        progress.setVisibility(View.GONE);

        if (!isOnline())
            error.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearList() {
        filtersAdapter.clearList();
    }

    @Override
    public void showMovies(List<Movie> movies) {
        filtersAdapter.addMoviesList(movies);
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
    }

    private void addAllChips(List<Chip> chips, ChipGroup chipGroup) {
        for (Chip chip : chips) {
            chipGroup.addView(chip);
        }
    }

    private void filteredWithGenres() {

        genresStr = "";

        for (int i = 0; i < genresChipList.size(); i++) {
            if (genresChipList.get(i).isChecked()) {
                genresStr += genres.get(genresChipList.get(i).getText()) + ",";
            }
        }
    }

    private void filteredWithSort() {

        for (int i = 0; i < sortChipList.size(); i++) {
            if (sortChipList.get(i).isChecked()) {
                sortStr = sorts.get(sortChipList.get(i).getText());
            }
        }
    }

    private void setSortListeners() {
        for (int i = 0; i < sortChipList.size(); i++) {
            int finalI = i;
            sortChipList.get(i).setOnClickListener(view -> {
                sortChipList.get(finalI).setChecked(sortChipList.get(finalI).isChecked());

                if (sortChipList.get(finalI).isChecked()) {

                    sortChipList.get(finalI).setTextColor(getResources().getColor(R.color.black));

                    for (int j = 0; j < sortChipList.size(); j++) {
                        if (!sortChipList.get(finalI).equals(sortChipList.get(j))) {
                            sortChipList.get(j).setChecked(false);
                            sortChipList.get(j).setTextColor(getResources().getColor(R.color.white));
                        }
                    }
                } else {
                    sortChipList.get(finalI).setTextColor(getResources().getColor(R.color.white));
                }
            });
        }
    }

    private void setGenresListeners() {
        for (int i = 0; i < genresChipList.size(); i++) {
            int finalI = i;
            genresChipList.get(i).setOnClickListener(view -> {
                genresChipList.get(finalI).setChecked(genresChipList.get(finalI).isChecked());

                if (genresChipList.get(finalI).isChecked()) {
                    genresChipList.get(finalI).setTextColor(getResources().getColor(R.color.black));
                } else {
                    genresChipList.get(finalI).setTextColor(getResources().getColor(R.color.white));
                }
            });
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private RecyclerView.OnScrollListener getOnScrollListener(){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if (isOnline()) {
                    if (isLoading) return;
                    if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                            && lastVisibleItemPosition >= 0) {
                        isLoading = true;
                        filtersPresenter.downloadMovies(sortStr, genresStr, true);
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(root, getResources().getString(R.string.snack), Snackbar.LENGTH_LONG);
                    if (totalItemCount == lastVisibleItemPosition + 1) {
                        snackbar.show();
                    }
                }
            }
        };
    }

    private View.OnClickListener getClickListener(){
        return view -> {
            expandableLayout.expand();
            btnExpand.setVisibility(View.GONE);
            bottomAppBar.setVisibility(View.VISIBLE);
        };
    }
}


