package com.example.irishka.movieapp.ui.movie.description.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.movie.description.PrepareDescription;
import com.example.irishka.movieapp.ui.movie.description.presenter.DescriptionPresenter;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

public class DescriptionFragment extends MvpAppCompatFragment implements DescriptionView, RelatedMoviesAdapter.OnItemClickListener {

    @Inject
    RelatedMoviesAdapter relatedMoviesAdapter;

    @Inject
    GalleryAdapter galleryAdapter;

    @Inject
    PrepareDescription prepareDescription;

    @Inject
    Provider<DescriptionPresenter> presenterProvider;

    @InjectPresenter
    DescriptionPresenter presenter;

    @ProvidePresenter
    DescriptionPresenter providePresenter() {
        return presenterProvider.get();
    }

    @BindView(R.id.related_recycler_view)
    RecyclerView relatedMovies;

    @BindView(R.id.gallery_recycler_view)
    RecyclerView gallery;

    @BindView(R.id.film_title)
    TextView filmTitle;

    @BindView(R.id.year)
    TextView year;

    @BindView(R.id.country)
    TextView country;

    @BindView(R.id.genres)
    TextView genre;

    @BindView(R.id.duration)
    TextView duration;

    @BindView(R.id.adult)
    TextView adult;

    @BindView(R.id.rate)
    TextView rate;

    @BindView(R.id.film_image)
    ImageView image;

    @BindView(R.id.overview)
    TextView overview;

    @BindView(R.id.see_also)
    TextView seeAlso;

    @BindView(R.id.ratingBar_small)
    RatingBar ratingBar;

    @Inject
    LinearLayoutManager linearLayoutManagerRelated;

//    @Inject
//    LinearLayoutManager linearLayoutManagerGallery;

    private boolean isLoading;

    public static DescriptionFragment newInstance() {
        return new DescriptionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_description, container, false);
        ButterKnife.bind(this, v);

        relatedMovies.setLayoutManager(linearLayoutManagerRelated);

        relatedMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if (isLoading) return;
                if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    presenter.downloadRelatedMovies();
                }
            }
        });

        relatedMovies.setAdapter(relatedMoviesAdapter);

        gallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        gallery.setAdapter(galleryAdapter);

        return v;
    }

    @Override
    public void finishLoading() {
        isLoading = false;
    }

    @Override
    public void showDescription(Movie movie) {

        ratingBar.setProgress((int)movie.getVoteAverage());

        filmTitle.setText(movie.getTitle());

        year.setText(prepareDescription.getYear(movie));

        prepareDescription.getPicture(movie, image);

        genre.setText(prepareDescription.getGenres(movie));

        duration.setText(prepareDescription.getDuration(movie));

        adult.setText(prepareDescription.getAdult(movie));

        rate.setText(String.valueOf(movie.getVoteAverage()));

        country.setText(prepareDescription.getCountries(movie));

        overview.setText(movie.getOverview());

        galleryAdapter.setGalleryList(movie.getBackdrops());

    }

    @Override
    public void showRelatedMovies(List<Movie> movies) {
        relatedMoviesAdapter.setRelatedList(movies);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        startActivity(intent);
    }
}
