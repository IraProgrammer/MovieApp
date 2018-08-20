package com.example.irishka.movieapp.ui.actor.films.view;

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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.MoviesViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private GlideHelper glideHelper;

    @Inject
    public FilmsAdapter(OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
        this.onItemClickListener = onItemClickListener;
        this.glideHelper = glideHelper;
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public void setMoviesList(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false), onItemClickListener, glideHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

        holder.bind(movies.get(position));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        private GlideHelper glideHelper;

        private OnItemClickListener onItemClickListener;

        @BindView(R.id.movie_text)
        TextView title;

        @BindView(R.id.movie_image)
        ImageView image;

        @BindView(R.id.rate_text)
        TextView rateText;

        @BindView(R.id.adult_text)
        TextView adultText;

        MoviesViewHolder(View itemView, OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
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
