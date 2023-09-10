package com.shopping_cart.shopping_cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopping_cart.shopping_cart.entity.Catalogue;
import com.shopping_cart.shopping_cart.repository.CatalogueRepository;

import javax.annotation.PostConstruct;


@Component
public class CatalogueDataLoader {

    private CatalogueRepository catalogueRepository;

    @Autowired
    public CatalogueDataLoader(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    @PostConstruct
    public void loadCatalogueData() {
        // Clear the database first (optional)
        catalogueRepository.deleteAll();

        // Load data here
        catalogueRepository.save(new Catalogue("Product 1", 9.99f, "Description 1"));
        catalogueRepository.save(new Catalogue("Product 2", 19.99f, "Description 2"));
        catalogueRepository.save(new Catalogue("Product 3", 29.99f, "Description 3"));
        catalogueRepository.save(new Catalogue("Product 4", 39.99f, "Description 4"));
    }
}
