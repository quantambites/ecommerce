package com.ecom_server.server.controller;

import com.ecom_server.server.model.Feature;
import com.ecom_server.server.service.FeatureService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/common/feature")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;

    @PostMapping("/add")
    public ResponseEntity<?> addFeatureImage(@RequestBody FeatureRequest request) {
        try {
            Feature feature = featureService.addFeatureImage(request.getImage());
            return ResponseEntity.status(201).body(Map.of(
                    "success", true,
                    "data", feature
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Some error occurred!"
            ));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getFeatureImages() {
        try {
            List<Feature> features = featureService.getFeatureImages();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", features
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Some error occurred!"
            ));
        }
    }

    @Data
    public static class FeatureRequest {
        private String image;
    }
}
