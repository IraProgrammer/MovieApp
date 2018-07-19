package com.example.irishka.movieapp.data.network;

import com.example.irishka.movieapp.BuildConfig;

import java.io.IOException;

import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO: не удалила
/*public class ApiManager {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static ApiManager apiManager;

    private MoviesApi moviesApi;

    public MoviesApi getMoviesApi() {
        return moviesApi;
    }

    private ApiManager(){
    }

    public static ApiManager getInstance() {

        if (apiManager == null) {
            apiManager = new ApiManager();
            apiManager.init();
        }

        return apiManager;
    }

    private void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .build();

        moviesApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(MoviesApi.class);

    }
} */
