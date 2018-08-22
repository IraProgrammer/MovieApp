package com.example.irishka.movieapp.ui.filters;

import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.app.AppCompatActivity;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.ui.filters.di.qualifiers.Genres;
import com.example.irishka.movieapp.ui.filters.di.qualifiers.Sort;
import com.example.irishka.movieapp.ui.filters.view.FiltersActivity;

import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

import javax.inject.Inject;

public class ChipsHelper {

    private Map<String, Integer> genres;

    private Map<String, String> sorts;

    private List<Chip> genresChipList;

    private List<Chip> sortChipList;

    private AppCompatActivity filtersActivity;

    @Inject
    public ChipsHelper(FiltersActivity filtersActivity, List<Chip> genresChipList, List<Chip> sortChipList,
                       Map<String, Integer> genres, Map<String, String> sorts) {
        this.filtersActivity = filtersActivity;
        this.genresChipList = genresChipList;
        this.sortChipList = sortChipList;
        this.genres = genres;
        this.sorts = sorts;
    }

    public void addAllSortChips(ChipGroup chipGroup) {
        for (Chip chip : sortChipList) {
            chipGroup.addView(chip);
        }
    }

    public void addAllGenresChips(ChipGroup chipGroup) {
        for (Chip chip : genresChipList) {
            chipGroup.addView(chip);
        }
    }

    public void setSortListeners() {
        for (int i = 0; i < sortChipList.size(); i++) {
            int finalI = i;
            sortChipList.get(i).setOnClickListener(view -> {
                sortChipList.get(finalI).setChecked(sortChipList.get(finalI).isChecked());

                if (sortChipList.get(finalI).isChecked()) {

                    sortChipList.get(finalI).setTextColor(filtersActivity.getResources().getColor(R.color.black));

                    for (int j = 0; j < sortChipList.size(); j++) {
                        if (!sortChipList.get(finalI).equals(sortChipList.get(j))) {
                            sortChipList.get(j).setChecked(false);
                            sortChipList.get(j).setTextColor(filtersActivity.getResources().getColor(R.color.white));
                        }
                    }
                } else {
                    sortChipList.get(finalI).setTextColor(filtersActivity.getResources().getColor(R.color.white));
                }
            });
        }
    }

    public void setGenresListeners() {
        for (int i = 0; i < genresChipList.size(); i++) {
            int finalI = i;
            genresChipList.get(i).setOnClickListener(view -> {
                genresChipList.get(finalI).setChecked(genresChipList.get(finalI).isChecked());

                if (genresChipList.get(finalI).isChecked()) {
                    genresChipList.get(finalI).setTextColor(filtersActivity.getResources().getColor(R.color.black));
                } else {
                    genresChipList.get(finalI).setTextColor(filtersActivity.getResources().getColor(R.color.white));
                }
            });
        }
    }

    public String filteredWithGenres() {

        StringBuilder genresBuilder = new StringBuilder();

        for (int i = 0; i < genresChipList.size(); i++) {
            if (genresChipList.get(i).isChecked()) {
                genresBuilder.append(genres.get(genresChipList.get(i).getText())).append(",");
            }
        }

        return genresBuilder.toString();

    }

    public String filteredWithSort() {

        for (int i = 0; i < sortChipList.size(); i++) {
            if (sortChipList.get(i).isChecked()) {
                return sorts.get(sortChipList.get(i).getText());
            }
        }
        return "";
    }
}
