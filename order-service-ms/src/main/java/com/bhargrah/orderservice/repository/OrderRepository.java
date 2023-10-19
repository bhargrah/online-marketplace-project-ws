package com.bhargrah.orderservice.repository;

import com.bhargrah.orderservice.model.Order;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
@Observed
public interface OrderRepository extends JpaRepository <Order,Long> {

}
