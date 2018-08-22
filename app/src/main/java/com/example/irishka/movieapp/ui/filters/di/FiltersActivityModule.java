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
    static Map<String, Integer> providesGenresMap(FiltersActivity filtersActivity){

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < providesGenresNamesArray(filtersActivity).length; i++) {
            map.put(providesGenresNamesArray(filtersActivity)[i], providesGenresIdsArray(filtersActivity)[i]);
        }
        return map;
    }

    @Provides
    @PerActivity
    static Map<String, String> providesSortMap(FiltersActivity filtersActivity){

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < providesSortTypesArray(filtersActivity).length; i++) {
            map.put(providesSortTypesArray(filtersActivity)[i], providesSortQueriesArray(filtersActivity)[i]);
        }
        return map;
    }

    private static String[] providesGenresNamesArray(FiltersActivity filtersActivity){
        return filtersActivity.getResources().getStringArray(R.array.genresNames);
    }

    private static String[] providesSortTypesArray(FiltersActivity filtersActivity){
        return filtersActivity.getResources().getStringArray(R.array.sortTypes);
    }

    private static int[] providesGenresIdsArray(FiltersActivity filtersActivity){
        return filtersActivity.getResources().getIntArray(R.array.genreIds);
    }

    private static String[] providesSortQueriesArray(FiltersActivity filtersActivity){
        return filtersActivity.getResources().getStringArray(R.array.sortQueries);
    }

    private static List<Chip> createChipList(FiltersActivity filtersActivity, String[] array){

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

        for (int i = 0; i < array.length; i++) {
            Chip chip = new Chip(filtersActivity);
            chip.setFocusable(true);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setLayoutParams(layoutParams);
            chip.setText(array[i]);
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
        return createChipList(filtersActivity, providesSortTypesArray(filtersActivity));
    }

    @Provides
    @PerActivity
    @Genres
    static List<Chip> providesGenresChipList(FiltersActivity filtersActivity){
        return createChipList(filtersActivity, providesGenresNamesArray(filtersActivity));
    }

}
