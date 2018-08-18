package com.example.irishka.movieapp.ui.filters.di;

import android.content.res.ColorStateList;
import android.support.design.chip.Chip;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.filters.di.qualifiers.Genres;
import com.example.irishka.movieapp.ui.filters.di.qualifiers.Sort;
import com.example.irishka.movieapp.ui.filters.view.FiltersActivity;
import com.example.irishka.movieapp.ui.filters.view.FiltersAdapter;
import com.example.irishka.movieapp.ui.search.view.SearchActivity;
import com.example.irishka.movieapp.ui.search.view.SearchAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dagger.Module;
import dagger.Provides;

@Module
public class FiltersActivityModule {

    private static String[] genreNames = new String[]{"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary",
            "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Mystery", "Romance", "Science Fiction",
            "TV Movie", "Thriller", "War", "Western"};

    private static int[] genreIds = new int[]{28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10770,
            53, 10752, 37};

    private static String[] sortTypes = new String[]{"Popularity", "Release date", "Revenue", "Vote average"};

    private static String[] sortQueries = new String[]{"popularity.desc", "release_date.desc", "revenue.desc", "vote_average.desc"};

    @Provides
    @PerActivity
    static FiltersAdapter providesFiltersAdapter(FiltersActivity filtersActivity, GlideHelper glideHelper) {
        return new FiltersAdapter(filtersActivity, glideHelper);
    }

    @Provides
    @PerActivity
    static LinearLayoutManager providesLinearLayoutManager(FiltersActivity filtersActivity){
        return new LinearLayoutManager(filtersActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    @PerActivity
    static Map<String, Integer> providesGenresMap(){
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < genreIds.length; i++) {
            map.put(genreNames[i], genreIds[i]);
        }
        return map;
    }

    @Provides
    @PerActivity
    static Map<String, String> providesSortMap(){

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < sortTypes.length; i++) {
            map.put(sortTypes[i], sortQueries[i]);
        }
        return map;
    }

    @Provides
    @PerActivity
    @Genres
    static List<Chip> providesGenresChipList(FiltersActivity filtersActivity){

        List<Chip> chips = new ArrayList<>();

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked}
        };

        int[] colors = new int[]{
                filtersActivity.getResources().getColor(R.color.accent_material_dark_1),
                filtersActivity.getResources().getColor(R.color.holo_primary_dark)
        };

        ColorStateList myList = new ColorStateList(states, colors);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 0, 8);

        for (int i = 0; i < genreNames.length; i++) {
            Chip chip = new Chip(filtersActivity);
            chip.setFocusable(true);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setLayoutParams(layoutParams);
            chip.setText(genreNames[i]);
            chip.setChipCornerRadius(32);
            chip.setCheckedIcon(null);
            chip.setTextColor(filtersActivity.getResources().getColor(R.color.white));
            chip.setChipBackgroundColor(myList);

            chips.add(chip);
        }

        return chips;
    }


    @Provides
    @PerActivity
    @Sort
    static List<Chip> providesSortChipList(FiltersActivity filtersActivity){

        List<Chip> chips = new ArrayList<>();

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_checked}
        };

        int[] colors = new int[]{
                filtersActivity.getResources().getColor(R.color.accent_material_dark_1),
                filtersActivity.getResources().getColor(R.color.holo_primary_dark)
        };

        ColorStateList myList = new ColorStateList(states, colors);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 0, 8);

        for (int i = 0; i < sortTypes.length; i++) {
            Chip chip = new Chip(filtersActivity);
            chip.setFocusable(true);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setLayoutParams(layoutParams);
            chip.setText(sortTypes[i]);
            chip.setChipCornerRadius(32);
            chip.setCheckedIcon(null);
            chip.setTextColor(filtersActivity.getResources().getColor(R.color.white));
            chip.setChipBackgroundColor(myList);

            chips.add(chip);
        }

        return chips;
    }

}
