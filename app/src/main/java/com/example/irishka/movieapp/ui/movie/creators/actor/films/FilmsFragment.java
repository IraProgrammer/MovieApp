package com.example.irishka.movieapp.ui.movie.creators.actor.films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class FilmsFragment extends MvpAppCompatFragment implements FilmsView {

    @Inject
    Provider<FilmsPresenter> presenterProvider;

    @InjectPresenter
    FilmsPresenter presenter;

    @ProvidePresenter
    FilmsPresenter providePresenter() {
        return presenterProvider.get();
    }

//    @Inject
//    PhotosAdapter photosAdapter;
//
//    @BindView(R.id.photos_recycler_view)
//    RecyclerView photosRecyclerView;

    public static FilmsFragment newInstance(){
        return new FilmsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_creators, container, false);
        ButterKnife.bind(this, v);

     //   photosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

     //   photosRecyclerView.setAdapter(photosAdapter);

        return v;
    }

//    @Override
//    public void showInfo(List<Cast> cast) {
//
//        photosAdapter.setList(cast);
//
//    }
}
