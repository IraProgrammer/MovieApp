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
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Description;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.movie.description.presenter.DescriptionPresenter;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import okhttp3.Interceptor;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

public class DescriptionFragment extends MvpAppCompatFragment implements DescriptionView, RelatedMoviesAdapter.OnItemClickListener {

    @Inject
    RelatedMoviesAdapter relatedMoviesAdapter;

    @Inject
    Provider<DescriptionPresenter> presenterProvider;

    @InjectPresenter
    DescriptionPresenter presenter;

    @ProvidePresenter
    DescriptionPresenter providePresenter() {
        return presenterProvider.get();
    }

    @BindView(R.id.film_title)
    TextView filmTitle;

    @BindView(R.id.year_country)
    TextView yearCountry;

    @BindView(R.id.genre)
    TextView genre;

    @BindView(R.id.duration)
    TextView duration;

    @BindView(R.id.rate)
    TextView rate;

    @BindView(R.id.film_image)
    ImageView image;

    @BindView(R.id.overview)
    TextView overview;

    @BindView(R.id.see_also)
    TextView seeAlso;

    @BindView(R.id.related_recycler_view)
    RecyclerView relatedMovies;

    public static DescriptionFragment newInstance(){
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

        relatedMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        relatedMovies.setAdapter(relatedMoviesAdapter);

        return v;
    }

    @Override
    public String toString() {
        return "Description";
    }

    @Override
    public void showDescription(Description description) {

        filmTitle.setText(description.getTitle());

        yearCountry.setText(description.getReleaseDate() + ", " + description.getProductionCountries().get(0).getName());

        setPicture(description);

        setGenre(description);

        setDurationAndAdult(description);

        rate.setText(String.valueOf(description.getPopularity()));

        overview.setText(description.getOverview());

        seeAlso.setText(R.string.see_also);
    }

    private void setPicture(Description description){
        Glide.with(this)
                .load(description.getPosterPath())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.no_image)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(image);
    }

    private void setGenre(Description description){
        StringBuilder genresStr = new StringBuilder();
        List<Genre> genres = description.getGenres();

        for (int i = 0; i < genres.size()-1; i++) {
            genresStr.append(genres.get(i).getName()).append(", ");
        }

        genresStr.append(genres.get(genres.size()-1).getName());

        genre.setText(genresStr.toString());
    }

    private void setDurationAndAdult(Description description){
        int hours = description.getRuntime()/60;
        int minutes = description.getRuntime()%60;

        String minStr;
        if (minutes < 10) minStr = "0" + String.valueOf(minutes);
        else minStr = String.valueOf(String.valueOf(minutes));
        duration.setText(String.valueOf(hours) + ":" + minStr);
        if (description.getAdult()) {
            duration.append(", 18+");
        }

    }

    @Override
    public void showRelatedMovies(List<Movie> movies){
        relatedMoviesAdapter.setRelatedList(movies);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        startActivity(intent);
    }
}
