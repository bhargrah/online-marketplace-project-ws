package com.bhargrah.orderservicems.repository;

import com.bhargrah.orderservicems.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order,Long> {

}
