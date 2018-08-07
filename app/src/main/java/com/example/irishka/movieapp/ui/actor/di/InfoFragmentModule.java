package com.example.irishka.movieapp.ui.actor.di;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
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
    static InfoPresenter providesInfoPresenter(IMoviesRepository moviesRepository, long id) {
        return new InfoPresenter(moviesRepository, id);
    }

    @Provides
    @PerFragment
    static PhotosAdapter providesPhotoAdapter(InfoFragment infoFragment, GlideHelper glideHelper){
        return new PhotosAdapter(infoFragment, glideHelper);
    }
}
