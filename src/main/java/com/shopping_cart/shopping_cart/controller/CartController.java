package com.shopping_cart.shopping_cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping_cart.shopping_cart.repository.CartRepository;
import com.shopping_cart.shopping_cart.entity.Cart;
import com.shopping_cart.shopping_cart.entity.Catalogue;

/*
    Assignment:
    - Apply @RestController to the class.
    - Apply @GetMapping and @PostMapping to the methods.
    - Apply @PathVariable to the method parameter of get(int).

    Test:
    - Use a client HTTP Tool like YARC to consume the API you have just created.
    E.g. https://chrome.google.com/webstore/detail/yet-another-rest-client/ehafadccdcdedbhcbddihehiodgcddpl?hl=en
*/

@RestController
@RequestMapping("/carts")
public class CartController {

    private CartRepository cartRepository;

    @Autowired
    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // get all carts
    @GetMapping("")
    public String getAllCarts() {
        return "GET /carts ok";
    }

    // get 1 cart
    @GetMapping("{id}")
    public String getCartById(@PathVariable int id) {
        return "GET /carts/" + id + " ok";
    }

    // create 1 cart
    @PostMapping("")
    public String createCart(@RequestBody Cart cart) {
        // get catalogue item ID -
        Catalogue item = cart.getItem();
        cartRepository.save(cart);
        return "POST /carts ok";
    }
}