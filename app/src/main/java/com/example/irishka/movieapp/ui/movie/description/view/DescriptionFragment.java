package com.example.irishka.movieapp.ui.movie.description.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.slideGallery.ImagePagerActivity;
import com.example.irishka.movieapp.ui.movie.description.PrepareDescription;
import com.example.irishka.movieapp.ui.movie.description.presenter.DescriptionPresenter;
import com.example.irishka.movieapp.ui.movie.di.qualifiers.Gallery;
import com.example.irishka.movieapp.ui.movie.di.qualifiers.Related;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.example.irishka.movieapp.ui.movie.view.MovieActivity.TITLE;
import static com.example.irishka.movieapp.ui.movies.fragment.MainFilmsFragment.MOVIE_ID;

public class DescriptionFragment extends MvpAppCompatFragment
        implements DescriptionView, RelatedMoviesAdapter.OnItemClickListener, GalleryAdapter.OnItemClickListener {

    public static final String ARRAY_LIST = "ARRAYLIST";

    public static final String POSITION = "POSITION";

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

    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;

    @Inject
    @Related
    LinearLayoutManager linearLayoutManagerRelated;

    @Inject
    @Gallery
    LinearLayoutManager linearLayoutManagerGallery;

    RecyclerView.LayoutManager layoutManager;

    View viewAtPosition;

    private View[] sharedViews;

    private boolean isLoading;

    private int currentPosition = 0;

    // ImageView galleryItem;

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

        //   galleryItem = v.findViewById(R.id.backdrop_image);

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

        gallery.setLayoutManager(linearLayoutManagerGallery);
        gallery.setAdapter(galleryAdapter);

        return v;
    }

    @Override
    public void finishLoading() {
        isLoading = false;
    }

    @Override
    public void showDescription(Movie movie) {

        ratingBar.setProgress((int) movie.getVoteAverage());

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

        prepareDescription.initializeYouTubePlayer(movie, youTubePlayerView);

    }

    @Override
    public void showRelatedMovies(List<Movie> movies) {
        relatedMoviesAdapter.setRelatedList(movies);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        intent.putExtra(TITLE, movie.getTitle());
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position, View item, ImageView image) {
        Intent intent = new Intent(getContext(), ImagePagerActivity.class);
        intent.putExtra(ARRAY_LIST, (ArrayList<Image>) galleryAdapter.getGalleryList());
        intent.putExtra(POSITION, position);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                new Pair<View, String>(image, getString(R.string.transition_name))
        );

        startActivityForResult(intent, 1, options.toBundle());

        //  postponeEnterTransition();

        //      startActivity(intent, options.toBundle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode != Activity.RESULT_OK) {
        //  return;
        //}
        if (data != null) {
            currentPosition = data.getIntExtra("CUR", 7);
        }

        layoutManager = gallery.getLayoutManager();



//        viewAtPosition = layoutManager.findViewByPosition(currentPosition);
//
//        if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true))
//            gallery.scrollToPosition(currentPosition);
//         //   startPostponedEnterTransition();
//        }
    }
}
