package com.ecom_server.server.controller;

import com.ecom_server.server.model.Product;
import com.ecom_server.server.service.ProductStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// ProductStatsController.java
@RestController
@RequestMapping("/api/shop/stats")
public class ProductStatsController {

    private final ProductStatsService statsService;

    public ProductStatsController(ProductStatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/most-bought")
    public ResponseEntity<?> getMostBoughtProducts() {
        List<Product> products = statsService.getMostBoughtProducts();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", products
        ));
    }
}
