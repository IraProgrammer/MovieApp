package com.example.irishka.movieapp.data.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeywordsPageModel {

    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("results")
    @Expose
    private List<KeywordModel> results;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public List<KeywordModel> getResults() {
        return results;
    }

    public void setResults(List<KeywordModel> results) {
        this.results = results;
    }

}
