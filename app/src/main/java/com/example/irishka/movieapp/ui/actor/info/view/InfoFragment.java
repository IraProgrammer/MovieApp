package com.example.irishka.movieapp.ui.actor.info.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.models.ActorInfoModel;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;
import com.example.irishka.movieapp.data.models.ActorProfileModel;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.ui.actor.info.presenter.InfoPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class InfoFragment extends MvpAppCompatFragment implements InfoView, PhotosAdapter.OnItemClickListener {

    @Inject
    Provider<InfoPresenter> presenterProvider;

    @InjectPresenter
    InfoPresenter presenter;

    @ProvidePresenter
    InfoPresenter providePresenter() {
        return presenterProvider.get();
    }

    @Inject
    PhotosAdapter photosAdapter;

    @BindView(R.id.photos_recycler_view)
    RecyclerView photosRecyclerView;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.birth)
    TextView birth;

    @BindView(R.id.place)
    TextView place;

    @BindView(R.id.info)
    TextView biography;

    @BindView(R.id.actor_image)
    ImageView image;

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, v);

        photosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        photosRecyclerView.setAdapter(photosAdapter);

        return v;
    }

    @Override
    public void showInfo(Cast cast) {
        name.setText(cast.getName());

        birth.setText(getBirthday(cast));
        place.setText(cast.getPlaceOfBirth());
        biography.setText(cast.getBiography());

        Glide.with(this)
                .load(cast.getProfileUrl())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.no_image)
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(image);

        photosAdapter.setPhotosList(cast.getPhotosUrl());
    }

    private String getBirthday(Cast cast){

        String oldDateString = cast.getBirthday();
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy, dd MMMM", Locale.ENGLISH);

        Date date = null;
        try {
            date = oldDateFormat.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = newDateFormat.format(date);

        return result + System.lineSeparator();
    }

    @Override
    public void onItemClick(String photoUrl) {
//        FragmentManager manager = getFragmentManager();
//        ImageDialog dialog = ImageDialog.newInstance(actorProfileModel.getFilePath());
//        dialog.show(manager, "dialog");
    }
}