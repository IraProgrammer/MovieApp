package com.example.irishka.movieapp.ui.SlideGallery;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.irishka.movieapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class ImagePagerActivity extends DaggerAppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;

    @Inject
    SlideGalleryAdapter slideGalleryAdapter;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        ButterKnife.bind(this);

        position = getIntent().getIntExtra("POSITION", 0);

        init();
    }

    private void init() {

        pager.setAdapter(slideGalleryAdapter);

        pager.setCurrentItem(position);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

}
