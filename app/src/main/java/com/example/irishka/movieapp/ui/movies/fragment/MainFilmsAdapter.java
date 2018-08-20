package com.example.irishka.movieapp.ui.movies.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.ui.GlideHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFilmsAdapter extends RecyclerView.Adapter<MainFilmsAdapter.MainFilmsViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private GlideHelper glideHelper;

    @Inject
    public MainFilmsAdapter(OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
        this.onItemClickListener = onItemClickListener;
        this.glideHelper = glideHelper;
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public void addMoviesList(List<Movie> movies) {
        int idStart = this.movies.size();
        int idEnd = movies.size();
        this.movies.addAll(movies);
        notifyItemRangeInserted(idStart, idEnd);
    }

    @NonNull
    @Override
    public MainFilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainFilmsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false), onItemClickListener, glideHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull MainFilmsViewHolder holder, int position) {

        holder.bind(movies.get(position));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MainFilmsViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener onItemClickListener;

        private GlideHelper glideHelper;

        @BindView(R.id.movie_text)
        TextView title;

        @BindView(R.id.rate_text)
        TextView rateText;

        @BindView(R.id.adult_text)
        TextView adultText;

        @BindView(R.id.movie_image)
        ImageView image;

        MainFilmsViewHolder(View itemView, OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
            this.glideHelper = glideHelper;
        }

//        private String getDate() {
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//            return dateFormat.format(new Date());
//        }
//
//        private int getYear(String releaseDate) {
//            return Integer.parseInt(releaseDate.split("-")[0]);
//        }

        void bind(Movie movie) {

            title.setText(movie.getTitle());

            rateText.setText(movie.getVoteAverageStr());

//            if (Integer.compare(getYear(movie.getReleaseDate()), getYear(getDate())) == 1) {
//                rateText.setText(itemView.getContext().getString(R.string.see_soon));
//            } else {
//                rateText.setText(String.format(itemView.getContext().getString(R.string.vote_average), (float) movie.getVoteAverageStr()));
//            }

            String adult = "";
            if (movie.getAdult()) adult = itemView.getContext().getString(R.string.adult);
            adultText.setText(adult);

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(movie));

            glideHelper.downloadPictureWithCache(movie.getPosterUrl(), image);
        }
    }
}
