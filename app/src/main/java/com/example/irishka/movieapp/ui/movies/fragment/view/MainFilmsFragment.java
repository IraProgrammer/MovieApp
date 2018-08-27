package com.example.irishka.movieapp.ui.movies.fragment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.MainType;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.example.irishka.movieapp.ui.movies.fragment.presenter.MainFilmsPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.irishka.movieapp.ui.movie.view.MovieActivity.TITLE;

public class MainFilmsFragment extends MvpAppCompatFragment
        implements MainFilmsView, MainFilmsAdapter.OnItemClickListener {

    public static final String MOVIE_ID = "movie_id";

    public static final String TYPE = "type";

    @Inject
    MainFilmsAdapter filmsAdapter;

    @Inject
    Provider<MainFilmsPresenter> presenterProvider;

    @InjectPresenter
    MainFilmsPresenter presenter;

    @ProvidePresenter
    MainFilmsPresenter providePresenter() {
        return presenterProvider.get();
    }

    @BindView(R.id.movies_recycler_view)
    RecyclerView moviesRecyclerView;

    @BindView(R.id.progress)
    MaterialProgressBar progressBar;

    @BindView(R.id.error)
    LinearLayout error;

    @BindView(R.id.error_btn2)
    Button errorBtn;

    @BindView(R.id.root)
    RelativeLayout root;

    Snackbar snackbar;

    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    private boolean isLoading;

    public static MainFilmsFragment newInstance(MainType tab) {

        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, tab.ordinal());

        MainFilmsFragment mainFilmsFragment = new MainFilmsFragment();
        mainFilmsFragment.setArguments(bundle);

        return mainFilmsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main_screen, container, false);
        ButterKnife.bind(this, v);

        if (!staggeredGridLayoutManager.isAttachedToWindow())
            moviesRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        moviesRecyclerView.addOnScrollListener(getOnScrollListener());

        moviesRecyclerView.setAdapter(filmsAdapter);

        errorBtn.setOnClickListener(view -> presenter.downloadMovies(false));

        return v;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        filmsAdapter.addMoviesList(movies);
    }

    @Override
    public void noInternetAndEmptyDb() {
        error.setVisibility(View.VISIBLE);
        if (snackbar != null)
            snackbar.dismiss();
    }

    @Override
    public void finishLoading() {
        isLoading = false;
    }

    private int getLastVisibleItemPosition() {
        int[] into = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
        List<Integer> intoList = new ArrayList<>();
        for (int i : into) {
            intoList.add(i);
        }
        return Collections.max(intoList);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (snackbar != null)
            snackbar.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (filmsAdapter.getItemCount() == 0)
            presenter.downloadMovies(false);
    }

    @Override
    public void showSnack() {

        if (this.isVisible()) {
            snackbar = Snackbar.make(root, getResources().getString(R.string.snack), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(getString(R.string.error_button), view -> presenter.downloadMovies(true));
            snackbar.show();
        }
    }

    @Override
    public void hideSnack() {
        if (snackbar != null)
            snackbar.dismiss();
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        intent.putExtra(TITLE, movie.getTitle());
        startActivity(intent);
    }

    private RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = getLastVisibleItemPosition();

                if (isLoading) return;
                if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    presenter.downloadMovies(true);
                }
            }
        };
    }
}
