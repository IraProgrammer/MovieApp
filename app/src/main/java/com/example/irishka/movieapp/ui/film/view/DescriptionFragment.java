package com.example.irishka.movieapp.ui.film.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.ui.film.presenter.DescriptionPresenter;

import javax.inject.Inject;

import dagger.android.DaggerFragment;

public class DescriptionFragment extends MvpAppCompatFragment implements DescriptionView{

    @Inject
    public DescriptionFragment() {
    }

    @InjectPresenter
    DescriptionPresenter presenter;

    public static DescriptionFragment newInstance(){
        return new DescriptionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public String toString() {
        return "Description";
    }

    @Override
    public void showInfo() {

    }
}
