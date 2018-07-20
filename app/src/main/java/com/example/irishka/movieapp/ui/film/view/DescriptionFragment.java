package com.example.irishka.movieapp.ui.film.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.data.models.Genre;
import com.example.irishka.movieapp.ui.film.presenter.DescriptionPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;
import dagger.android.DaggerFragment;
import dagger.android.support.AndroidSupportInjection;

import static android.content.Context.DEVICE_POLICY_SERVICE;
import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

public class DescriptionFragment extends MvpAppCompatFragment implements DescriptionView {

  /*  @Inject
    public DescriptionFragment() {
    } */

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
        return v;
    }

    @Override
    public String toString() {
        return "Description";
    }

    @Override
    public void showDescription(DescriptionModel description) {

        filmTitle.setText(description.getTitle());

        yearCountry.setText(description.getReleaseDate() + ", " + description.getProductionCountries().get(0).getName());

        setPicture(description);

        setGenre(description);

        duration.setText(description.getRuntime() + ", " + description.getAdult());

        rate.setText(String.valueOf(description.getPopularity()));

        overview.setText(description.getOverview());

        seeAlso.setText("See Also");
    }

    private void setPicture(DescriptionModel description){
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500//" + description.getPosterPath())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.no_image)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(image);
    }

    private void setGenre(DescriptionModel description){
        String genresStr = "";

        List<Genre> genres = description.getGenres();
        for (Genre g: genres) {
            genresStr += g.getName() + ", ";
        }

        genre.setText(genresStr);
    }
}
