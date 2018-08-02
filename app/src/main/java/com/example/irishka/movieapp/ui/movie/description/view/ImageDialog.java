package com.example.irishka.movieapp.ui.movie.description.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Movie;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

public class ImageDialog extends Activity {

    private ImageView mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.dialog_layout);
        mDialog = (ImageView) findViewById(R.id.dialog_image);
        mDialog.setClickable(true);

      //  String url = getIntent().getIntExtra(MOVIE_ID, 235);

            Glide.with(this)
                    .load(getIntent().getStringExtra("URL"))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .transform(new RoundedCorners(20))
                            .placeholder(R.drawable.no_image)
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                    .into(mDialog);


        mDialog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) { finish();
        }
    });

}
}
