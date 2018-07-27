package com.example.irishka.movieapp.data.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class BackdropDb {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String fileUrl;

    public BackdropDb(){
    }

    @Ignore
    public BackdropDb(String fileUrl){
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
