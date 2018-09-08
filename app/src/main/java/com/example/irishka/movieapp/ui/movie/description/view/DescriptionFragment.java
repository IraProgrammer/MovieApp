package com.example.irishka.movieapp.ui.movie.description.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static android.app.Activity.RESULT_OK;
import static com.example.irishka.movieapp.ui.movie.view.MovieActivity.TITLE;
import static com.example.irishka.movieapp.ui.movies.fragment.view.MainFilmsFragment.MOVIE_ID;
import static com.example.irishka.movieapp.ui.slideGallery.ImagePagerActivity.CURRENT;

public class DescriptionFragment extends MvpAppCompatFragment
        implements DescriptionView, RelatedMoviesAdapter.OnItemClickListener, GalleryAdapter.OnItemClickListener, MovieActivity.OnBackPressedListener {

    public static final String ARRAY_LIST = "ARRAYLIST";

    public static final String POSITION = "POSITION";

    private static final int REQUEST_IMAGEPAGER = 1;

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

    @BindView(R.id.tv_sorry)
    TextView sorryTv;

    @BindView(R.id.desc_lilLayout)
    LinearLayout descLinLay;

    @BindView(R.id.nested_scroll)
    NestedScrollView nestedScrollView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayout linearWithTabs;

    @Inject
    @Related
    LinearLayoutManager linearLayoutManagerRelated;

    @Inject
    @Gallery
    LinearLayoutManager linearLayoutManagerGallery;

    private boolean isLoading;

    TabLayout tabLayout;

    private int pos;

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

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.downloadDescription();
            presenter.downloadRelatedMovies(false);
        });

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent_material_dark_1));

        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.background_holo_dark));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            swipeRefreshLayout.setElevation(4);
        }

        return v;
    }

    public void setOnBackPressListener(MovieActivity.OnBackPressedListener onBackPressListener) {
        MovieActivity movieActivity = (MovieActivity) getActivity();
        movieActivity.setOnBackPressedListener(onBackPressListener);


    }

    @Override
    public void showError() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this.getContext(), getResources().getString(R.string.error_description), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideError() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);

        if (relatedMoviesAdapter.getItemCount() != 0) {
            seeAlso.setVisibility(View.VISIBLE);
        }

        relatedMovies.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishLoading() {
        isLoading = false;
    }

    @Override
    public void onDownloadError() {
        ratingBar.setVisibility(View.GONE);
        galleryTextView.setVisibility(View.GONE);
        youTubePlayerView.setVisibility(View.GONE);
        seeAlso.setVisibility(View.GONE);
        sorryTv.setVisibility(View.VISIBLE);
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

        tabLayout = getActivity().findViewById(R.id.tabs);
        linearWithTabs = getActivity().findViewById(R.id.linear);

        if (movie.getTrailer() != null && isOnline()) {
            prepareDescription.initializeYouTubePlayer(movie, youTubePlayerView, new View[]{relatedMovies, seeAlso, galleryTextView, gallery, overview, descLinLay, tabLayout}, linearWithTabs, nestedScrollView);
            youTubePlayerView.setVisibility(View.VISIBLE);
        } else {
            youTubePlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showRelatedMovies(List<Movie> movies) {

        //TODO
        if (!isOnline() && movies.size() != 0) {
            seeAlso.setVisibility(View.VISIBLE);
        } else if (isOnline() && progress.getVisibility() == View.GONE && movies.size() != 0) {
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

    @Override
    public void onItemClick(int position, ImageView image) {
        Intent intent = new Intent(getContext(), ImagePagerActivity.class);
        intent.putExtra(ARRAY_LIST, (ArrayList<Image>) galleryAdapter.getGalleryList());
        intent.putExtra(POSITION, position);

        pos = position;

        startActivityForResult(intent, REQUEST_IMAGEPAGER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_IMAGEPAGER) {
            int curpos = data.getIntExtra(CURRENT, pos);

            if (curpos > pos && curpos < galleryAdapter.getItemCount() - 1) {
                gallery.scrollToPosition(curpos + 1);
            } else if (curpos < pos && curpos > 0) {
                gallery.scrollToPosition(curpos - 1);
            } else if (curpos == 0) {
                gallery.scrollToPosition(curpos);
            } else if (curpos == galleryAdapter.getItemCount() - 1) {
                gallery.scrollToPosition(curpos);
            }
        }
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

    @Override
    public void onBackPress() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && overview.getVisibility() == View.GONE) {
            prepareDescription.exitFullscreen();
        }
    }
}
