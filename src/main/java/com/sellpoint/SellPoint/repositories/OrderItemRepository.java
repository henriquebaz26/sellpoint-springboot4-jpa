package com.sellpoint.SellPoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellpoint.SellPoint.entities.OrderItem;
import com.sellpoint.SellPoint.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {   

}
