package com.example.irishka.movieapp.view;

import android.content.Context;
import android.net.Uri;
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
import com.example.irishka.movieapp.model.Pojo.ConcreteMovie;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500//";

    private List<ConcreteMovie> movies = new ArrayList<>();

    public void setMoviesList(List<ConcreteMovie> movie) {
        this.movies = movie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        ConcreteMovie movie = movies.get(position);

        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_textView)
        TextView title;

        @BindView(R.id.movie_imageView)
        ImageView image;

        MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ConcreteMovie movie) {

             title.setText(movie.getTitle());

            Glide.with(itemView.getContext())
                    .load(Uri.parse(BASE_IMAGE_URL + movie.getPosterPath()))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .override(Target.SIZE_ORIGINAL))
                    .into(image);
        }
    }
}
