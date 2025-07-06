package com.ecom_server.server.service;

import com.ecom_server.server.model.Order;
import com.ecom_server.server.model.Product;
import com.ecom_server.server.model.submodel.CartItem_orders;
import com.ecom_server.server.repository.OrderRepository;
import com.ecom_server.server.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductStatsService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final RedisTemplate<String, Object> redisTemplate;

    private final String CACHE_KEY = "top4_products";

    // Called normally — uses cache
    public List<Product> getMostBoughtProducts() {
        List<Product> cached = (List<Product>) redisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) return cached;

        return refreshMostBoughtProducts(); // Populate and return
    }

    // Called by scheduler — recomputes and overwrites cache
    public List<Product> refreshMostBoughtProducts() {
        Map<String, Integer> productCountMap = new HashMap<>();

        for (Order order : orderRepo.findAll()) {
            for (CartItem_orders item : order.getCartItems()) {
                productCountMap.put(
                        item.getProductId(),
                        productCountMap.getOrDefault(item.getProductId(), 0) + item.getQuantity()
                );
            }
        }

        List<String> top4Ids = productCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(4)
                .map(Map.Entry::getKey)
                .toList();

        List<Product> topProducts = productRepo.findAllById(top4Ids);

        // Overwrite Redis cache
        redisTemplate.opsForValue().set(CACHE_KEY, topProducts);

        return topProducts;
    }
}
