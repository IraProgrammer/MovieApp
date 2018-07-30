package com.example.irishka.movieapp.ui.movie.di;

import com.example.irishka.movieapp.di.scopes.PerFragment;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.movie.creators.presenter.CreatorsPresenter;
import com.example.irishka.movieapp.ui.movie.creators.view.ActorsAdapter;
import com.example.irishka.movieapp.ui.movie.description.PrepareDescription;
import com.example.irishka.movieapp.ui.movie.description.presenter.DescriptionPresenter;
import com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment;
import com.example.irishka.movieapp.ui.movie.description.view.GalleryAdapter;
import com.example.irishka.movieapp.ui.movie.description.view.RelatedMoviesAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DescriptionFragmentModule {

    @Provides
    @PerFragment
    static DescriptionPresenter providesDescriptionPresenter(IMoviesRepository moviesRepository, long movieId) {
        return new DescriptionPresenter(moviesRepository, movieId);
    }

    @Provides
    @PerFragment
    static GalleryAdapter providesGalleryAdapter(){
        return new GalleryAdapter();
    }

    @Provides
    @PerFragment
    static RelatedMoviesAdapter providesRelatedMoviesAdapter(DescriptionFragment descriptionFragment){
        return new RelatedMoviesAdapter(descriptionFragment);
    }

    // TODO: ненужно, т.к. не покрывается интерфейсом, т.е. есть только 1 реализация
    // проверь все подобные места
    @Provides
    @PerFragment
    static PrepareDescription providesPrepareDescriptions(DescriptionFragment descriptionFragment){
        return new PrepareDescription(descriptionFragment);
    }

}
