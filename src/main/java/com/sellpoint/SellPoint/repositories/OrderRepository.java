package com.sellpoint.SellPoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellpoint.SellPoint.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {   

}