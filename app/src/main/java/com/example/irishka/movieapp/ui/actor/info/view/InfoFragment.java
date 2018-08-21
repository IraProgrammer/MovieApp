package com.example.irishka.movieapp.ui.actor.info.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.slideGallery.ImagePagerActivity;
import com.example.irishka.movieapp.ui.actor.info.presenter.InfoPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class InfoFragment extends MvpAppCompatFragment implements InfoView, PhotosAdapter.OnItemClickListener {

    @Inject
    Provider<InfoPresenter> presenterProvider;

    @Inject
    GlideHelper glideHelper;

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

    @BindView(R.id.progressBar)
    MaterialProgressBar progress;

    @BindView(R.id.birth_txt)
    TextView birthTxt;

    @BindView(R.id.place_txt)
    TextView placeTxt;

    @BindView(R.id.photos)
    TextView photosTxt;

    @BindView(R.id.sorry)
    TextView sorry;

    @BindView(R.id.error)
    LinearLayout error;

    @BindView(R.id.error_btn)
    Button errorBtn;

    public static InfoFragment newInstance() {
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

        errorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.downloadInfo();
                if (isOnline()) {
                    error.setVisibility(View.GONE);
                }
            }
        });

        return v;
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        birthTxt.setVisibility(View.VISIBLE);
        placeTxt.setVisibility(View.VISIBLE);
        photosTxt.setVisibility(View.VISIBLE);

        progress.setVisibility(View.GONE);

        if (!isOnline()) {
            error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showInfo(Cast cast) {

        if (cast.getBirthday() == null) {
            sorry.setVisibility(View.VISIBLE);
        }

        name.setText(cast.getName());

        birth.setText(getBirthday(cast));

        place.setText(cast.getPlaceOfBirth());
        biography.setText(cast.getBiography());

        glideHelper.downloadPictureWithCache(cast.getProfileUrl(), image);

        photosAdapter.setPhotosList(cast.getPhotos());
    }

    private String getBirthday(Cast cast) {

        if (cast.getBirthday() == null || cast.getBirthday().length() == 0) return null;

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
    public void onItemClick(List<Image> photos, int position) {
        Intent intent = new Intent(getContext(), ImagePagerActivity.class);
        intent.putExtra("ARRAYLIST", (ArrayList<Image>) photos);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
