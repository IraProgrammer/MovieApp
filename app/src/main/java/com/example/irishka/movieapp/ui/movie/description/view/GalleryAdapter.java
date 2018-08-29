package com.example.irishka.movieapp.ui.movie.description.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.ui.GlideHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Image> backdrops = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private GlideHelper glideHelper;

    @Inject
    public GalleryAdapter(OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
        this.onItemClickListener = onItemClickListener;
        this.glideHelper = glideHelper;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, ImageView image);
    }

    public void setGalleryList(List<Image> backdrops) {
        this.backdrops = backdrops;
        notifyDataSetChanged();
    }

    public List<Image> getGalleryList() {
        return backdrops;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false), onItemClickListener, glideHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {

        holder.bind(backdrops.get(position));

    }

    @Override
    public int getItemCount() {
        return backdrops.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {

        private GlideHelper glideHelper;

        private OnItemClickListener onItemClickListener;

        @BindView(R.id.backdrop_image)
        ImageView image;

        GalleryViewHolder(View itemView, OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
            this.glideHelper = glideHelper;
        }


        void bind(Image backdrop) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                image.setTransitionName(String.valueOf(getAdapterPosition()));
            }

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(getAdapterPosition(), image));

            glideHelper.downloadPictureWithCache(backdrop.getFileUrl(), image);
        }
    }
}
