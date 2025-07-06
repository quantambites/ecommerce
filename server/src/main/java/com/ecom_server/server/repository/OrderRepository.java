package com.ecom_server.server.repository;

import com.ecom_server.server.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
    // Correct custom MongoDB query to check if user ordered a specific product
    @Query("{ 'userId': ?0, 'cartItems.productId': ?1 }")
    List<Order> findByUserIdAndCartItemsProductId(String userId, String productId);

    // Optional: More efficient version if you just need existence check
    @Query(value = "{ 'userId': ?0, 'cartItems.productId': ?1 }", exists = true)
    boolean existsByUserIdAndCartItemsProductId(String userId, String productId);

}
