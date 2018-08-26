package com.example.irishka.movieapp.ui.slideGallery;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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

    private ImagePagerActivity imagePagerActivity;

    private ImageView imageV;

    //  private List<ImageView> list = new ArrayList<>();

    private Map<Integer, ImageView> map = new HashMap<>();

    //TODO ButterKnife
//    @BindView(R.id.image_in_viewpager)
//    ImageView imageView;

    @Inject
    public SlideGalleryAdapter(List<Image> backdrops, GlideHelper glideHelper,
                               ImagePagerActivity i
    ) {
        this.backdrops = backdrops;
        this.glideHelper = glideHelper;
        this.imagePagerActivity = i;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return backdrops.size();
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {

        View imageLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.viewpager_item, view, false);

        //  ButterKnife.bind(imageLayout, imagePagerActivity);

        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image_in_viewpager);

        imageV = imageView;

        //  list.add(imageView);

        map.putIfAbsent(position, imageView);

        String a = String.valueOf(position);

        imageView.setTransitionName(a);

        glideHelper.downloadPictureWithoutPlaceholder(backdrops.get(position).getFileUrl(), imageView, imagePagerActivity);

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

    public void call(int position) {
        imagePagerActivity.setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                names.clear();
                int s = sharedElements.size();
                int k = position;
               sharedElements.clear();
                names.add(String.valueOf(position));
                sharedElements.put(String.valueOf(position), map.get(position));
            }
        });
    }


}
