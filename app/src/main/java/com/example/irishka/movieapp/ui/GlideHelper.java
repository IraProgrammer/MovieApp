package com.example.irishka.movieapp.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;

import javax.inject.Inject;

public class GlideHelper {

    @Inject
    public GlideHelper() {
    }

    public void downloadPictureWithCache(String url, ImageView image) {
        Glide.with(image.getContext())
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.no_image)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                )
                .into(image);
    }

//    public void downloadPictureWithCacheWithoutPlaceholder(String url, ImageView image) {
//        Glide.with(image.getContext())
//                .load(url)
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                        .placeholder(R.color.background_holo_dark)
//                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                )
//                .into(image);
//    }

    public void downloadPictureWithoutPlaceholder(String url, ImageView image, Activity activity) {
        Glide.with(image.getContext())
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                )
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        scheduleStartPostponedTransition(image, activity);
//                        return false;
//                    }})
                .into(image);
    }


    private void scheduleStartPostponedTransition(final ImageView sharedElement, Activity activity) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onPreDraw() {
                sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                activity.startPostponedEnterTransition();
                return true;
            }
        });
    }

}
