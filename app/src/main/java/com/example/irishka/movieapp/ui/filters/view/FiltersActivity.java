package com.example.irishka.movieapp.ui.filters.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment.MOVIE_ID;
import static com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment.TITLE;

public class FiltersActivity extends MvpAppCompatActivity implements FiltersView, FiltersAdapter.OnItemClickListener {

    //  public static final String FILTERS = "FILTERS";

    @BindView(R.id.expandableLayout)
    ExpandableRelativeLayout expandableLayout;

    @BindView(R.id.mmmmm)
    TextView mmmmm;

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
                    filtersPresenter.downloadMovies(sort);
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

                filtersPresenter.downloadMovies(sort);
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
}


