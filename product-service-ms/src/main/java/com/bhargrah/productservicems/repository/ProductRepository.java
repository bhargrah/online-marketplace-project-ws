package com.bhargrah.productservicems.repository;

import com.bhargrah.productservicems.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
