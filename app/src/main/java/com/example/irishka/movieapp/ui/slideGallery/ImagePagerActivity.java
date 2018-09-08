package com.example.irishka.movieapp.ui.slideGallery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.ui.actor.info.view.InfoFragment;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movie.view.MovieActivity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment.POSITION;

public class ImagePagerActivity extends DaggerAppCompatActivity {

    public static final String CURRENT = "current";

    @BindView(R.id.main_pager)
    ViewPager pager;

    @BindView(R.id.count_photo)
    TextView count;

    @BindView(R.id.btn_home)
    ImageView btnHome;

    @Inject
    SlideGalleryAdapter slideGalleryAdapter;

    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        ButterKnife.bind(this);

        btnHome.setOnClickListener(view -> onBackPressed());

        currentPosition = getIntent().getIntExtra(POSITION, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        init();
    }

    private void init() {

        pager.setAdapter(slideGalleryAdapter);

        pager.setCurrentItem(currentPosition);

        count.setText(new StringBuilder().append(String.valueOf(currentPosition + 1)).append("/").append(slideGalleryAdapter.getCount()).toString());

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                count.setText(new StringBuilder().append(position + 1).append("/").append(slideGalleryAdapter.getCount()).toString());
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent data = new Intent();
        data.putExtra(CURRENT, currentPosition);

        setResult(RESULT_OK, data);

        super.onBackPressed();
    }
}
