// Assignment 3.14
package com.shopping_cart.shopping_cart.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopping_cart.shopping_cart.entity.Catalogue;
import com.shopping_cart.shopping_cart.repository.CatalogueRepository;

@RestController
public class CatalogueController {

    @Autowired
    CatalogueRepository repo; // Added

    // Scenario 3: Database connection is lost
    // Updated (produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/catalogues", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Catalogue>> list(@RequestParam(defaultValue = "") String search) {
        try {
            List<Catalogue> results = repo.findByNameContaining(search);
            System.out.println("Results Size: " + results.size());
            if (results.size() == 0) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(results);
            }
        } catch (Exception e) {
            // Scenario 3: Database connection is lost
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /*
     * Switch between @PathVariable and @RequestParam to help learners understand
     * the difference.
     * 
     * @PathVariable => /catalogues/1
     * 
     * @RequestParam => /catalogues?id=1
     */
    @GetMapping(value = "/catalogues/{id}")
    public ResponseEntity<?> getCatalogueById(@PathVariable int id) {
        Optional<Catalogue> result = (Optional<Catalogue>) repo.findById(id);
        if (result.isPresent())
            // Scenario 2: Catalogue Id is present
            return ResponseEntity.ok(result.get());
        // Scenario 1: Catalogue Id is not found
        return ResponseEntity.notFound().build();
    }

    /*
     * By default, a @PostMapping assumes application/json content type.
     */
    @PostMapping(value = "/catalogues")
    public ResponseEntity<Catalogue> create(@RequestBody RequestBodyTempData data) {
        Catalogue newRecord = new Catalogue();
        newRecord.setName(data.getName());
        newRecord.setPrice(data.getPrice());

        try {
            Catalogue created = repo.save(newRecord);
            return ResponseEntity.created(null).body(created);
        } catch (Exception e) {
            System.out.println(e);
            // Scenario 3: Database connection is lost
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}

/*
 * This is a private class, not accessible outside this java file.
 * We will use this for now. In the future, the request body
 */
class RequestBodyTempData {
    String name;
    float price;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
