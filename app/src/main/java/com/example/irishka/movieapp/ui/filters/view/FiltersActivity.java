package com.example.irishka.movieapp.ui.filters.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    private Map<String, Integer> genres;

    List<Chip> chipList = new ArrayList<>();

    @BindView(R.id.expandableLayout)
    ExpandableRelativeLayout expandableLayout;

    @BindView(R.id.mmmmm)
    TextView mmmmm;

    @BindView(R.id.chipGroup)
    ChipGroup chipGroup;

    @BindView(R.id.btn_home)
    ImageButton btnHome;

    @BindView(R.id.btn_ok)
    ImageButton btnOk;

    @BindView(R.id.sortRadioGroup)
    RadioGroup radioGroupSort;

    @BindView(R.id.rb_releaseDate)
    RadioButton radio1;

    @BindView(R.id.rb_voteAverage)
    RadioButton radio2;

    @BindView(R.id.rb_revenue)
    RadioButton radio3;

    @BindView(R.id.rb_popularity)
    RadioButton radio4;

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

    private String sort = "";

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
                    filtersPresenter.downloadForScroll(sort, genresStr);
                }
            }
        });

        filtersRecyclerView.setAdapter(filtersAdapter);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (radioGroupSort.getCheckedRadioButtonId()) {
                    case R.id.rb_popularity:
                        sort = "popularity.desc";
                        break;
                    case R.id.rb_revenue:
                        sort = "release_date.desc";
                        break;
                    case R.id.rb_releaseDate:
                        sort = "revenue.desc";
                        break;
                    case R.id.rb_voteAverage:
                        sort = "vote_average.desc";
                        break;
                    default:
                        break;
                }

                filteredWithGenres();
                filtersPresenter.downloadMovies(sort, genresStr);
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

        addChips();

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

    private Map<String, Integer> createGenresMap() {

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < genreIds.length; i++) {
            map.put(genreNames[i], genreIds[i]);
        }
        return map;
    }

    private void addChips() {

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                Color.BLACK,
                Color.RED,
                Color.GREEN,
                Color.BLUE
        };

        ColorStateList myList = new ColorStateList(states, colors);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < genreNames.length; i++) {
            Chip chip = new Chip(this);
            chip.setFocusable(true);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setLayoutParams(layoutParams);
            chip.setText(genreNames[i]);
            chip.setChipCornerRadius(16);
            chip.setTextColor(getResources().getColor(R.color.white));
         //   chip.setChipBackgroundColor(myList);

            chip.setRippleColorResource(R.color.colorAccent);
            chip.setChipStrokeColorResource(R.color.light_gray);

            chipList.add(chip);

            chipGroup.addView(chip);
        }
    }

    private void filteredWithGenres() {

        for (int i = 0; i < chipList.size(); i++) {
            if (chipList.get(i).isChecked()) {
                genresStr += genres.get(chipList.get(i).getText()) + ",";
            }
        }
    }

}


