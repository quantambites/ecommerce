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

import java.time.Instant;

@Document(collection = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Address {

    @Id
    @JsonProperty("_id")
    private String id;

    private String userId;
    private String address;
    private String city;
    private String pincode;
    private String phone;
    private String notes;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
