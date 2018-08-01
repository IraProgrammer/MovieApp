package com.example.irishka.movieapp.ui.movie.creators.actor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.irishka.movieapp.R;
import com.example.irishka.movieapp.ui.movie.creators.actor.films.view.FilmsFragment;
import com.example.irishka.movieapp.ui.movie.creators.actor.info.InfoFragment;

import javax.inject.Inject;

public class ActorViewPagerAdapter extends FragmentPagerAdapter {

    private ActorActivity actorActivity;

    @Inject
    public ActorViewPagerAdapter(ActorActivity actorsActivity) {
        super(actorsActivity.getSupportFragmentManager());
        this.actorActivity = actorsActivity;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return InfoFragment.newInstance();
            case 1:
                return FilmsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return actorActivity.getString(R.string.info_fragment);
            case 1:
                return actorActivity.getString(R.string.films_fragment);
            default:
                return "";
        }
    }
}
