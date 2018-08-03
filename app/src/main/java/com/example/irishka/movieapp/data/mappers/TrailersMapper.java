package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.BackdropDb;
import com.example.irishka.movieapp.data.models.BackdropModel;
import com.example.irishka.movieapp.data.models.TrailerModel;
import com.example.irishka.movieapp.domain.entity.Backdrop;
import com.example.irishka.movieapp.domain.entity.Trailer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TrailersMapper {

    @Inject
    public TrailersMapper() {

    }

    public Trailer apply(TrailerModel trailerModel) {
        Trailer trailer = new Trailer();
        trailer.setId(trailerModel.getId());
        trailer.setKey(trailerModel.getKey());
        trailer.setName(trailerModel.getName());
        trailer.setSize(trailerModel.getSize());
        trailer.setType(trailerModel.getType());
        return trailer;
    }

    public Trailer mapTrailersListToOneTrailer(List<TrailerModel> trailerModels){

        for (TrailerModel trailerModel: trailerModels) {
            if (trailerModel.getType().equals("Trailer"))
            return apply(trailerModel);
        }

        Trailer t = new Trailer();
        t.setKey("SUXWAEX2jlg");

        return t;
    }

}
