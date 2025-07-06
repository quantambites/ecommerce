package com.ecom_server.server.controller;

import com.ecom_server.server.model.ProductReview;
import com.ecom_server.server.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop/review")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody ProductReview review) {
        try{
            ProductReview savedReview = reviewService.addReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "success", true,
                    "data", savedReview
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductReviews(@PathVariable String productId) {
        try{
            List<ProductReview> reviews = reviewService.getReviews(productId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", reviews
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }
}
