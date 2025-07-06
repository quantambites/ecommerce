package com.ecom_server.server.controller;

import com.ecom_server.server.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("my_file") MultipartFile file) {
        try {
            Map result = imageUploadService.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "result", result
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Image upload failed"
            ));
        }
    }
}

