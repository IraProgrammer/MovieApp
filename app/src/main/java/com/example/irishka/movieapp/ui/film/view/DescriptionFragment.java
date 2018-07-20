package com.example.irishka.movieapp.ui.film.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.data.models.DescriptionModel;
import com.example.irishka.movieapp.ui.film.presenter.DescriptionPresenter;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;
import dagger.android.DaggerFragment;
import dagger.android.support.AndroidSupportInjection;

import static com.example.irishka.movieapp.ui.movies.view.MoviesListActivity.MOVIE_ID;

public class DescriptionFragment extends MvpAppCompatFragment implements DescriptionView {

  /*  @Inject
    public DescriptionFragment() {
    } */

    @Inject
    Provider<DescriptionPresenter> presenterProvider;

    @InjectPresenter
    DescriptionPresenter presenter;

    @ProvidePresenter
    DescriptionPresenter providePresenter() {
        return presenterProvider.get();
    }

    @BindView(R.id.film_title)
    TextView filmTitle;

    public static DescriptionFragment newInstance(){
        return new DescriptionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_description, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public String toString() {
        return "Description";
    }

    @Override
    public void showDescription(DescriptionModel description) {
        filmTitle.setText(description.getTitle());
    }
}
