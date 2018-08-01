package com.example.irishka.movieapp.ui.movie.creators.actor.films.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.MoviesViewHolder> {

    private List<MovieModel> movies = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    @Inject
    public FilmsAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(MovieModel movie);
    }

    public void setMoviesList(List<MovieModel> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false), onItemClickListener);
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

        OnItemClickListener onItemClickListener;

        @BindView(R.id.movie_text)
        TextView title;

        @BindView(R.id.movie_image)
        ImageView image;

        @BindView(R.id.rate_text)
        TextView rateText;

        @BindView(R.id.adult_text)
        TextView adultText;

        MoviesViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
        }

        void bind(MovieModel movie) {

            title.setText(movie.getTitle());

            String voteAverage = String.format(itemView.getContext().getString(R.string.vote_average), (float) movie.getVoteAverage());

            rateText.setText(voteAverage);

            String adult = "";
            if (movie.getAdult()) adult = itemView.getContext().getString(R.string.adult);
            adultText.setText(adult);

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(movie));

            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w500//" + movie.getPosterPath())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.no_image)
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                    .into(image);
        }
    }
}
