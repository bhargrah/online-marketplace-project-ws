package com.bhargrah.productservice.repository;

import com.bhargrah.productservice.model.Product;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.mongodb.repository.MongoRepository;
@Observed
public interface ProductRepository extends MongoRepository<Product, String> {
}
