package com.example.irishka.movieapp.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.model.DatabaseManager;
import com.example.irishka.movieapp.model.Pojo.ConcreteMovie;
import com.example.irishka.movieapp.presenter.MoviesPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends MvpAppCompatActivity implements MoviesView {

    @BindView(R.id.movies_recycler_view)
    RecyclerView moviesRecyclerView;

    @InjectPresenter
    MoviesPresenter moviesPresenter;

    MoviesAdapter moviesAdapter;

    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        //moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, columns));

        int orientation;
        int number;
        float scalefactor;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            orientation = StaggeredGridLayoutManager.VERTICAL;
            number = getWindowManager().getDefaultDisplay().getWidth();
            scalefactor = getResources().getDisplayMetrics().density * 150;
        }

        else {
            orientation = StaggeredGridLayoutManager.HORIZONTAL;
            number = getWindowManager().getDefaultDisplay().getHeight();
            scalefactor = getResources().getDisplayMetrics().density * 200;
        }

        int columns = (int) ((float) number / (float) scalefactor);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columns, orientation);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        moviesRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        moviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = getLastVisibleItemPosition();

                if (isLoading) return;
// всего 70 эл. видимых 5.  65.
                if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    moviesPresenter.onDownloadMovies();
                }
            }
        });

        moviesAdapter = new MoviesAdapter();
        moviesRecyclerView.setAdapter(moviesAdapter);

    }

    @Override
    public void showMovies(List<ConcreteMovie> movies) {
        isLoading = false;
        moviesAdapter.setMoviesList(movies);
    }

    @Override
    protected void onStop() {
        super.onStop();
        moviesPresenter.onStop();
    }

    @Override
    public void initDatabase() {
        DatabaseManager.getInstance().init(this);
    }

    private int getLastVisibleItemPosition(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) moviesRecyclerView.getLayoutManager();
        int[] into = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
        List<Integer> intoList = new ArrayList<>();
        for (int i : into) {
            intoList.add(i);
        }
        return Collections.max(intoList);
    }
}
