package com.example.irishka.movieapp.ui.movies.fragment;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.movieapp.domain.repository.IMoviesRepository;
import com.example.irishka.movieapp.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.NOW_PLAYING;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.POPULAR;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.TOP_RATED;
import static com.example.irishka.movieapp.ui.movies.view.ViewPagerAdapter.UPCOMING;

@InjectViewState
public class MainFilmsPresenter extends BasePresenter<MainFilmsView> {

    private IMoviesRepository moviesRepository;

    private int page = 1;

    private String type;

    @Inject
    public MainFilmsPresenter(IMoviesRepository repository, String type) {
        this.moviesRepository = repository;
        this.type = type;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        downloadMovies();
    }

    public void downloadMovies(){
//        if (type.equals(NOW_PLAYING)){
//            downloadNowPlaying();
//        }
//        else if (type.equals(POPULAR)){
//            downloadPopular();
//        }
//        if (type.equals(TOP_RATED)){
//            downloadTopRated();
//        }
//        if (type.equals(UPCOMING)){
//            downloadUpcoming();
//        }


        addDisposables(moviesRepository.downloadMoviesForMainScreen(page, type)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(movies -> getViewState().finishLoading())
                .doOnSuccess(movies -> page++)
                .doOnError(movies -> getViewState().finishLoading())
                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));


    }

//    private void downloadNowPlaying() {
//
//        addDisposables(moviesRepository.getNowPlayingFromInternet(page)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSuccess(movies -> getViewState().finishLoading())
//                .doOnSuccess(movies -> page++)
//                .doOnError(movies -> getViewState().finishLoading())
//                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));
//    }
//
//    private void downloadPopular() {
//
//        addDisposables(moviesRepository.getPopularFromInternet(page)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSuccess(movies -> getViewState().finishLoading())
//                .doOnSuccess(movies -> page++)
//                .doOnError(movies -> getViewState().finishLoading())
//                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));
//    }
//
//    private void downloadTopRated() {
//
//        addDisposables(moviesRepository.getTopRatedFromInternet(page)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSuccess(movies -> getViewState().finishLoading())
//                .doOnSuccess(movies -> page++)
//                .doOnError(movies -> getViewState().finishLoading())
//                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));
//    }
//
//    private void downloadUpcoming() {
//
//        addDisposables(moviesRepository.getUpcomingFromInternet(page)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSuccess(movies -> getViewState().finishLoading())
//                .doOnSuccess(movies -> page++)
//                .doOnError(movies -> getViewState().finishLoading())
//                .subscribe(movies -> getViewState().showMovies(movies), throwable -> {}));
//    }
}
