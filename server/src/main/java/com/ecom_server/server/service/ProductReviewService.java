package com.ecom_server.server.service;

import com.ecom_server.server.model.ProductReview;
import com.ecom_server.server.repository.OrderRepository;
import com.ecom_server.server.repository.ProductRepository;
import com.ecom_server.server.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public ProductReview addReview(ProductReview review) {
        // Check if user bought the product
        String productId = review.getProductId();
        String userId = review.getUserId();
        boolean hasOrder = !orderRepo.findByUserIdAndCartItemsProductId(review.getUserId(), review.getProductId()).isEmpty();

        if (!hasOrder) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You need to purchase product to review it.");
        }

        // Check if user already reviewed
        boolean alreadyReviewed = !productReviewRepo.findByProductIdAndUserId(productId, userId).isEmpty();
        if (alreadyReviewed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already reviewed this product!");
        }

        // Save review
        ProductReview savedReview = productReviewRepo.save(review);

        // Recalculate average review
        List<ProductReview> reviews = productReviewRepo.findByProductId(review.getProductId());
        double avg = reviews.stream()
                .mapToInt(ProductReview::getReviewValue)
                .average()
                .orElse(0.0);

        productRepo.findById(review.getProductId()).ifPresent(product -> {
            product.setAverageReview(avg);
            productRepo.save(product);
        });

        return savedReview;
    }

    public List<ProductReview> getReviews(String productId) {
        return productReviewRepo.findByProductId(productId);
    }
}
