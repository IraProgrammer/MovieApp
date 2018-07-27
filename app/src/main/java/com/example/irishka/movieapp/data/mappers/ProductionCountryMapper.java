package com.example.irishka.movieapp.data.mappers;

import com.example.irishka.movieapp.data.database.entity.ProductionCountryDb;
import com.example.irishka.movieapp.data.models.ProductionCountryModel;
import com.example.irishka.movieapp.domain.entity.ProductionCountry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class ProductionCountryMapper {

    @Inject
    public ProductionCountryMapper(){

    }

    public ProductionCountry apply(ProductionCountryModel productionCountryModel){
        ProductionCountry productionCountry = new ProductionCountry("");
        productionCountry.setName(productionCountryModel.getName());
        return productionCountry;
    }

    public ProductionCountryDb applyToDb(ProductionCountryModel productionCountryModel){
        ProductionCountryDb productionCountry = new ProductionCountryDb("");
        productionCountry.setName(productionCountryModel.getName());
        return productionCountry;
    }

    public ProductionCountry applyFromDb(ProductionCountryDb productionCountryDb){
        ProductionCountry productionCountry = new ProductionCountry("");
        productionCountry.setName(productionCountryDb.getName());
        return productionCountry;
    }

    public List<ProductionCountry> mapProductionCountryList(List<ProductionCountryModel> productionCountryModels){
        List<ProductionCountry> productionCountries = new ArrayList<>();

        for (ProductionCountryModel productionCountryModel: productionCountryModels) {
            productionCountries.add(apply(productionCountryModel));
        }

        return productionCountries;
    }

    public List<ProductionCountryDb> mapProductionCountryListToDb(List<ProductionCountryModel> productionCountryModels){
        List<ProductionCountryDb> productionCountries = new ArrayList<>();

        for (ProductionCountryModel productionCountryModel: productionCountryModels) {
            productionCountries.add(applyToDb(productionCountryModel));
        }

        return productionCountries;
    }

    public List<ProductionCountry> mapProductionCountryListFromDb(List<ProductionCountryDb> productionCountriesDb){
        List<ProductionCountry> productionCountries = new ArrayList<>();

        for (ProductionCountryDb productionCountryDb: productionCountriesDb) {
            productionCountries.add(applyFromDb(productionCountryDb));
        }

        return productionCountries;
    }
}
