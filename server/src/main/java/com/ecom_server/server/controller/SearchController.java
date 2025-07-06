package com.ecom_server.server.controller;


import com.ecom_server.server.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<?> search(@PathVariable String keyword) {
        return searchService.searchProducts(keyword);
    }
}
