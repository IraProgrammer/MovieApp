package com.example.irishka.movieapp.ui.film.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irishka.movieapp.R;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TrailersFragment extends Fragment {

//    @Inject
//    public TrailersFragment() {
//    }

    public static TrailersFragment newInstance(){
        return new TrailersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trailers, container, false);
    }

    @Override
    public String toString() {
        return "Trailers";
    }

}
