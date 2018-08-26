package com.example.irishka.movieapp.ui.movie.description;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Genre;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

import java.util.List;

import javax.inject.Inject;

import static java.lang.String.format;

public class PrepareDescription {

    private GlideHelper glideHelper;

    private Fragment fragment;

    private YouTubePlayerView youTubePlayerView;

    private FullScreenHelper fullScreenHelper;

    @Inject
    public PrepareDescription(Fragment fragment, GlideHelper glideHelper) {
        this.fragment = fragment;
        this.glideHelper = glideHelper;
    }

    public void getPicture(Movie movie, ImageView image) {

        glideHelper.downloadPictureWithCache(movie.getPosterUrl(), image);
    }

    public String getGenres(Movie movie) {
        StringBuilder genresStr = new StringBuilder();
        List<Genre> genres = movie.getGenres();

        for (int i = 0; i < genres.size(); i++) {
            if (i == genres.size() - 1) {
                genresStr.append(genres.get(genres.size() - 1).getName());
                return genresStr.toString();
            }
            genresStr.append(genres.get(i).getName()).append(", ");
        }

        return genresStr.toString();
    }

    public String getYear(Movie movie) {
        if (!movie.getReleaseDate().equals("")) {
            String releaseDate = movie.getReleaseDate();
            return releaseDate.substring(0, 4);
        } else return "";
    }

    public String getDuration(Movie movie) {
        int hours = movie.getRuntime() / 60;
        int minutes = movie.getRuntime() % 60;

        if (minutes < 10 && minutes > 0)
            return format(fragment.getString(R.string.durationWithNull), hours, minutes);

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
            if (i == productionCountries.size() - 1) {
                countriesStr.append(productionCountries.get(productionCountries.size() - 1).getName());
                return countriesStr.toString();
            }
            countriesStr.append(productionCountries.get(i).getName()).append(", ");
        }

        return countriesStr.toString();
    }

    public void initializeYouTubePlayer(Movie movie, YouTubePlayerView youTubePlayerView, View[] views, LinearLayout linearWithTabs) {

        this.youTubePlayerView = youTubePlayerView;
        fragment.getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        initializedYouTubePlayer.cueVideo(movie.getTrailer().getKey(), 0);
                    }
                });
                addFullScreenListenerToPlayer(initializedYouTubePlayer, views, linearWithTabs);
            }
        }, true);
    }

    private void addFullScreenListenerToPlayer(final YouTubePlayer youTubePlayer, View[] views, LinearLayout linearWithTabs) {
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                fragment.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                fullScreenHelper = new FullScreenHelper(fragment.getActivity(), linearWithTabs, youTubePlayerView, views);

                fullScreenHelper.enterFullScreen();
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                fragment.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                if (fullScreenHelper != null) {
                    fullScreenHelper.exitFullScreen();
                }
            }
        });
    }

}
