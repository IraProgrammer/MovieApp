package com.example.irishka.movieapp.ui.actor.di;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repositories.ICastsRepository;
import com.example.irishka.movieapp.domain.repositories.IMoviesRepository;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.actor.info.presenter.InfoPresenter;
import com.example.irishka.movieapp.ui.actor.info.view.InfoFragment;
import com.example.irishka.movieapp.ui.actor.info.view.PhotosAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class InfoFragmentModule {

    @Provides
    @PerFragment
    static InfoPresenter providesInfoPresenter(ICastsRepository castsRepository, long id) {
        return new InfoPresenter(castsRepository, id);
    }

    @Provides
    @PerFragment
    static PhotosAdapter providesPhotoAdapter(InfoFragment infoFragment, GlideHelper glideHelper){
        return new PhotosAdapter(infoFragment, glideHelper);
    }
}
