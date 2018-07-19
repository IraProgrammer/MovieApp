package com.example.irishka.movieapp.ui.view;

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
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    @Inject
    public MoviesAdapter() {

    }

    public void setMoviesList(List<Movie> movies) {
        int idStart = this.movies.size();
        int idEnd = movies.size();
        this.movies.addAll(movies);
        notifyItemRangeInserted(idStart, idEnd);
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

        holder.bind(movies.get(position));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_text)
        TextView title;

        @BindView(R.id.movie_image)
        ImageView image;

        MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Movie movie) {

            title.setText(movie.getTitle());

            Glide.with(itemView.getContext())
                    .load(movie.getPosterUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.no_image)
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                    .into(image);
        }
    }
}
