package com.example.irishka.movieapp.ui.film.di;

import android.support.v4.app.Fragment;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.ui.film.view.CreatorsFragment;
import com.example.irishka.movieapp.ui.film.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.film.view.TrailersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MovieActivityModule {

    @PerFragment
    @ContributesAndroidInjector(modules = {DescriptionFragmentModule.class})
    abstract DescriptionFragment providesDescriptionFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {CreatorsFragmentModule.class})
    abstract CreatorsFragment providesCreatorsFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = {TrailersFragmentModule.class})
    abstract TrailersFragment providesTrailersFragment();
}
