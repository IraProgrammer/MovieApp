package com.example.irishka.movieapp.ui.movie.creators.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.models.CastModel;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.domain.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.CreatorsViewHolder> {

    private List<Cast> actors = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    @Inject
    public ActorsAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Cast cast);
    }

    public void setList(List<Cast> actors) {
        this.actors = actors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreatorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreatorsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.actors_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActorsAdapter.CreatorsViewHolder holder, int position) {

        holder.bind(actors.get(position));

    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    class CreatorsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.actor_name)
        TextView title;

        @BindView(R.id.actor_image)
        ImageView image;

        CreatorsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Cast actor) {

            title.setText(actor.getName());

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(actor));

            Glide.with(itemView.getContext())
                    .load(actor.getProfileUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            //    .optionalFitCenter()
                            .placeholder(R.drawable.no_image)
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            //   .centerCrop()
                            .error(R.drawable.no_image)
                    )
                    .into(image);
        }
    }
}

