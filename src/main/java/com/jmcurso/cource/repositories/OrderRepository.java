package com.jmcurso.cource.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jmcurso.cource.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByOrderStatus(Integer orderStatus);

	@Query("SELECT o FROM Order o WHERE o.client.id = :userId")
	List<Order> findByUserId(@Param("userId") Long userId);

	List<Order> findByClientId(Long clientId);

}
