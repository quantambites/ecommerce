package com.ecom_server.server.model.submodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    @JsonProperty("_id")
    private String id;
    private String userId;
    private List<CartItemResponse> items;
    private Instant createdAt;
    private Instant updatedAt;
}