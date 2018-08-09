package com.example.irishka.movieapp.ui.slideGallery;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irishka.movieapp.R;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class ImagePagerActivity extends DaggerAppCompatActivity {

    public static final int RES = 1;

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.count_photo)
    TextView count;

    @BindView(R.id.btn_home)
    ImageView btnHome;

    @Inject
    SlideGalleryAdapter slideGalleryAdapter;

    private int currentPosition;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        ButterKnife.bind(this);

        postponeEnterTransition();

        currentPosition = getIntent().getIntExtra("POSITION", 0);

        init();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {

        pager.setAdapter(slideGalleryAdapter);

        pager.setCurrentItem(currentPosition);

        count.setText((currentPosition +1) + "/" + slideGalleryAdapter.getCount());

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageSelected(int position) {
                sendResult(RESULT_OK, position+1);
                count.setText((position+1) + "/" + slideGalleryAdapter.getCount());
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });
    }

    private void sendResult(int resultCode, int position) {

        Intent intent = new Intent();
        intent.putExtra("CUR", position);

        setResult(resultCode, intent);
    }
}
