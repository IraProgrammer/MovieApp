package com.example.irishka.movieapp.ui.movie.trailers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irishka.movieapp.R;

import dagger.android.support.AndroidSupportInjection;

public class TrailersFragment extends Fragment {

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
        return "Review";
    }

}
