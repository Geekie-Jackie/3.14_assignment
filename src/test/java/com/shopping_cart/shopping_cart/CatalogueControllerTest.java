package com.shopping_cart.shopping_cart;

import com.shopping_cart.shopping_cart.controller.CatalogueController;
import com.shopping_cart.shopping_cart.entity.Catalogue;
import com.shopping_cart.shopping_cart.repository.CatalogueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CatalogueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CatalogueRepository catalogueRepository;

    @DisplayName("Catalogue Id is not found - return status 404 not found")
    @Test
    public void getCatalogueByIdNotFound() throws Exception {
        // Step 1: Build a request
        RequestBuilder request = MockMvcRequestBuilders.get("/catalogues/5");

        // Step 2: Perform the request, get the response, and assert
        mockMvc.perform(request)
                // Assert that the status code is 404 Not Found
                .andExpect(status().isNotFound());
    }

    @DisplayName("Catalogue Id is present - return status 200 ok and response body")
    @Test
    public void getCatalogueByIdFound() throws Exception {
        // Step 1: Create a Catalogue object
        Catalogue catalogue = new Catalogue();
        catalogue.setName("Sample Item");
        catalogue.setPrice(19.99F);

        // Save the Catalogue object to the repository
        Catalogue savedCatalogue = catalogueRepository.save(catalogue);

        // Step 2: Build a request to get the saved Catalogue by ID
        RequestBuilder request = MockMvcRequestBuilders.get("/catalogues/" + savedCatalogue.getId());

        // Step 3: Perform the request, get the response, and assert
        mockMvc.perform(request)
                // Assert that the status code is 200 OK
                .andExpect(status().isOk())
                // Assert that the content is JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Assert that the ID matches the saved Catalogue's ID
                .andExpect(jsonPath("$.id").value(savedCatalogue.getId()));
    }

    @DisplayName("Database connection is lost - return 500 status internal server error")
    @Test
    public void listCataloguesDatabaseConnectionLost() throws Exception {
        // Step 1: Mock the repository's behavior to throw an exception
        when(catalogueRepository.findByNameContaining(any())).thenThrow(new RuntimeException("Database connection lost"));

        // Step 2: Build a GET request to /catalogues
        RequestBuilder request = MockMvcRequestBuilders.get("/catalogues");

        // Step 3: Perform the request, get the response, and assert
        mockMvc.perform(request)
                // Assert that the status code is 500 Internal Server Error
                .andExpect(status().isInternalServerError());
    }


}
