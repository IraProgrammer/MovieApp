package com.example.irishka.movieapp.ui.actor.info.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static android.app.Activity.RESULT_OK;
import static com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment.ARRAY_LIST;
import static com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment.POSITION;
import static com.example.irishka.movieapp.ui.slideGallery.ImagePagerActivity.CURRENT;

public class InfoFragment extends MvpAppCompatFragment implements InfoView, PhotosAdapter.OnItemClickListener {

    private static final int REQUEST_IMAGEPAGER = 1;

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

    @BindView(R.id.tv_sorry)
    TextView sorryTv;

    @BindView(R.id.error)
    LinearLayout error;

    @BindView(R.id.error_btn)
    Button errorBtn;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private int pos;

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

        errorBtn.setOnClickListener(view -> {
            presenter.downloadInfo();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.downloadInfo());

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent_material_dark_1));

        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.background_holo_dark));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            swipeRefreshLayout.setElevation(4);
        }

        return v;
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void showError() {

        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this.getContext(), getResources().getString(R.string.error_description), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideError() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void hideProgress() {
        birthTxt.setVisibility(View.VISIBLE);
        placeTxt.setVisibility(View.VISIBLE);
        photosTxt.setVisibility(View.VISIBLE);

        progress.setVisibility(View.GONE);
    }

    @Override
    public void showInfo(Cast cast) {

        if (cast.getBirthday() == null) {
            sorryTv.setVisibility(View.VISIBLE);
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
    public void onItemClick(int position, ImageView imageView) {
        Intent intent = new Intent(getContext(), ImagePagerActivity.class);
        intent.putExtra(ARRAY_LIST, (ArrayList<Image>) photosAdapter.getPhotosList());
        intent.putExtra(POSITION, position);

        pos = position;

        startActivityForResult(intent, REQUEST_IMAGEPAGER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_IMAGEPAGER) {
            int curpos = data.getIntExtra(CURRENT, pos);

            if (curpos > pos && curpos < photosAdapter.getItemCount() - 1) {
                photosRecyclerView.scrollToPosition(curpos + 1);
            } else if (curpos < pos && curpos > 0) {
                photosRecyclerView.scrollToPosition(curpos - 1);
            } else if (curpos == 0) {
                photosRecyclerView.scrollToPosition(curpos);
            } else if (curpos == photosAdapter.getItemCount() - 1) {
                photosRecyclerView.scrollToPosition(curpos);
            }
        }
    }
}
