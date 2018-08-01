package com.example.irishka.movieapp.ui.movie.description;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.format;

public class PrepareDescription {

    private Fragment fragment;

    @Inject
    public PrepareDescription(Fragment fragment){
        this.fragment = fragment;
    }

    public void getPicture(Movie movie, ImageView image) {
        Glide.with(image.getContext())
                .load(movie.getPosterUrl())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .transform(new RoundedCorners(20))
                        .placeholder(R.drawable.no_image)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(image);
    }

    public String getGenre(Movie movie) {
        StringBuilder genresStr = new StringBuilder();
        List<Genre> genres = movie.getGenres();

        for (int i = 0; i < genres.size(); i++) {
            genresStr.append(genres.get(i).getName()).append(", ");
            if (i == genres.size() - 1) genresStr.append(genres.get(genres.size() - 1).getName());
        }

        return genresStr.toString();
    }

    public String getYear(Movie movie) {
        String releaseDate = movie.getReleaseDate();
        return releaseDate.substring(0, 4);
    }

    public String getDuration(Movie movie) {
        int hours = movie.getRuntime() / 60;
        int minutes = movie.getRuntime() % 60;

        if (minutes < 10 && minutes > 0) return format(fragment.getString(R.string.durationWithNull), hours, minutes);

        return String.format(fragment.getString(R.string.duration), hours, minutes);
    }

    public String getAdult(Movie movie) {

        if (movie.getAdult()) return fragment.getString(R.string.adult);
        else return "";

    }

    public String getCountries(Movie movie) {
        StringBuilder countriesStr = new StringBuilder();
        List<ProductionCountry> productionCountries = movie.getCountries();

        for (int i = 0; i < productionCountries.size(); i++) {
            countriesStr.append(productionCountries.get(i).getName()).append(", ");
            if (i == productionCountries.size() - 1) countriesStr.append(productionCountries.get(productionCountries.size() - 1).getName());
        }

        return countriesStr.toString();
    }

}
