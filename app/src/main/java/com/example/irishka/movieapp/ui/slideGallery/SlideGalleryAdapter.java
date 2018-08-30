package com.example.irishka.movieapp.ui.slideGallery;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.ui.GlideHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class SlideGalleryAdapter extends PagerAdapter {

    private List<Image> backdrops;

    private GlideHelper glideHelper;

    private Map<Integer, ImageView> map = new HashMap<>();

    //TODO ButterKnife
//    @BindView(R.id.image_in_viewpager)
//    ImageView imageView;

    @Inject
    public SlideGalleryAdapter(List<Image> backdrops, GlideHelper glideHelper) {
        this.backdrops = backdrops;
        this.glideHelper = glideHelper;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return backdrops.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {

        View imageLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.viewpager_item, view, false);

        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image_in_viewpager);

        map.put(position, imageView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(String.valueOf(position));
        }

        glideHelper.downloadPictureWithoutPlaceholder(backdrops.get(position).getFileUrl(), imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
