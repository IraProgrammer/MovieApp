package com.example.irishka.movieapp.data.network.mappers;

import com.example.irishka.movieapp.data.models.ProductionCountryModel;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class ProductionCountryMapper implements Function<ProductionCountryModel, ProductionCountry> {

    @Inject
    public ProductionCountryMapper(){

    }

    @Override
    public ProductionCountry apply(ProductionCountryModel productionCountryModel){
        ProductionCountry productionCountry = new ProductionCountry();
        productionCountry.setName(productionCountryModel.getName());
        return productionCountry;
    }

    public List<ProductionCountry> mapProductionCountryList(List<ProductionCountryModel> productionCountryModels){
        List<ProductionCountry> productionCountries = new ArrayList<>();

        for (ProductionCountryModel productionCountryModel: productionCountryModels) {
            productionCountries.add(apply(productionCountryModel));
        }

        return productionCountries;
    }
}
