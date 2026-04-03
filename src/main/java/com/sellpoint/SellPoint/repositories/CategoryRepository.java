package com.sellpoint.SellPoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellpoint.SellPoint.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {   

}