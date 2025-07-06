package com.ecom_server.server.service;

import com.ecom_server.server.model.Product;
import com.ecom_server.server.repository.ProductRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SearchService {

    private final MongoTemplate mongoTemplate;

    public SearchService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ResponseEntity<?> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Keyword is required and must be in string format"
            ));
        }

        Pattern regex = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);

        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("title").regex(regex),
                Criteria.where("description").regex(regex),
                Criteria.where("category").regex(regex),
                Criteria.where("brand").regex(regex)
        ));

        List<Product> products = mongoTemplate.find(query, Product.class);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", products
        ));
    }
}
