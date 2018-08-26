package com.example.irishka.movieapp.ui.movie.description;

import android.app.Activity;
import android.graphics.Point;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FullScreenHelper {

    private Activity context;
    private LinearLayout linearWithTabs;
    private View youTubeView;
    private View[] views;

    public FullScreenHelper(Activity context, LinearLayout linearWithTabs, View youTubeView, View... views) {
        this.context = context;
        this.linearWithTabs = linearWithTabs;
        this.youTubeView = youTubeView;
        this.views = views;
    }

    public void enterFullScreen() {
        View decorView = context.getWindow().getDecorView();

        hideSystemUI(decorView);

        for (View view : views) {
            view.setVisibility(View.GONE);
            view.invalidate();
        }

        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) linearWithTabs.getLayoutParams();
        p.setBehavior(null);
        linearWithTabs.setLayoutParams(p);

        Point point = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(point);

        ViewGroup.LayoutParams viewParams = youTubeView.getLayoutParams();
        viewParams.height = point.y;
        youTubeView.setLayoutParams(viewParams);
    }

    public void exitFullScreen() {
        View decorView = context.getWindow().getDecorView();

        showSystemUI(decorView);

        for (int i = views.length - 1; i >= 0; i--) {
            views[i].setVisibility(View.VISIBLE);
            views[i].invalidate();
        }

        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) linearWithTabs.getLayoutParams();
        p.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        linearWithTabs.setLayoutParams(p);

        ViewGroup.LayoutParams viewParams = youTubeView.getLayoutParams();
        viewParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        youTubeView.setLayoutParams(viewParams);
    }

    private void hideSystemUI(View mDecorView) {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showSystemUI(View mDecorView) {
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
}
