package com.shopping_cart.shopping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping_cart.shopping_cart.entity.Cart;

// assignment 3.14 create Cart Repo then extends JpaRepo to gain access to the JPA methods
public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
