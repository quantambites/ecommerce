package com.ecom_server.server.repository;

import com.ecom_server.server.model.ProductReview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductReviewRepository extends MongoRepository<ProductReview, String> {
    List<ProductReview> findByProductId(String productId);
    List<ProductReview> findByProductIdAndUserId(String productId, String userId);
}