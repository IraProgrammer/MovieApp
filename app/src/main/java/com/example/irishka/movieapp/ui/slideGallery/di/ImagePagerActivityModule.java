package com.example.irishka.movieapp.ui.slideGallery.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.slideGallery.ImagePagerActivity;
import com.example.irishka.movieapp.ui.slideGallery.SlideGalleryAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

import static com.example.irishka.movieapp.ui.movie.description.view.DescriptionFragment.ARRAY_LIST;

@Module
public abstract class ImagePagerActivityModule {

    @Provides
    @PerActivity
    static List<Image> providesBackdropsList(ImagePagerActivity imagePagerActivity){
        return imagePagerActivity.getIntent().getParcelableArrayListExtra(ARRAY_LIST);
    }

    @Provides
    @PerActivity
    static SlideGalleryAdapter providesSlideGalleryAdapter(List<Image> backdrops, GlideHelper glideHelper){
        return new SlideGalleryAdapter(backdrops, glideHelper);
    }
}
