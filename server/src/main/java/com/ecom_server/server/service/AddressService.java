package com.ecom_server.server.service;

import com.ecom_server.server.model.Address;
import com.ecom_server.server.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepo;

    public Address addAddress(Address address) {
        if (address.getUserId() == null || address.getAddress() == null || address.getCity() == null ||
                address.getPincode() == null || address.getPhone() == null || address.getNotes() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data provided!");
        }

        return addressRepo.save(address);
    }

    public List<Address> getAddresses(String userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is required!");
        }
        return addressRepo.findByUserId(userId);
    }

    public Address editAddress(String userId, String addressId, Address updatedData) {
        Address address = addressRepo.findById(addressId)
                .filter(a -> a.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        address.setAddress(updatedData.getAddress());
        address.setCity(updatedData.getCity());
        address.setPincode(updatedData.getPincode());
        address.setPhone(updatedData.getPhone());
        address.setNotes(updatedData.getNotes());

        return addressRepo.save(address);
    }

    public void deleteAddress(String userId, String addressId) {
        Address address = addressRepo.findById(addressId)
                .filter(a -> a.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        addressRepo.delete(address);
    }
}

