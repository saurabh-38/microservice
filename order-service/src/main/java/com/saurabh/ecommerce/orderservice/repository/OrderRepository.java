package com.saurabh.ecommerce.orderservice.repository;

import com.saurabh.ecommerce.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
