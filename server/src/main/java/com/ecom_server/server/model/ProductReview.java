package com.ecom_server.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Collection;


@Document(collection = "productreviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductReview {
    @Id
    private String id;

    private String productId;
    private String userId;
    private String userName;
    private String reviewMessage;
    private int reviewValue;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
