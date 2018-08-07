package com.example.irishka.movieapp.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;

import javax.inject.Inject;

public class GlideHelper {

    @Inject
    public GlideHelper(){}

    public void downloadPictureWithCache(String url, ImageView image) {
        Glide.with(image.getContext())
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.no_image)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(image);
    }

    public void downloadPicture(String url, ImageView image) {
        Glide.with(image.getContext())
                .load(url)
                .apply(new RequestOptions()
                .placeholder(R.drawable.no_image)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(image);
    }
}
