package com.example.irishka.movieapp.ui.SlideGallery;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.ui.GlideHelper;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlideGalleryAdapter extends PagerAdapter {

    private ArrayList<Image> backdrops;

    private GlideHelper glideHelper;

 //   ImagePagerActivity i;

    //TODO ButterKnife
//    @BindView(R.id.image_in_viewpager)
//    ImageView imageView;

    @Inject
    public SlideGalleryAdapter(ArrayList<Image> backdrops, GlideHelper glideHelper) {

        this.backdrops = backdrops;
        this.glideHelper = glideHelper;
     //   this.i = i;
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

      //  ButterKnife.bind(imageLayout, i);

        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image_in_viewpager);

        TextView textViewPosition = imageLayout.findViewById(R.id.textView111);

        textViewPosition.setText(position + "/" + getCount());

        glideHelper.downloadPictureWithCache(backdrops.get(position).getFileUrl(), imageView);

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
