package com.bhargrah.orderservice.repository;

import com.bhargrah.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order,Long> {

}
