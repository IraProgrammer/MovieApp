package com.example.irishka.movieapp.ui.movie.creators.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class CreatorsFragment extends MvpAppCompatFragment implements CreatorsView, ActorsAdapter.OnItemClickListener {

    public static final String PERSON_ID = "id_of_cast";

    public static final String ACTOR = "ACTOR";

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

    @BindView(R.id.progress)
    MaterialProgressBar progressBar;

    @BindView(R.id.tv_sorry)
    TextView sorryTv;

    int i = 1;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static CreatorsFragment newInstance() {
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

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.downloadCasts();
        });

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent_material_dark_1));

        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.background_holo_dark));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            swipeRefreshLayout.setElevation(4);
        }

        return v;
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        swipeRefreshLayout.setRefreshing(false);

        if (i > 1)
            Toast.makeText(this.getContext(), getResources().getString(R.string.error_description), Toast.LENGTH_SHORT).show();
        i++;
    }

    @Override
    public void hideError() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSorry() {
        sorryTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCasts(List<Cast> cast) {
        actorsAdapter.setList(cast);
    }

    @Override
    public void onItemClick(Cast cast) {
        Intent intent = new Intent(getActivity(), ActorActivity.class);
        intent.putExtra(PERSON_ID, cast.getId());
        intent.putExtra(ACTOR, cast.getName());
        startActivity(intent);
    }
}