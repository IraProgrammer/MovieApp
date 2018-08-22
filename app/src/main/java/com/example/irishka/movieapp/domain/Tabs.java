package com.example.irishka.movieapp.domain;

public enum Tabs {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming");

    private final String title;

    Tabs(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}