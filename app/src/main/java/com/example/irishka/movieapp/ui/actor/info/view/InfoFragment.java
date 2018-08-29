package com.example.irishka.movieapp.ui.actor.info.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment.ARRAY_LIST;
import static com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment.POSITION;
import static com.example.irishka.movieapp.ui.slideGallery.ImagePagerActivity.CURRENT;

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

    @BindView(R.id.tv_sorry)
    TextView sorryTv;

    @BindView(R.id.error)
    LinearLayout error;

    @BindView(R.id.error_btn)
    Button errorBtn;

    public static int curpos = 0;

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

        return v;
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        error.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        error.setVisibility(View.GONE);
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

        curpos = position;

        getActivity().setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

                if (curpos != position) {

                    ImageView viewAtPosition = (ImageView) photosRecyclerView.getLayoutManager().findViewByPosition(curpos);
                    final RecyclerView.LayoutManager layoutManager =
                            photosRecyclerView.getLayoutManager();

                    if (viewAtPosition == null
                            || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
                        postponeEnterTransition();
                        photosRecyclerView.scrollToPosition(curpos);
                    }
                }

                RecyclerView.ViewHolder selectedViewHolder = photosRecyclerView.findViewHolderForAdapterPosition(curpos);
                if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                    return;
                }
                sharedElements.put(String.valueOf(curpos), selectedViewHolder.itemView.findViewById(R.id.actor_image));

                photosRecyclerView.post(() -> {
                    startPostponedEnterTransition();

                    RecyclerView.ViewHolder selectedViewHolder2 = photosRecyclerView.findViewHolderForAdapterPosition(curpos);
                    if (selectedViewHolder2 == null || selectedViewHolder2.itemView == null) {
                        return;
                    }
                    names.clear();
                    sharedElements.clear();
                    names.add(String.valueOf(curpos));
                    sharedElements.put(String.valueOf(curpos), selectedViewHolder2.itemView.findViewById(R.id.actor_image));

                });
            }
        });

        ActivityOptionsCompat options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
        }

        startActivity(intent, options.toBundle());
    }
}
