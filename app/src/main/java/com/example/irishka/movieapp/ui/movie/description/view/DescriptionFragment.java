package com.example.irishka.movieapp.ui.movie.description.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

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

    @BindView(R.id.gallery)
    TextView galleryTextView;

    @BindView(R.id.see_also)
    TextView seeAlso;

    @BindView(R.id.ratingBar_small)
    RatingBar ratingBar;

    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;

    @BindView(R.id.progressBar)
    MaterialProgressBar progress;

    @BindView(R.id.video_card)
    CardView videoCard;

    @BindView(R.id.sorry)
    TextView sorry;

    @BindView(R.id.error)
    LinearLayout error;

    @BindView(R.id.error_btn)
    Button errorBtn;

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
                if (isOnline()) {
                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                    if (isLoading) return;
                    if ((totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + 20)
                            && lastVisibleItemPosition >= 0) {
                        isLoading = true;
                        presenter.downloadRelatedMovies(true);
                    }
                }
            }
        });

        relatedMovies.setAdapter(relatedMoviesAdapter);

        gallery.setLayoutManager(linearLayoutManagerGallery);
        gallery.setAdapter(galleryAdapter);

        errorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.downloadDescription();
                presenter.downloadRelatedMovies(false);
                if (isOnline()) {
                    error.setVisibility(View.GONE);
                }
            }
        });

        return v;
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);

        relatedMovies.setVisibility(View.VISIBLE);
        if (relatedMovies.getAdapter().getItemCount() != 0) {
            seeAlso.setVisibility(View.VISIBLE);
        }

        if (!isOnline()) {
            error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finishLoading() {
        isLoading = false;
    }

    @Override
    public void onDownloadError() {
        ratingBar.setVisibility(View.GONE);
        galleryTextView.setVisibility(View.GONE);
        videoCard.setVisibility(View.GONE);
        seeAlso.setVisibility(View.GONE);
        sorry.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(Movie movie) {

        if (movie.getVoteAverage() > 0) {
            ratingBar.setProgress((int) movie.getVoteAverage());
            ratingBar.setVisibility(View.VISIBLE);
        } else {
            ratingBar.setProgress(0);
            ratingBar.setVisibility(View.VISIBLE);
        }

        filmTitle.setText(movie.getTitle());

        year.setText(prepareDescription.getYear(movie));
        prepareDescription.getPicture(movie, image);
        genre.setText(prepareDescription.getGenres(movie));
        duration.setText(prepareDescription.getDuration(movie));
        adult.setText(prepareDescription.getAdult(movie));
        rate.setText(String.valueOf(movie.getVoteAverageStr()));
        country.setText(prepareDescription.getCountries(movie));
        overview.setText(movie.getOverview());

        if (movie.getBackdrops().size() > 0) {
            galleryAdapter.setGalleryList(movie.getBackdrops());
            galleryTextView.setVisibility(View.VISIBLE);
        }

        if (movie.getTrailer() != null && isOnline()) {
            prepareDescription.initializeYouTubePlayer(movie, youTubePlayerView);
            videoCard.setVisibility(View.VISIBLE);
        } else {
            videoCard.setVisibility(View.GONE);
        }

    }

    @Override
    public void showRelatedMovies(List<Movie> movies) {

        if (!isOnline() && movies.size() != 0) {
            seeAlso.setVisibility(View.VISIBLE);
        }

        relatedMoviesAdapter.setRelatedList(movies);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        intent.putExtra(TITLE, movie.getTitle());
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position, View item, ImageView image) {
        Intent intent = new Intent(getContext(), ImagePagerActivity.class);
        intent.putExtra(ARRAY_LIST, (ArrayList<Image>) galleryAdapter.getGalleryList());
        intent.putExtra(POSITION, position);

        image.setTransitionName(getString(R.string.transition_name).concat(String.valueOf(position)));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                new Pair<View, String>(image, image.getTransitionName())
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

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
