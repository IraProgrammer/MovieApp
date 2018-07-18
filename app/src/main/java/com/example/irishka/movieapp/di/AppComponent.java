package com.example.irishka.movieapp.di;

import android.content.Context;

import com.example.irishka.movieapp.di.module.DatabaseModule;
import com.example.irishka.movieapp.di.module.NetworkModule;
import com.example.irishka.movieapp.di.module.RepModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = { DatabaseModule.class, NetworkModule.class, RepModule.class} )
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }

    MovieComponent.Builder movieComponentBuilder();
}
