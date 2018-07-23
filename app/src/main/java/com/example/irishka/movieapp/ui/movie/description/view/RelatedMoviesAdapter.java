package com.example.irishka.movieapp.ui.movie.description.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class RelatedMoviesAdapter extends RecyclerView.Adapter<RelatedMoviesAdapter.RelatedViewHolder> {

    private List<Movie> relatedMovies = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    @Inject
    public RelatedMoviesAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public void setRelatedList(List<Movie> relatedMovies) {
        this.relatedMovies = relatedMovies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RelatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RelatedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedViewHolder holder, int position) {

        holder.bind(relatedMovies.get(position));

    }

    @Override
    public int getItemCount() {
        return relatedMovies.size();
    }

    class RelatedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_text)
        TextView title;

        @BindView(R.id.movie_image)
        ImageView image;

        RelatedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Movie movie) {

            title.setText(movie.getTitle());

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(movie));

            Glide.with(itemView.getContext())
                    .load(movie.getPosterUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.no_image)
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                    .into(image);
        }
    }
}
