package com.example.irishka.movieapp.ui.movie.description.view;

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
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Backdrop> backdrops = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    @Inject
    public GalleryAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Backdrop backdrop);
    }

    public void setGalleryList(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {

        holder.bind(backdrops.get(position));

    }

    @Override
    public int getItemCount() {
        return backdrops.size();
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {

        OnItemClickListener onItemClickListener;

        @BindView(R.id.backdrop_image)
        ImageView image;

        GalleryViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
        }

        void bind(Backdrop backdrop) {

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(backdrop));

            Glide.with(itemView.getContext())
                    .load(backdrop.getFileUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .placeholder(R.drawable.no_image))
                     //       .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                    .into(image);
        }
    }
}
