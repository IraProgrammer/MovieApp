package com.example.irishka.movieapp.ui.movie.di;

import android.support.v7.widget.LinearLayoutManager;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.movie.creators.presenter.CreatorsPresenter;
import com.example.irishka.movieapp.ui.movie.creators.view.ActorsAdapter;
import com.example.irishka.movieapp.ui.movie.description.PrepareDescription;
import com.example.irishka.movieapp.ui.movie.description.presenter.DescriptionPresenter;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movie.description.view.GalleryAdapter;
import com.example.irishka.movieapp.ui.movie.description.view.RelatedMoviesAdapter;
import com.example.irishka.movieapp.ui.movie.di.qualifiers.Gallery;
import com.example.irishka.movieapp.ui.movie.di.qualifiers.Related;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DescriptionFragmentModule {

    @Provides
    @PerFragment
    static RelatedMoviesAdapter providesRelatedMoviesAdapter(DescriptionFragment descriptionFragment) {
        return new RelatedMoviesAdapter(descriptionFragment);
    }

    @Provides
    @PerFragment
    static GalleryAdapter providesGalleryAdapter(DescriptionFragment descriptionFragment) {
        return new GalleryAdapter(descriptionFragment);
    }

    @Provides
    @PerFragment
    static PrepareDescription providesPrepareDescriptions(DescriptionFragment descriptionFragment, GlideHelper glideHelper) {
        return new PrepareDescription(descriptionFragment, glideHelper);
    }

    @Provides
    @PerFragment
    @Related
    static LinearLayoutManager providesLinearLayoutManagerForRelated(DescriptionFragment descriptionFragment){
        return new LinearLayoutManager(descriptionFragment.getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    @Provides
    @PerFragment
    @Gallery
    static LinearLayoutManager providesLinearLayoutManagerForGallery(DescriptionFragment descriptionFragment){
        return new LinearLayoutManager(descriptionFragment.getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

}
