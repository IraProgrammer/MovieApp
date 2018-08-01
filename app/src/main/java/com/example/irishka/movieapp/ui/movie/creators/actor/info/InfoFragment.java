package com.example.irishka.movieapp.ui.movie.creators.actor.info;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.models.ActorPhotosModel;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class InfoFragment extends MvpAppCompatFragment implements InfoView {

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
    public void showPhotos(ActorPhotosModel photosModel) {
        photosAdapter.setPhotosList(photosModel.getProfiles());
    }

//    @Override
//    public void showInfo(List<Cast> cast) {
//
//        photosAdapter.setList(cast);
//
//    }
}
