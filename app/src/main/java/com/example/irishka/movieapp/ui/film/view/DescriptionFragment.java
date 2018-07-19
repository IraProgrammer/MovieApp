package com.example.irishka.movieapp.ui.film.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irishka.movieapp.R;

import javax.inject.Inject;

import dagger.android.DaggerFragment;

public class DescriptionFragment extends dagger.android.support.DaggerFragment {

    @Inject
    public DescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

}
