package com.ecom_server.server.repository;

import com.ecom_server.server.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}