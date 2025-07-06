package com.ecom_server.server.repository;

import com.ecom_server.server.model.Feature;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeatureRepository extends MongoRepository<Feature, String> {
}
