package com.jmcurso.cource.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmcurso.cource.entities.OrderItem;
import com.jmcurso.cource.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
