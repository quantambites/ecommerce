package com.ecom_server.server.controller;

import com.ecom_server.server.model.Address;
import com.ecom_server.server.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody Address address) {
        Address saved = addressService.addAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", true, "data", saved));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getAll(@PathVariable String userId) {
        List<Address> list = addressService.getAddresses(userId);
        return ResponseEntity.ok(Map.of("success", true, "data", list));
    }

    @PutMapping("/update/{userId}/{addressId}")
    public ResponseEntity<?> edit(@PathVariable String userId, @PathVariable String addressId, @RequestBody Address data) {
        Address updated = addressService.editAddress(userId, addressId, data);
        return ResponseEntity.ok(Map.of("success", true, "data", updated));
    }

    @DeleteMapping("/delete/{userId}/{addressId}")
    public ResponseEntity<?> delete(@PathVariable String userId, @PathVariable String addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(Map.of("success", true, "message", "Address deleted successfully"));
    }
}

