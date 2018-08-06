package com.example.irishka.movieapp.ui.movie.description.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDialog extends DialogFragment {

    private static final String URL = "URL";

    @BindView(R.id.dialog_image)
    ImageView image;

    public static ImageDialog newInstance(String url) {
        
        Bundle args = new Bundle();
        args.putString(URL, url);

        ImageDialog fragment = new ImageDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext());

        View view = View.inflate(getContext(), R.layout.dialog_layout, null);
        dialog.setContentView(view);
        ButterKnife.bind(this, view);

        image.setClickable(true);

        Glide.with(this)
                .load(this.getArguments().getString(URL))
                .apply(new RequestOptions().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                //.apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(image);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        return dialog;
    }

    @Override
    public void onResume(){
        super.onResume();
        //getDialog().getWindow().setLayout(700, 1300);
    }
}
