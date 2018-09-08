package com.example.irishka.movieapp.ui.movies.fragment.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.domain.entity.Movie;
import com.example.irishka.movieapp.domain.entity.MoviesListWithError;
import com.example.irishka.movieapp.ui.GlideHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MainFilmsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MoviesListWithError moviesWithError;

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

        void onErrorClick(ImageButton error);
    }

    public void addMoviesList(MoviesListWithError moviesListWithError) {

        this.moviesWithError = moviesListWithError;

        if (movies.size() > 0) {
            if (movies.get(movies.size() - 1).getOverview() == null)
                movies.remove(movies.size() - 1);
        }

        notifyDataSetChanged();

        int idStart = movies.size();
        int idEnd = moviesListWithError.getMovies().size();
        this.movies.addAll(moviesListWithError.getMovies());
        if (moviesListWithError.isError()) {
            movies.add(new Movie());
        }
        notifyItemRangeInserted(idStart, idEnd);
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).getOverview() == null)
            return 0;
        else
            return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                return new ErrorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.error_btn, parent, false), onItemClickListener);
            case 1:
                return new MainFilmsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false), onItemClickListener, glideHelper);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 0:
                ((ErrorViewHolder) holder).bind();
                break;

            case 1:
                ((MainFilmsViewHolder) holder).bind(movies.get(position));
                break;
        }

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

        void bind(Movie movie) {

            title.setText(movie.getTitle());

            rateText.setText(movie.getVoteAverageStr());

            if (movie.getAdult())
                adultText.setText(itemView.getContext().getString(R.string.adult));

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(movie));

            glideHelper.downloadPictureWithCache(movie.getPosterUrl(), image);
        }
    }

    static class ErrorViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener onItemClickListener;

        @BindView(R.id.btn_err)
        ImageButton errorBtn;

        @BindView(R.id.progress)
        MaterialProgressBar materialProgressBar;

        ErrorViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
        }

        void bind() {

            errorBtn.setVisibility(View.VISIBLE);

            errorBtn.setOnClickListener(view -> onItemClickListener.onErrorClick(errorBtn));

            StaggeredGridLayoutManager.LayoutParams params;
            params = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
            params.setFullSpan(true);
            itemView.setLayoutParams(params);
        }
    }
}
