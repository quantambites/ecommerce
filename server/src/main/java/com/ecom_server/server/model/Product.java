package com.ecom_server.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;


@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @JsonProperty("_id")
    private String id;

    private String image;
    private String title;
    private String description;
    private String category;
    private String brand;

    private Double price;
    private Double salePrice;
    private Integer totalStock;
    private Double averageReview;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
