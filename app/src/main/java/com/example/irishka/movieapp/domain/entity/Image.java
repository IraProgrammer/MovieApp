package com.example.irishka.movieapp.domain.entity;

import java.io.Serializable;

public class Image implements Serializable {

    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
