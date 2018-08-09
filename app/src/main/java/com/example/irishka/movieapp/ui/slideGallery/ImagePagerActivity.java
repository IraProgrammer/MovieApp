package com.example.irishka.movieapp.ui.slideGallery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.irishka.movieapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class ImagePagerActivity extends DaggerAppCompatActivity {

    public static final int RES = 1;

    @BindView(R.id.pager)
    ViewPager pager;

//    @BindView(R.id.textView_position)
//    TextView textViewPosition;

    @Inject
    SlideGalleryAdapter slideGalleryAdapter;

    private int position;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        ButterKnife.bind(this);

        postponeEnterTransition();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendResult(RES, 5);
    }

    private void sendResult(int resultCode, int position) {

        Intent intent = new Intent();
        intent.putExtra("CUR", position);

        setResult(resultCode, intent);
    }

}
