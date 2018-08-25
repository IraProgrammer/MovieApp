package com.example.irishka.movieapp.ui.slideGallery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment.POSITION;

public class ImagePagerActivity extends DaggerAppCompatActivity {

    @BindView(R.id.main_pager)
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

        currentPosition = getIntent().getIntExtra(POSITION, 0);

        init();

        btnHome.setOnClickListener(view -> finish());
    }

    private void init() {

        pager.setAdapter(slideGalleryAdapter);

        pager.setCurrentItem(currentPosition);

        count.setText((currentPosition + 1) + "/" + slideGalleryAdapter.getCount());

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageSelected(int position) {
                sendResult(RESULT_OK, position);
                count.setText((position + 1) + "/" + slideGalleryAdapter.getCount());
            }
        });
    }

    private void sendResult(int resultCode, int position) {

        Intent intent = new Intent();
        intent.putExtra("CUR", position);

        setResult(resultCode, intent);
    }
}
