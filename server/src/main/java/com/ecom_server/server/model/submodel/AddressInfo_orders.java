package com.ecom_server.server.model.submodel;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AddressInfo_orders {
    private String addressId;
    private String address;
    private String city;
    private String pincode;
    private String phone;
    private String notes;
}
