package com.example.irishka.movieapp.ui.movie.creators.view;

import android.content.Intent;
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
import com.example.irishka.movieapp.domain.entity.Cast;
import com.example.irishka.movieapp.ui.actor.view.ActorActivity;
import com.example.irishka.movieapp.ui.movie.creators.presenter.CreatorsPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class CreatorsFragment extends MvpAppCompatFragment implements CreatorsView, ActorsAdapter.OnItemClickListener {

    public static final String PERSON_ID = "id_of_cast";

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
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
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
    public void showCasts(List<Cast> cast) {
        actorsAdapter.setList(cast);

    }

    @Override
    public void onItemClick(Cast cast) {
        Intent intent = new Intent(getActivity(), ActorActivity.class);
        intent.putExtra(PERSON_ID, cast.getId());
        intent.putExtra("ACTOR", cast.getName());
        startActivity(intent);
    }
}