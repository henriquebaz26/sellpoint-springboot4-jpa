package com.sellpoint.SellPoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellpoint.SellPoint.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {   

}
