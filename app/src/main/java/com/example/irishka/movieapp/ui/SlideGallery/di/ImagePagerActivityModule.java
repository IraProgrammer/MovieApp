package com.example.irishka.movieapp.ui.SlideGallery.di;

import com.example.irishka.movieapp.di.scopes.PerActivity;
import com.example.irishka.movieapp.domain.entity.Image;
import com.example.irishka.movieapp.ui.GlideHelper;
import com.example.irishka.movieapp.ui.SlideGallery.ImagePagerActivity;
import com.example.irishka.movieapp.ui.SlideGallery.SlideGalleryAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ImagePagerActivityModule {

    @Provides
    @PerActivity
    static ArrayList<Image> providesBackdropsList(ImagePagerActivity imagePagerActivity){
        return (ArrayList<Image>) imagePagerActivity.getIntent().getSerializableExtra("ARRAYLIST");
    }

    @Provides
    @PerActivity
    static SlideGalleryAdapter providesSlideGalleryAdapter(ArrayList<Image> backdrops, GlideHelper glideHelper){
        return new SlideGalleryAdapter(backdrops, glideHelper);
    }
}
