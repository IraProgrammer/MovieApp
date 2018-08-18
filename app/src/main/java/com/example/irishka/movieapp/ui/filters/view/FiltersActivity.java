package com.example.irishka.movieapp.ui.filters.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.filters.presenter.FiltersPresenter;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment.MOVIE_ID;
import static com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment.TITLE;

public class FiltersActivity extends MvpAppCompatActivity implements FiltersView, FiltersAdapter.OnItemClickListener {

    private String[] genreNames = new String[]{"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary",
            "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Mystery", "Romance", "Science Fiction",
            "TV Movie", "Thriller", "War", "Western"};

    private int[] genreIds = new int[]{28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10770,
            53, 10752, 37};

    final boolean[] flagsForGenres = {true, true, true, true, true, true, true, true,
            true, true, true, true, true, true, true, true, true, true, true};

    private String[] sortTypes = new String[]{"Popularity", "Release date", "Revenue", "Vote average"};

    private String[] sortQueries = new String[]{"popularity.desc", "release_date.desc", "revenue.desc", "vote_average.desc"};

    final boolean[] flagsForSort = {true, true, true, true};

    private Map<String, Integer> genres;

    private Map<String, String> sorts;

    List<Chip> genresChipList = new ArrayList<>();

    List<Chip> sortChipList = new ArrayList<>();

    @BindView(R.id.expandableLayout)
    ExpandableRelativeLayout expandableLayout;

    @BindView(R.id.mmmmm)
    TextView mmmmm;

    @BindView(R.id.genres_chipGroup)
    ChipGroup genresChipGroup;

    @BindView(R.id.sort_chipGroup)
    ChipGroup sortChipGroup;

    @BindView(R.id.btn_home)
    ImageButton btnHome;

    @BindView(R.id.btn_ok)
    ImageButton btnOk;

    @BindView(R.id.filters_recycler_view)
    RecyclerView filtersRecyclerView;

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

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        filtersRecyclerView.setLayoutManager(linearLayoutManager);

        filtersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if (isLoading) return;
                if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    filtersPresenter.downloadForScroll(sortStr, genresStr);
                }
            }
        });

        filtersRecyclerView.setAdapter(filtersAdapter);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filteredWithSort();
                filteredWithGenres();
                filtersPresenter.downloadMovies(sortStr, genresStr);
            }
        });

        mmmmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableLayout.isExpanded())
                    expandableLayout.collapse();
                else expandableLayout.expand();
            }
        });

        genres = createGenresMap();
        sorts = createSortsMap();

        sortChipList = createChipList(sortTypes);
        genresChipList = createChipList(genreNames);

        addAllChips(sortChipList, sortChipGroup);
        addAllChips(genresChipList, genresChipGroup);

    }

    @Override
    public void showMovies(List<Movie> movies, boolean isFiltred) {
        filtersAdapter.addMoviesList(movies, isFiltred);
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

    private Map<String, Integer> createGenresMap() {

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < genreIds.length; i++) {
            map.put(genreNames[i], genreIds[i]);
        }
        return map;
    }

    private Map<String, String> createSortsMap() {

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < sortTypes.length; i++) {
            map.put(sortTypes[i], sortQueries[i]);
        }

        return map;
    }

    private List<Chip> createChipList(String[] titles) {

        List<Chip> chips = new ArrayList<>();

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked}
        };

        int[] colors = new int[]{
                getResources().getColor(R.color.accent_material_dark_1),
                getResources().getColor(R.color.holo_primary_dark)
        };

        ColorStateList myList = new ColorStateList(states, colors);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 0, 8);

        for (int i = 0; i < titles.length; i++) {
            Chip chip = new Chip(this);
            chip.setFocusable(true);


               // chip.setSelected(true);


            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setLayoutParams(layoutParams);
            chip.setText(titles[i]);
            chip.setChipCornerRadius(32);
            chip.setCheckedIcon(null);
            chip.setTextColor(getResources().getColor(R.color.white));
            chip.setChipBackgroundColor(myList);

            int finalI = i;

            if (titles[0].equals("Action")) {
                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chip.setChecked(flagsForGenres[finalI]);
                        flagsForGenres[finalI] = !flagsForGenres[finalI];
                    }
                });
            }

                else {
                    chip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                chip.setChecked(flagsForSort[finalI]);
                                flagsForGenres[finalI] = !flagsForGenres[finalI];

                                if (chip.isChecked()) {

                                    for (int i = 0; i < flagsForSort.length; i++) {
                                        if (i != finalI) {
                                            flagsForSort[i] = true;
                                            sortChipList.get(i).setChecked(false);
                                        }
                                    }
                                }
                            }
                    });
                }

            chips.add(chip);
        }

        return chips;
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

}


