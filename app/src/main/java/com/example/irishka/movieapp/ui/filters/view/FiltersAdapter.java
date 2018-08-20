package com.example.irishka.movieapp.ui.filters.view;

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

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.FilteresViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private GlideHelper glideHelper;

    @Inject
    public FiltersAdapter(OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
        this.onItemClickListener = onItemClickListener;
        this.glideHelper = glideHelper;
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public void addMoviesList(List<Movie> movies, boolean isFiltered) {
        if (isFiltered) {
            clearList(); }
            int idStart = this.movies.size();
            int idEnd = movies.size();
            this.movies.addAll(movies);
            notifyItemRangeInserted(idStart, idEnd);
    }

    public void clearList() {
        this.movies = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilteresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilteresViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false), onItemClickListener, glideHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull FilteresViewHolder holder, int position) {

        holder.bind(movies.get(position));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class FilteresViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener onItemClickListener;

        private GlideHelper glideHelper;

        @BindView(R.id.movie_image)
        ImageView image;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.overview)
        TextView overview;

        @BindView(R.id.rate)
        TextView rate;

        @BindView(R.id.adult)
        TextView adult;

        FilteresViewHolder(View itemView, OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
            this.glideHelper = glideHelper;
        }

        void bind(Movie movie) {

            title.setText(movie.getTitle());

            overview.setText(movie.getOverview());

            rate.setText(movie.getVoteAverageStr());

            String adultStr = "";
            if (movie.getAdult()) adultStr = itemView.getContext().getString(R.string.adult);
            adult.setText(adultStr);

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(movie));

            glideHelper.downloadPictureWithCache(movie.getPosterUrl(), image);
        }
    }
}
