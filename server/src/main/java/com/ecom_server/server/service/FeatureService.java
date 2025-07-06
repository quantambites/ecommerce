package com.ecom_server.server.service;

import com.ecom_server.server.model.Feature;
import com.ecom_server.server.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;

    public Feature addFeatureImage(String image) {
        Feature feature = Feature.builder()
                .image(image)
                .build();
        return featureRepository.save(feature);
    }

    public List<Feature> getFeatureImages() {
        return featureRepository.findAll();
    }
}
