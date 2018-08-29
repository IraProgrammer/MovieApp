package com.example.irishka.movieapp.ui.movies.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.MainType;
import com.example.irishka.movieapp.ui.filters.view.FiltersActivity;
import com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment;
import com.example.irishka.movieapp.ui.search.view.SearchActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MoviesListActivity extends DaggerAppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.main_pager)
    ViewPager viewPager;

    @BindView(R.id.btn_search)
    ImageButton btnSearch;

    @BindView(R.id.btn_filters)
    ImageButton btnFilters;

    @BindView(R.id.root)
    CoordinatorLayout root;

    @Inject
    ViewPagerAdapter adapter;

    Snackbar snackbar;

    private Map<Integer, Snackbar> snackMap = new HashMap<>();

    private OnClickListener onClickListener;

    private MainType type;

    private int currentPosition;

    private int lastPosition;

    public interface OnClickListener {
        void onClick();
        void notifyPage();
    }

    public void setOnClickListener(OnClickListener onClickListener, MainType type) {
        this.onClickListener = onClickListener;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppCompatDarkNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {

                if (onClickListener != null){
                    onClickListener.notifyPage();
                }

                if (snackMap.get(lastPosition) != null) {
                    snackMap.get(lastPosition).dismiss();
                }
                lastPosition = currentPosition;
                currentPosition = i;

                if (!isOnline() && snackMap.get(currentPosition) != null) {
                    snackMap.get(currentPosition).show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        btnSearch.setOnClickListener(view -> {
            Intent intent = new Intent(MoviesListActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        btnFilters.setOnClickListener(view -> {
            Intent intent = new Intent(MoviesListActivity.this, FiltersActivity.class);
            startActivity(intent);
        });
    }

    public void showSnack() {

        if (snackMap.get(currentPosition) == null) {
            if (!isOnline()) {
                snackbar = Snackbar.make(root, getResources().getString(R.string.snack), Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getString(R.string.error_button), view -> onClickListener.onClick());

                snackMap.put(currentPosition, snackbar);
            }

            if (type.equals(MainType.values()[currentPosition]))
                snackMap.get(currentPosition).show();

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
}
