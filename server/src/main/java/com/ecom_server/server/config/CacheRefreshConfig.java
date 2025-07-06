package com.ecom_server.server.config;

import com.ecom_server.server.service.ProductStatsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheRefreshConfig {
    private final ProductStatsService statsService;

    public CacheRefreshConfig(ProductStatsService statsService) {
        this.statsService = statsService;
    }

    @Scheduled(fixedRate = 3600000) // every 1 hour (in ms)
    public void refreshTopProductsCache() {
        statsService.refreshMostBoughtProducts();
        System.out.println("Top products cache refreshed.");
    }
}
