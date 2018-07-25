package com.example.irishka.movieapp.ui.movie.creators.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.models.Credits;
import com.example.irishka.movieapp.ui.movie.creators.presenter.CreatorsPresenter;
import com.example.irishka.movieapp.ui.movie.description.presenter.DescriptionPresenter;
import com.example.irishka.movieapp.ui.movie.view.MovieFragment;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class CreatorsFragment extends MovieFragment implements CreatorsView {

    @Inject
    Provider<CreatorsPresenter> presenterProvider;

    @InjectPresenter
    CreatorsPresenter presenter;

    @ProvidePresenter
    CreatorsPresenter providePresenter() {
        return presenterProvider.get();
    }

    @Inject
    ActorsAdapter actorsAdapter;

    @BindView(R.id.actors_recycler_view)
    RecyclerView actorsRecyclerView;

    public static CreatorsFragment newInstance(){
        return new CreatorsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_creators, container, false);
        ButterKnife.bind(this, v);

        actorsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        actorsRecyclerView.setAdapter(actorsAdapter);

        return v;
    }

    @Override
    public String getTitle() {
        return "Cast";
    }

    @Override
    public void showCreators(Credits credits) {

        actorsAdapter.setList(credits.getCast());

    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }
}