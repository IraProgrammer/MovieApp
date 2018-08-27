package com.example.irishka.movieapp.ui.actor.info.view;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.ui.GlideHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    private List<Image> photos = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private GlideHelper glideHelper;

    @Inject
    public PhotosAdapter(OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
        this.onItemClickListener = onItemClickListener;
        this.glideHelper = glideHelper;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, ImageView imageView);
    }

    public void setPhotosList(List<Image> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    public List<Image> getPhotosList() {
        return photos;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_photo_item, parent, false), onItemClickListener, glideHelper);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {

        holder.bind(photos.get(position));

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class PhotosViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener onItemClickListener;

        private GlideHelper glideHelper;

        @BindView(R.id.actor_image)
        ImageView image;

        PhotosViewHolder(View itemView, OnItemClickListener onItemClickListener, GlideHelper glideHelper) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
            this.glideHelper = glideHelper;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void bind(Image photo) {

            image.setTransitionName(String.valueOf(getAdapterPosition()));

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(getAdapterPosition(), image));

            glideHelper.downloadPictureWithCache(photo.getFileUrl(), image);
        }
    }
}
